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
        // if (self.classType == CharacterClassType.)
        if (moveActions.isEmpty()) {
            return null;
        }
        AstarStrategy astar = new AstarStrategy();
        // List<Position> positions = new ArrayList<>();
        // positions.add(new Position(50, 50));
        // positions.add(new Position(50, 46));
        // positions.add(new Position(50, 50));

        switch (gameState.turn()) {
            case 2:
                return new MoveAction(id, new Position(50, 50));
            // astar.getBestMoveAction(gameState, pos, new Position(50, 50), moveActions,
            // 3);
            case 4:
                return new MoveAction(id, new Position(50, 47));
            // astar.getBestMoveAction(gameState, pos, new Position(50, 47), moveActions,
            // 3);
            case 6:
                return new MoveAction(id, new Position(50, 45));
            case 8:
                return new MoveAction(id, new Position(50, 48));
            case 10:
                return new MoveAction(id, new Position(50, 51));
            case 12:
                return new MoveAction(id, new Position(50, 54));
            case 14:
                return new MoveAction(id, new Position(50, 57));
            case 16:
                return new MoveAction(id, new Position(50, 60));
            case 18:
                return new MoveAction(id, new Position(50, 63));
            case 20:
                return new MoveAction(id, new Position(50, 66));
            case 22:
                return new MoveAction(id, new Position(47, 69));
            case 24:
                return new MoveAction(id, new Position(44, 69));
            case 26:
                return new MoveAction(id, new Position(41,69));
            case 28:
                return new MoveAction(id, new Position(41, 72));
            case 30:
                return new MoveAction(id, new Position(41, 75));
            case 32:
                return new MoveAction(id, new Position(41, 78));
            case 34:
                return new MoveAction(id, new Position(38, 78));
            case 36:
                return new MoveAction(id, new Position(35, 78));
            case 38:
                return new MoveAction(id, new Position(32, 78));
            case 40:
                return new MoveAction(id, new Position(29, 78));
            case 42:
                return new MoveAction(id, new Position(26, 78));
            case 44:
                return new MoveAction(id, new Position(25, 80));
            case 46:
                return new MoveAction(id, new Position(25, 83));
            case 48:
                return new MoveAction(id, new Position(25, 86));
            case 50:
                return new MoveAction(id, new Position(22, 86));
            case 52:
                return new MoveAction(id, new Position(22, 89));
            case 54:
                return new MoveAction(id, new Position(50, 51));
            case 56:
                return new MoveAction(id, new Position(50, 51));
            case 58:
                return new MoveAction(id, new Position(50, 51));
            case 60:
                return new MoveAction(id, new Position(50, 51));
            case 62:
                return new MoveAction(id, new Position(50, 51));
            case 64:
                return new MoveAction(id, new Position(50, 51));
            case 66:
                return new MoveAction(id, new Position(50, 51));
            case 68:
                return new MoveAction(id, new Position(50, 51));
            case 70:
                return new MoveAction(id, new Position(50, 51));
            case 72:
                return new MoveAction(id, new Position(50, 51));
            case 74:
                return new MoveAction(id, new Position(50, 51));
            case 76:
                return new MoveAction(id, new Position(50, 51));
            case 78:
                return new MoveAction(id, new Position(50, 51));
            default:
                return moveActions.get(0);
        }

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
                return null;
            // chooseAbilityAction = HumanHelpers.Heal(gameState, abilityActions);
        }
        return chooseAbilityAction;
    }

}