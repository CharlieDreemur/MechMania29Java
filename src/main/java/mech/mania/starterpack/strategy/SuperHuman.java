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
                return new MoveAction(id, new Position(50, 66));
            case 24:
                return new MoveAction(id, new Position(47, 69));
            case 26:
                return new MoveAction(id, new Position(44, 69));
            case 28:
                return new MoveAction(id, new Position(41, 69));
            case 30:
                return new MoveAction(id, new Position(41, 72));
            case 32:
                return new MoveAction(id, new Position(41, 75));
            case 34:
                return new MoveAction(id, new Position(41, 78));
            case 36:
                return new MoveAction(id, new Position(38, 78));
            case 38:
                return new MoveAction(id, new Position(35, 78));
            case 40:
                return new MoveAction(id, new Position(32, 78));
            case 42:
                return new MoveAction(id, new Position(29, 78));
            case 44:
                return new MoveAction(id, new Position(26, 78));
            case 46:
                return new MoveAction(id, new Position(25, 80));
            case 48:
                return new MoveAction(id, new Position(25, 83));
            case 50:
                return new MoveAction(id, new Position(25, 86));
            case 52:
                return new MoveAction(id, new Position(22, 86));
            case 54:
                return new MoveAction(id, new Position(22, 89));
            case 56:
                return new MoveAction(id, new Position(21, 91));
            case 58:
                return new MoveAction(id, new Position(21, 94));
            case 60:
                return new MoveAction(id, new Position(21, 97));
            case 62:
                return new MoveAction(id, new Position(18, 97));
            case 64:
                return new MoveAction(id, new Position(15, 97));
            case 66:
                return new MoveAction(id, new Position(13, 98));
            case 68:
                return new MoveAction(id, new Position(10, 98));
            case 70:
                return new MoveAction(id, new Position(7, 98));
            case 72:
                return new MoveAction(id, new Position(5, 97));
            case 74:
                return new MoveAction(id, new Position(4, 95));
            case 76:
                return new MoveAction(id, new Position(4, 92));
            case 78:
                return new MoveAction(id, new Position(4, 89));
            case 80:
                return new MoveAction(id, new Position(4, 89));
            case 82:
                return new MoveAction(id, new Position(5, 87));
            case 84:
                return new MoveAction(id, new Position(8, 87));
            case 86:
                return new MoveAction(id, new Position(11, 87));
            case 88:
                return new MoveAction(id, new Position(13, 88));
            case 90:
                return new MoveAction(id, new Position(15, 89));
            case 92:
                return new MoveAction(id, new Position(18, 89));
            case 94:
                return new MoveAction(id, new Position(19, 91));
            case 96:
                return new MoveAction(id, new Position(21, 91));
            case 98:
                return new MoveAction(id, new Position(21, 94));
            case 100:
                return new MoveAction(id, new Position(21, 97));
            case 102:
                return new MoveAction(id, new Position(18, 97));
            case 104:
                return new MoveAction(id, new Position(15, 97));
            case 106:
                return new MoveAction(id, new Position(13, 98));
            case 108:
                return new MoveAction(id, new Position(10, 98));
            case 110:
                return new MoveAction(id, new Position(7, 98));
            case 112:
                return new MoveAction(id, new Position(5, 97));
            case 114:
                return new MoveAction(id, new Position(4, 95));
            case 116:
                return new MoveAction(id, new Position(4, 92));
            case 118:
                return new MoveAction(id, new Position(4, 89));
            case 120:
                return new MoveAction(id, new Position(4, 89));
            case 122:
                return new MoveAction(id, new Position(5, 87));
            case 124:
                return new MoveAction(id, new Position(8, 87));
            case 126:
                return new MoveAction(id, new Position(11, 87));
            case 128:
                return new MoveAction(id, new Position(13, 88));
            case 130:
                return new MoveAction(id, new Position(15, 89));
            case 132:
                return new MoveAction(id, new Position(18, 89));
            case 134:
                return new MoveAction(id, new Position(19, 91));
            case 136:
                return new MoveAction(id, new Position(21, 91));
            case 138:
                return new MoveAction(id, new Position(21, 94));
            case 140:
                return new MoveAction(id, new Position(21, 97));
            case 142:
                return new MoveAction(id, new Position(18, 97));
            case 144:
                return new MoveAction(id, new Position(15, 97));
            case 146:
                return new MoveAction(id, new Position(13, 98));
            case 148:
                return new MoveAction(id, new Position(10, 98));
            case 150:
                return new MoveAction(id, new Position(7, 98));
            case 152:
                return new MoveAction(id, new Position(5, 97));
            case 154:
                return new MoveAction(id, new Position(4, 95));
            case 156:
                return new MoveAction(id, new Position(4, 92));
            case 158:
                return new MoveAction(id, new Position(4, 89));
            case 160:
                return new MoveAction(id, new Position(4, 89));
            case 162:
                return new MoveAction(id, new Position(5, 87));
            case 164:
                return new MoveAction(id, new Position(8, 87));
            case 166:
                return new MoveAction(id, new Position(11, 87));
            case 168:
                return new MoveAction(id, new Position(13, 88));
            case 170:
                return new MoveAction(id, new Position(15, 89));
            case 172:
                return new MoveAction(id, new Position(18, 89));
            case 174:
                return new MoveAction(id, new Position(19, 91));
            case 176:
                return new MoveAction(id, new Position(21, 91));
            case 178:
                return new MoveAction(id, new Position(21, 94));
            case 180:
                return new MoveAction(id, new Position(21, 97));
            case 182:
                return new MoveAction(id, new Position(18, 97));
            case 184:
                return new MoveAction(id, new Position(15, 97));
            case 186:
                return new MoveAction(id, new Position(13, 98));
            case 188:
                return new MoveAction(id, new Position(10, 98));
            case 190:
                return new MoveAction(id, new Position(7, 98));
            case 192:
                return new MoveAction(id, new Position(5, 97));
            case 194:
                return new MoveAction(id, new Position(4, 95));
            case 196:
                return new MoveAction(id, new Position(4, 92));
            case 198:
                return new MoveAction(id, new Position(4, 89));
            case 200:
                return new MoveAction(id, new Position(4, 89));
            default:
                Pair<Character, Integer> closestPair = Helpers.FindNearestZombie(self, gameState.characters().values());
                Character closestZombie = closestPair.first;
                Position closestZombiePos = closestZombie.position();
                int closestZombieDistance = closestPair.second;
                MoveAction best = HumanHelpers.EscapeWalk(pos, closestZombiePos, moveActions);
                return best;
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