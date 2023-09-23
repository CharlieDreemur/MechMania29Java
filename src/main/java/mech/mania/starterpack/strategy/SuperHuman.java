package mech.mania.starterpack.strategy;

import java.util.*;

import mech.mania.starterpack.game.GameState;
import mech.mania.starterpack.game.character.Character;
import mech.mania.starterpack.game.character.action.AbilityAction;
import mech.mania.starterpack.game.character.action.AbilityActionType;
import mech.mania.starterpack.game.character.action.AttackAction;
import mech.mania.starterpack.game.character.action.AttackActionType;
import mech.mania.starterpack.game.util.Position;
import mech.mania.starterpack.game.character.MoveAction;
import mech.mania.starterpack.strategy.Helpers;
import java.util.Collections;
public class SuperHuman extends IndividualStrategy {

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
        System.out.println(gameState.turn());
        System.out.println(gameState.characters().values().isEmpty());
        System.out.println(Helpers.canAlwaysStun(gameState.characters().values()).isEmpty());
        //if (gameState.turn() == 2) {
            
        return new MoveAction(id, new Position(50, 50));
        //}
        // if (gameState.turn() == 4) {
        //     // System.out.println(Helpers.canAlwaysStun(gameState.characters().values()).isEmpty());
        //     return new MoveAction(id, new Position(52, 50));
        // }
        // Pair<Character, Integer> closestPair = Helpers.FindNearestZombie(self, gameState.characters().values());
        // Character closestZombie = closestPair.first;
        // Position closestZombiePos = closestZombie.position();
        // int closestZombieDistance = closestPair.second;
        // MoveAction best = HumanHelpers.EscapeWalk(pos, closestZombiePos, moveActions);
        // return best;
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
                // Distance between the attackpos and self
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
        if (abilityActions.isEmpty()) {
            return null;
        }
        AbilityActionType type = abilityActions.get(0).type();
        AbilityAction chooseAbilityAction = abilityActions.get(0);
        switch (type) {
            case BUILD_BARRICADE:
                chooseAbilityAction = HumanHelpers.SuperBuild(gameState, abilityActions);
            case HEAL:
                chooseAbilityAction = HumanHelpers.Heal(gameState, abilityActions);
        }
        return chooseAbilityAction;
    }

}