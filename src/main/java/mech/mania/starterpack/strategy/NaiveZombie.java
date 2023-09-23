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
public class NaiveZombie extends IndividualStrategy {

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

        // System.out.println(closestHumanPos);
        // System.out.println(closestHumanDistance);

        int moveDistance = Integer.MAX_VALUE;
        MoveAction moveChoice = moveActions.get(0);
        for (MoveAction m : moveActions) {
            int distance = Helpers.ManhattonDistanceFunction(m.destination(), closestHumanPos);

            if (distance < moveDistance) {
                moveDistance = distance;
                moveChoice = m;
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
        int closestZombieDistance = Integer.MAX_VALUE;
        for (AttackAction a : attackActions) {
            if (a.type() == AttackActionType.CHARACTER) {
                Position attackeePos = gameState.characters().get(a.attackingId()).position();
                int distance = Helpers.ManhattonDistanceFunction(attackeePos, pos);

                if (distance < closestZombieDistance) {
                    closestHuman = a;
                    closestZombieDistance = distance;
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
