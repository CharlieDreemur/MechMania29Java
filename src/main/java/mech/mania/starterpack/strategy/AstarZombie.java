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
    public MoveAction Move(GameState gameState, List<MoveAction> moveActions) {
        if (moveActions.isEmpty()) {
            return null;
        }
        Position closestHumanPos = new Position(114514, 114514);
        int closestHumanDistance = Integer.MAX_VALUE;

        for (Character c : gameState.characters().values()) {
            if (c.zombie()) {
                continue;
            }
            // System.out.println("c.position:"+c.position());
            int distance = Helpers.ManhattonDistanceFunction(c.position(), closestHumanPos);
            // System.out.println("distance:"+distance);
            if (distance < closestHumanDistance) {
                closestHumanPos = c.position();
                closestHumanDistance = distance;
            }
        }

        AstarStrategy astar = new AstarStrategy();

        // System.out.println(closestHumanPos);
        // System.out.println(closestHumanDistance);

        // Use A* to find a path from currentPos to closestHumanPos
        List<Position> pathToHuman = astar.getPath(gameState, pos, closestHumanPos);



        int moveDistance = Integer.MAX_VALUE;
        MoveAction moveChoice = moveActions.get(0);
        if (!pathToHuman.isEmpty() && pathToHuman.size() > 1) { // Check if pathToHuman has more than just the starting point
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
    public AttackAction Attack(GameState gameState, List<AttackAction> attackActions) {
        if (attackActions.isEmpty()) {
            return null;
        }
        AttackAction closestHuman = null;
        int closestHumanDistance = 404;
        for (AttackAction a : attackActions) {
            if (a.type() == AttackActionType.CHARACTER) {
                Position attackeePos = gameState.characters().get(a.attackingId()).position();
                int distance = Helpers.ManhattonDistanceFunction(attackeePos, pos);

                if (distance < closestHumanDistance) {
                    closestHuman = a;
                    closestHumanDistance = distance;
                }
            }
        }

        if (closestHuman != null) {
            return closestHuman;
        }
        return null;
    }

    @Override
    public AbilityAction Ability(GameState gameState, List<AbilityAction> abilityActions) {
        return null;
    }

}