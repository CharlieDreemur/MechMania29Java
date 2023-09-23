package mech.mania.starterpack.strategy;

import mech.mania.starterpack.game.character.Character;
import mech.mania.starterpack.game.character.action.AbilityActionType;
import mech.mania.starterpack.game.util.Position;
import java.lang.Math;
import mech.mania.starterpack.game.character.action.AbilityAction;
import java.util.List;
import java.util.Map;
import mech.mania.starterpack.strategy.Pair;
import mech.mania.starterpack.game.terrain.Terrain;
import mech.mania.starterpack.game.GameState;
//All needs add obstacle detection
public class Helpers {
    public static int ManhattonDistanceFunction(
            Position a,
            Position b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    // Find distance
    public static int ChebyshevDistanceFunction(
            Position a,
            Position b) {
        return Math.max(Math.abs(a.x() - b.x()), Math.abs(a.y() - b.y()));
    }

    public static AbilityAction Build(
            String id,
            Position _buildPosition) {
        return new AbilityAction(id, null, _buildPosition, AbilityActionType.BUILD_BARRICADE);
    }

    public static AbilityAction Heal(
            String id,
            Character _healTarget) {
        return new AbilityAction(id, _healTarget.id(), null, AbilityActionType.HEAL);
    }

    // Find nearest, returned as map of <people, distance>.
    public static Pair<Character, Integer> FindNearestZombie(Character _human, List<Character> _charList) {
        int _leastDistance = 200;
        Character nearest = null;
        for (Character c : _charList) {
            if(c.zombie()) {
                if (ManhattonDistanceFunction(_human.position(), c.position()) < _leastDistance) {
                    nearest = c;
                    _leastDistance = ManhattonDistanceFunction(_human.position(),c.position());
                }
            };
        }
        Pair<Character, Integer> _nearestZombie = new Pair<Character, Integer>(nearest,
                _leastDistance);
        return _nearestZombie;
    }

    public static Pair<Character, Integer> FindNearestHuman(Character _zombie, List<Character> _charList) {
        int _leastDistance = 200;
        Character nearest = null;
        for (Character c : _charList) {
            if(!c.zombie()) {
                if (ManhattonDistanceFunction(_zombie.position(), c.position()) < _leastDistance) {
                    nearest = c;
                    _leastDistance = ManhattonDistanceFunction(_zombie.position(),c.position());
                }
            };
        }
        Pair<Character, Integer> _nearestHuman = new Pair<Character, Integer>(nearest,
                _leastDistance);
        return _nearestHuman;
    }
    public static List<Pair<Character, Integer>> FindAllZombies(Character _human, List<Character> _charList) {
        List<Pair<Character, Integer>> _allZombieDist = null;
        for (Character c : _charList) {
            if(c.zombie()) {
                Pair<Character, Integer> _indv_zombie = null;
                _indv_zombie = new Pair<>(c,ManhattonDistanceFunction(_human.position(), c.position()));
                _allZombieDist.add(_indv_zombie);
            };
        }
        return _allZombieDist;
    }
    public static List<Pair<Character, Integer>> FindAllHumans(Character _zombie, List<Character> _charList) {
        List<Pair<Character, Integer>> _allHumanDist = null;
        for (Character c : _charList) {
            if(!c.zombie()) {
                Pair<Character, Integer> _indv_human = null;
                _indv_human = new Pair<>(c,ManhattonDistanceFunction(_zombie.position(), c.position()));
                _allHumanDist.add(_indv_human);
            };
        }
        return _allHumanDist;
    }
}

