package mech.mania.starterpack.strategy;

import java.util.List;

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
        Position closestHumanPos = pos;
        int closestHumanDistance = Integer.MAX_VALUE;

        for (Character c : gameState.characters().values()) {
            if (c.isZombie()) {
                continue;
            }

            int distance = Math.abs(c.position().x() - pos.y())
                    + Math.abs(c.position().x() - pos.y());

            if (distance < closestHumanDistance) {
                closestHumanPos = c.position();
                closestHumanDistance = distance;
            }
        }

        int moveDistance = Integer.MAX_VALUE;
        MoveAction moveChoice = moveActions.get(0);

        for (MoveAction m : moveActions) {
            int distance = Math.abs(m.destination().x() - closestHumanPos.x())
                    + Math.abs(m.destination().y() - closestHumanPos.y());

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
        int closestZombieDistance = 404;
        for (AttackAction a : attackActions) {
            if (a.type() == AttackActionType.CHARACTER) {
                Position attackeePos = gameState.characters().get(a.attackingId()).position();
                int distance = Math.abs(attackeePos.x() - pos.x())
                        + Math.abs(attackeePos.y() - pos.y());

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
