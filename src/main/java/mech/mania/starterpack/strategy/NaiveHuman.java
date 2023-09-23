package mech.mania.starterpack.strategy;

import java.util.*;

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
    public MoveAction Move(String id, GameState gameState,
            List<MoveAction> moveActions) {
        Init(id, gameState);
                // Handle the case where there is no move to be made, such as when stunned
        if (moveActions.isEmpty()) {
            return null;
        }
        Pair<Character, Integer> closestPair = Helpers.FindNearestZombie(self, gameState.characters().values());
        Character closestZombie = closestPair.first;
        Position closestZombiePos = closestZombie.position();
        int closestZombieDistance = closestPair.second;
        MoveAction best = HumanHelpers.EscapeWalk(pos, closestZombiePos, moveActions);
        return best;
    }

    @Override
    public AttackAction Attack(String id, GameState gameState, List<AttackAction> attackActions) {
        Init(id, gameState);
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
                //Distance between the attackpos and self
                int distance = Helpers.ManhattonDistanceFunction(attackeePos, pos);

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
    public AbilityAction Ability(String id, GameState gameState, List<AbilityAction> abilityActions) {
        Init(id, gameState);
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
