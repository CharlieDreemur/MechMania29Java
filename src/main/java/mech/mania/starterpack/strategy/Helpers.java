package mech.mania.starterpack.strategy;

import mech.mania.starterpack.game.character.Character;
import mech.mania.starterpack.game.character.action.AbilityActionType;
import mech.mania.starterpack.game.util.Position;
import java.lang.Math;
import mech.mania.starterpack.game.character.action.AbilityAction;
import java.util.List;
import java.util.Map;
import mech.mania.starterpack.strategy.Pair;
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
    public static Pair<Character, Integer> FindNearestZombie(Character _human, List<Character> _zombieList) {
        int _leastDistance = 200;
        int nearest = 0;
        for (int i = 0; i < _zombieList.size(); i++) {
            if (ManhattonDistanceFunction(_human.position(), _zombieList.get(i).position()) < _leastDistance) {
                nearest = i;
                _leastDistance = ManhattonDistanceFunction(_human.position(), _zombieList.get(i).position());
            }
        }
        Pair<Character, Integer> _nearestZombie = new Pair<Character, Integer>(_zombieList.get(nearest),
                _leastDistance);
        return _nearestZombie;
    }

    public static Pair<Character, Integer> FindNearestHuman(Character _zombie, List<Character> _humanList) {
        int _leastDistance = 200;
        int nearest = 0;
        for (int i = 0; i < _humanList.size(); i++) {
            if (ManhattonDistanceFunction(_zombie.position(), _humanList.get(i).position()) < _leastDistance) {
                nearest = i;
                _leastDistance = ManhattonDistanceFunction(_zombie.position(), _humanList.get(i).position());
            }
        }
        Pair<Character, Integer> _nearestHuman = new Pair<Character, Integer>(_humanList.get(nearest), _leastDistance);
        return _nearestHuman;
    }

    public static List<Pair<Character, Integer>> FindAllZombies(Character _human, List<Character> _zombieList) {
        List<Pair<Character, Integer>> _allZombieDist = null;
        for(int i = 0; i < _zombieList.size();i++) {
            Pair<Character, Integer> _indv_zombie = null;
            _indv_zombie = new Pair<>(_zombieList.get(i),ManhattonDistanceFunction(_human.position(), _zombieList.get(i).position()));
            _allZombieDist.add(_indv_zombie);
        }
        return _allZombieDist;
    }
    public static List<Pair<Character, Integer>> FindAllHumans(Character _zombie, List<Character> _humanList) {
        List<Pair<Character, Integer>> _allHumanDist = null;
        for(int i = 0; i < _humanList.size();i++) {
            Pair<Character, Integer> _indv_human = null;
            _indv_human = new Pair<>(_humanList.get(i),ManhattonDistanceFunction(_zombie.position(), _humanList.get(i).position()));
            _allHumanDist.add(_indv_human);
        }
        return _allHumanDist;
    }
}

