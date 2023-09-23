package mech.mania.starterpack.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mech.mania.starterpack.game.GameState;
import mech.mania.starterpack.game.util.Position;
import mech.mania.starterpack.strategy.Helpers;
import mech.mania.starterpack.game.character.action.AbilityAction;
import mech.mania.starterpack.game.character.action.AbilityActionType;
import mech.mania.starterpack.game.character.action.CharacterClassType;
import mech.mania.starterpack.game.character.Character;
import mech.mania.starterpack.game.character.MoveAction;

public class HumanHelpers {
    public static MoveAction EscapeWalk(Position selfPos, Position enemyPos, List<MoveAction> possibleMoves) {
        float deltaX = (float) selfPos.x() - enemyPos.x();
        float deltaY = selfPos.y() - enemyPos.y();

        double length = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        double unitX = deltaX / length;
        double unitY = deltaY / length;

        double maxDotProduct = Double.NEGATIVE_INFINITY; // Initialize with a very small value
        Position furthestPoint = null;
        MoveAction best = possibleMoves.get(0);
        for (MoveAction possible : possibleMoves) {
            // Calculate the vector from the current position to the future position
            Position futureDest = possible.destination();
            float dx = futureDest.x() - selfPos.x();
            float dy = futureDest.y() - selfPos.y();
            // Calculate the dot product between the unit vector and the position vector
            double dotProduct = dx * unitX + dy * unitY;

            // Check if this position goes further along the unit vector
            if (dotProduct > maxDotProduct) {
                maxDotProduct = dotProduct;
                furthestPoint = futureDest;
                best = possible;
            }
        }

        return best;
    }

    public static Position cloestZombie(GameState gameState, Position selfPos) {
        Position closestZombiePos = selfPos;
        int closestZombieDistance = Integer.MAX_VALUE;
        // Find the closest zombie
        for (Character c : gameState.characters().values()) {
            if (!c.isZombie()) {
                continue; // Ignore fellow humans
            }
            int distance = Math.abs(c.position().x() - selfPos.x()) +
                    Math.abs(c.position().y() - selfPos.y());
            if (distance < closestZombieDistance) {
                closestZombiePos = c.position();
                closestZombieDistance = distance;
            }
        }
        return closestZombiePos;
    }

    public static boolean canZombieKillHuman(Position selfPos, Position enemyPos) {
        final int MOVE_RANGE = 5;
        final int ATTACK_RANGE = 1;
        int manhattanDistance = Math.abs(selfPos.x() - enemyPos.x()) + Math.abs(selfPos.y() - enemyPos.y());
        if (manhattanDistance <= MOVE_RANGE + ATTACK_RANGE) {
            return true;
        } else {
            return false;
        }
    }

    public static AbilityAction Heal(GameState gameState, List<AbilityAction> abilities) {
        AbilityAction humanTarget = abilities.get(0);
        int leastHealth = Integer.MAX_VALUE;
        // Find the human target with the least health to heal
        for (AbilityAction a : abilities) {
            int health = gameState.characters().get(a.characterIdTarget()).health();
            if (health < leastHealth) {
                humanTarget = a;
                leastHealth = health;
            }
        }
        return humanTarget;
    }

    public static AbilityAction SuperBuild(GameState gameState, List<AbilityAction> abilities) {
        List<Position> positions = new ArrayList<Position>();
        positions.add(new Position(50, 42));
        positions.add(new Position(49, 43));
        positions.add(new Position(50, 43));
        positions.add(new Position(51, 43));
        positions.add(new Position(50, 44));
        positions.add(new Position(50, 56));
        positions.add(new Position(50, 57));
        positions.add(new Position(49, 58));
        positions.add(new Position(50, 58));
        positions.add(new Position(51, 58));
        positions.add(new Position(24, 80));
        positions.add(new Position(25, 80));
        positions.add(new Position(26, 80));
        positions.add(new Position(24, 81));
        positions.add(new Position(25, 81));
        for (AbilityAction possible : abilities) {
            for (Position pos : positions) {
                if (possible.positionalTarget().equals(pos)) {
                    return possible;
                }
            }
        }
        return null;
    }

    public static AbilityAction Build(GameState gameState, List<AbilityAction> abilities) {
        AbilityAction builderTarget = abilities.get(0);
        Character builder = gameState.characters().get(builderTarget.executingCharacterId());
        Character zombie = Helpers.FindNearestZombie(builder, gameState.characters().values()).first;
        float dx = (float) builder.position().x() - zombie.position().x();
        float dy = builder.position().y() - zombie.position().y();
        double length = Math.sqrt(dx * dx + dy * dy);
        double unitX = dx / length;
        double unitY = dy / length;

        double reverseUX = -unitX;
        double reverseUY = -unitY;
        double maxDotProduct = Double.NEGATIVE_INFINITY;
        for (AbilityAction possible : abilities) {
            // Calculate the vector from the current position to the future position
            Position futureDest = possible.positionalTarget();
            float dex = futureDest.x() - builder.position().x();
            float dey = futureDest.y() - builder.position().y();
            // Calculate the dot product between the unit vector and the position vector
            double dotProduct = dex * reverseUX + dey * reverseUY;

            // Check if this position goes further along the unit vector
            if (dotProduct > maxDotProduct) {
                maxDotProduct = dotProduct;
                builderTarget = possible;
            }
        }

        // public static Pair<Character, Integer> FindNearestZombie(Character _human,
        // Collection<Character> _charList)
        // Position builderPosition =
        // gameState.characters().get(positionTarget.executingCharacterId()).position();
        // Position tmp =
        return builderTarget;
    }

}