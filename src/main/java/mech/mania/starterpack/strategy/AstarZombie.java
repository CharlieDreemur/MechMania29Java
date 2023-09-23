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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * A simple human which runs away from zombies
 */
public class AstarZombie extends IndividualStrategy {

    @Override
    public void Init(String id, GameState gameState) {
        super.Init(id, gameState);
    }

    @Override
    public MoveAction Move(String id, GameState gameState, List<MoveAction> moveActions) {
        Init(id, gameState);
        if (moveActions.isEmpty()) {
            return null;
        }
        Pair<Character, Integer> closestPair = Helpers.FindNearestHuman(self, gameState.characters().values());
        Character closestHuman = closestPair.first;
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

    @Override
    public AttackAction Attack(String id, GameState gameState, List<AttackAction> attackActions) {
        Init(id, gameState);
        if (attackActions.isEmpty()) {
            return null;
        }
        AttackAction closestTarget = null;
        int closestZombieDistance = Integer.MAX_VALUE;
        for (AttackAction a : attackActions) {
            if (a.type() == AttackActionType.CHARACTER) {
                Position attackeePos = gameState.characters().get(a.attackingId()).position();
                int distance = Helpers.ManhattonDistanceFunction(attackeePos, pos);

                if (distance < closestZombieDistance) {
                    closestTarget = a;
                    closestZombieDistance = distance;
                }
            }
        }
        // if no human can be attack, attack any terrain within one attack range
        if (closestTarget == null) {
            for (AttackAction a : attackActions) {
                if (a.type() == AttackActionType.TERRAIN) {
                    Position attackeePos = gameState.terrains().get(a.attackingId()).position();
                    int distance = Helpers.ChebyshevDistanceFunction(attackeePos, pos);
                    if (distance <= 1)
                        closestTarget = a;
                }
            }
        }
        if (closestTarget != null) {
            return closestTarget;
        }
        return null;
    }

    @Override
    public AbilityAction Ability(String id, GameState gameState, List<AbilityAction> abilityActions) {
        return null;
    }

}