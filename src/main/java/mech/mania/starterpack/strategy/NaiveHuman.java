package mech.mania.starterpack.strategy;

import java.util.List;

import mech.mania.starterpack.game.GameState;
import mech.mania.starterpack.game.character.Character;
import mech.mania.starterpack.game.character.action.AbilityAction;
import mech.mania.starterpack.game.character.action.AttackAction;
import mech.mania.starterpack.game.character.action.AttackActionType;
import mech.mania.starterpack.game.util.Position;
import mech.mania.starterpack.game.character.MoveAction;

public class NaiveHuman extends IndividualStrategy {

    @Override
    public void Init(String id, GameState gameState) {
        super.Init(id, gameState);
    }

    @Override
    public MoveAction Move(GameState gameState,
            List<MoveAction> moveActions) {
        // Handle the case where there is no move to be made, such as when stunned
        if (moveActions.isEmpty()) {
            return null;
        }
        Position closestZombiePos = pos;
        int closestZombieDistance = Integer.MAX_VALUE;

        // Find the closest zombie
        for (Character c : gameState.characters().values()) {
            if (!c.isZombie()) {
                continue; // Ignore fellow humans
            }

            int distance = Math.abs(c.position().x() - pos.x()) +
                    Math.abs(c.position().y() - pos.y());

            if (distance < closestZombieDistance) {
                closestZombiePos = c.position();
                closestZombieDistance = distance;
            }
        }

        int moveDistance = -1;
        MoveAction moveChoice = moveActions.get(0);

        // Choose a move action that takes the character further from the closest zombie
        for (MoveAction m : moveActions) {
            int distance = Math.abs(m.destination().x() - closestZombiePos.x()) +
                    Math.abs(m.destination().y() - closestZombiePos.y());

            if (distance > moveDistance) {
                moveDistance = distance;
                moveChoice = m;
            }
        }

        return moveChoice;
    }

    @Override
    public AttackAction Attack(GameState gameState, List<AttackAction> attackActions) {
        // Handle the case where there is no attack to be made, such as when stunned
        if (attackActions.isEmpty()) {
            return null;
        }
        AttackAction closestZombie = null;
        int closestZombieDistance = Integer.MAX_VALUE;

        // Find the closest zombie to attack
        for (AttackAction a : attackActions) {
            if (a.type() == AttackActionType.CHARACTER) {
                Position attackeePos = gameState.characters().get(a.attackingId()).position();

                int distance = Math.abs(attackeePos.x() - pos.x()) +
                        Math.abs(attackeePos.y() - pos.y());

                if (distance < closestZombieDistance) {
                    closestZombie = a;
                    closestZombieDistance = distance;
                }
            }
        }

        if (closestZombie != null) {
            return closestZombie;
        }
        return null;
    }

    @Override
    public AbilityAction Ability(GameState gameState, List<AbilityAction> abilityActions) {
        // Handle the case where there is no ability to be made, such as when stunned
        if (abilityActions.isEmpty()) {
            return null;
        }
        AbilityAction humanTarget = abilityActions.get(0);
        int leastHealth = Integer.MAX_VALUE;

        // Find the human target with the least health to heal
        for (AbilityAction a : abilityActions) {
            int health = gameState.characters().get(a.characterIdTarget()).health();

            if (health < leastHealth) {
                humanTarget = a;
                leastHealth = health;
            }
        }

        return humanTarget;
    }

}
