package mech.mania.starterpack.strategy;
import java.util.List;
import java.util.Map;

import mech.mania.starterpack.game.GameState;
import mech.mania.starterpack.game.util.Position;
import mech.mania.starterpack.strategy.Helpers;
import mech.mania.starterpack.game.character.action.CharacterClassType;
import mech.mania.starterpack.game.character.Character;
import mech.mania.starterpack.game.character.MoveAction;

public class HumanHelpers {
    public static MoveAction EscapeWalk(Position selfPos, Position enemyPos, List<MoveAction> possibleMoves) {
        float deltaX = (float)selfPos.x() - enemyPos.x();
        float deltaY = selfPos.y() - enemyPos.y();
        // System.out.println(selfPos.x() - enemyPos.x());

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
                continue;  // Ignore fellow humans
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
    
    
}