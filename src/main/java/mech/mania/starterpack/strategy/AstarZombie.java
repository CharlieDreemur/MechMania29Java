package mech.mania.starterpack.strategy;

import java.util.*;

import mech.mania.starterpack.game.GameState;
import mech.mania.starterpack.game.character.Character;
import mech.mania.starterpack.game.character.MoveAction;
import mech.mania.starterpack.game.character.action.AbilityAction;
import mech.mania.starterpack.game.character.action.AttackAction;
import mech.mania.starterpack.game.character.action.AttackActionType;
import mech.mania.starterpack.game.character.action.CharacterClassType;
import mech.mania.starterpack.game.util.Position;

/**
 * A simple human which runs away from zombies
 */
public class AstarZombie extends IndividualStrategy {

   // This map keeps track of how many zombies are targeting a specific human
    private static Map<String, Integer> humanTargetCount = new HashMap<>();

    @Override
    public void Init(String id, GameState gameState) {
        super.Init(id, gameState);
        // Reset human target counts at the beginning of each zombie's turn
        humanTargetCount.clear();
    }

    private Character getOptimalTarget(GameState gameState) {
        List<Pair<Character, Integer>> allPairs = new ArrayList<>();
        for (Character character : gameState.characters().values()) {
            if (!character.id().equals(self.id()) && !character.isZombie()) {
                int distance = Helpers.ManhattonDistanceFunction(character.position(), pos);
                // Introduce a weight to factor in how many zombies are already targeting this human
                int weightedDistance = distance + (humanTargetCount.getOrDefault(character.id(), 0) * 10); // Increasing the weight for humans already targeted by more zombies
                allPairs.add(new Pair<>(character, weightedDistance));
            }
        }

        allPairs.sort(Comparator.comparingInt(pair -> pair.second));

        Pair<Character, Integer> bestPair = allPairs.get(0);
        String humanId = bestPair.first.id();
        humanTargetCount.put(humanId, humanTargetCount.getOrDefault(humanId, 0) + 1);

        return bestPair.first;
    }

    @Override
    public AttackAction Attack(String id, GameState gameState, List<AttackAction> attackActions) {
        Init(id, gameState);
        if (attackActions.isEmpty()) {
            return null;
        }
        AttackAction bestTarget = null;
        int bestScore = Integer.MIN_VALUE;

        for (AttackAction a : attackActions) {
            if (a.type() == AttackActionType.CHARACTER) {
                Position attackeePos = gameState.characters().get(a.attackingId()).position();
                int distance = Helpers.ManhattonDistanceFunction(attackeePos, pos);
                int nearbyZombies = 0;

                // Count the number of zombies nearby the potential target
                for (Character character : gameState.characters().values()) {
                    if (character.isZombie() && Helpers.ManhattonDistanceFunction(character.position(), attackeePos) <= 5) {
                        nearbyZombies++;
                    }
                }

                // Favor attacking humans which are surrounded by more zombies
                int score = nearbyZombies - distance;

                if (score > bestScore) {
                    bestTarget = a;
                    bestScore = score;
                }
            }
        }

        if (bestTarget == null) {
            for (AttackAction a : attackActions) {
                if (a.type() == AttackActionType.TERRAIN) {
                    Position attackeePos = gameState.terrains().get(a.attackingId()).position();
                    int distance = Helpers.ChebyshevDistanceFunction(attackeePos, pos);
                    if (distance <= 1)
                        bestTarget = a;
                }
            }
        }

        return bestTarget;
    }



    @Override
    public MoveAction Move(String id, GameState gameState, List<MoveAction> moveActions) {
        Init(id, gameState);
        if (moveActions.isEmpty()) {
            return null;
        }
        Pair<Character, Integer> closestPair = Helpers.FindNearestHuman(self, gameState.characters().values());
        Character closestHuman = getOptimalTarget(gameState);
        Position closestHumanPos = closestHuman.position();
        int closestHumanDistance = closestPair.second;

        AstarStrategy astar = new AstarStrategy();

        // System.out.println(closestHumanPos);
        // System.out.println(closestHumanDistance);

        // Use A* to find a path from currentPos to closestHumanPos
        List<Position> pathToHuman = astar.getPath(gameState, pos, closestHumanPos);

        int moveDistance = Integer.MAX_VALUE;
        MoveAction moveChoice = moveActions.get(0);
        if (!pathToHuman.isEmpty() && pathToHuman.size() > 1) { // Check if pathToHuman has more than just the starting
                                                                // point
            // Determine how many steps the zombie should ideally take
            int steps = Math.min(pathToHuman.size() - 1, 5); // 5 or less if the list is smaller
            Position nextStep = pathToHuman.get(steps); // Get the position after the desired number of steps

            for (MoveAction move : moveActions) {

                int distance = Helpers.ManhattonDistanceFunction(move.destination(), nextStep);
                if (distance < moveDistance) {
                    moveDistance = distance;
                    moveChoice = move;
                }
            }
        }
        return moveChoice;
    }

    // @Override
    // public AttackAction Attack(String id, GameState gameState, List<AttackAction> attackActions) {
    //     Init(id, gameState);
    //     if (attackActions.isEmpty()) {
    //         return null;
    //     }
    //     AttackAction closestTarget = null;
    //     int closestZombieDistance = Integer.MAX_VALUE;
    //     for (AttackAction a : attackActions) {
    //         if (a.type() == AttackActionType.CHARACTER) {
    //             Position attackeePos = gameState.characters().get(a.attackingId()).position();
    //             int distance = Helpers.ManhattonDistanceFunction(attackeePos, pos);

    //             if (distance < closestZombieDistance) {
    //                 closestTarget = a;
    //                 closestZombieDistance = distance;
    //             }
    //         }
    //     }
    //     // if no human can be attack, attack any terrain within one attack range
    //     if (closestTarget == null) {
    //         for (AttackAction a : attackActions) {
    //             if (a.type() == AttackActionType.TERRAIN) {
    //                 Position attackeePos = gameState.terrains().get(a.attackingId()).position();
    //                 int distance = Helpers.ChebyshevDistanceFunction(attackeePos, pos);
    //                 if (distance <= 1)
    //                     closestTarget = a;
    //             }
    //         }
    //     }
    //     if (closestTarget != null) {
    //         return closestTarget;
    //     }
    //     return null;
    // }

    @Override
    public AbilityAction Ability(String id, GameState gameState, List<AbilityAction> abilityActions) {
        return null;
    }

}