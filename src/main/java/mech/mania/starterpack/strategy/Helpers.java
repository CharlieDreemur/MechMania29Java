package mech.mania.starterpack.strategy;

import mech.mania.starterpack.game.character.Character;
import mech.mania.starterpack.game.character.action.AbilityActionType;
import mech.mania.starterpack.game.util.Position;
import java.lang.Math;
import mech.mania.starterpack.game.character.action.AbilityAction;

import java.util.Collection;
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
    public static Pair<Character, Integer> FindNearestZombie(Character _human, Collection<Character> _charList) {
        int _leastDistance = 200;
        Character nearest = null;
        for (Character c : _charList) {
            if (c.zombie()) {
                if (ManhattonDistanceFunction(_human.position(), c.position()) < _leastDistance) {
                    nearest = c;
                    _leastDistance = ManhattonDistanceFunction(_human.position(), c.position());
                }
            }
            ;
        }
        Pair<Character, Integer> _nearestZombie = new Pair<Character, Integer>(nearest,
                _leastDistance);
        return _nearestZombie;
    }

    public static Pair<Character, Integer> FindNearestHuman(Character _zombie, Collection<Character> _charList) {
        int _leastDistance = 200;
        Character nearest = null;
        for (Character c : _charList) {
            if (!c.zombie()) {
                if (ManhattonDistanceFunction(_zombie.position(), c.position()) < _leastDistance) {
                    nearest = c;
                    _leastDistance = ManhattonDistanceFunction(_zombie.position(), c.position());
                }
            }
            ;
        }
        Pair<Character, Integer> _nearestHuman = new Pair<Character, Integer>(nearest,
                _leastDistance);
        return _nearestHuman;
    }

    public static List<Pair<Character, Integer>> FindAllZombies(Character _human, Collection<Character> _charList) {
        List<Pair<Character, Integer>> _allZombieDist = null;
        for (Character c : _charList) {
            if (c.zombie()) {
                Pair<Character, Integer> _indv_zombie = null;
                _indv_zombie = new Pair<>(c, ManhattonDistanceFunction(_human.position(), c.position()));
                _allZombieDist.add(_indv_zombie);
            }
            ;
        }
        return _allZombieDist;
    }

    public static List<Pair<Character, Integer>> FindAllHumans(Character _zombie, Collection<Character> _charList) {
        List<Pair<Character, Integer>> _allHumanDist = null;
        for (Character c : _charList) {
            if (!c.zombie()) {
                Pair<Character, Integer> _indv_human = null;
                _indv_human = new Pair<>(c, ManhattonDistanceFunction(_zombie.position(), c.position()));
                _allHumanDist.add(_indv_human);
            }
            ;
        }
        return _allHumanDist;
    }

    public static boolean canAlwaysStun(Collection<Character> _charList){
        for (Character c:_charList)
         step 0 :  List zombieList; //for (Character c : _charList) {if zombie() add to zombieList}
    List humanList//all non-normal humans
    AttackerList = {};
    step 1 : find first_round attackers : 
    zombieList1 = zombieList.copy
    for(human : humans) {
        boolean zombie_in_range = 0; farest_zombie_in_attack_range = null; farest_distance = 0;
        for(pair currentZombieTarget :FindAllZombies(human)) {
            if (currentZombieTarget.second  < human.movespeed + human.attackrange) {
                zombie_in_range = 1; if(farest_distance <= currentZombieTarget.second) {farest_distance = currentZombieTarget.second; farest_zombie_in_attack_range = currentZombieTarget;}
            }
        }
        if(zombie_in_range) {AttackerList.add(human);zombieList1.remove(farest_zombie_in_attack_range);}
    }
    if (zombieList1.empty) continue; else {return false;}
    step 2 : find second_round attackers : 
    zombieList2 = zombieList.copy
    for(human : humans) {
        if(human not in AttackerList) {
            boolean zombie_in_range = 0; farest_zombie_in_attack_range = null; farest_distance = 0;
            for(pair currentZombieTarget :FindAllZombies(human)) {
                if (currentZombieTarget.second  < human.movespeed + human.attackrange) {
                    AttackerList.add(human); zombie_in_range = 1; if(farest_distance <= currentZombieTarget.second) {farest_distance = currentZombieTarget.second; farest_zombie_in_attack_range = currentZombieTarget;}
                }
            }
            if(zombie_in_range) {AttackerList.add(human);zombieList2.remove(farest_zombie_in_attack_range)}
        }
    }
    if (zombieList2.empty) continue; else {return false;}
    step 3 : find third_round attackers : 
    zombieList3 = zombieList.copy
    for(human : humans) {
        if(human not in AttackerList) {
            boolean zombie_in_range = 0; farest_zombie_in_attack_range = null; farest_distance = 0;
            for(pair currentZombieTarget :FindAllZombies(human)) {
                if (currentZombieTarget.second  < human.movespeed + human.attackrange) {
                    AttackerList.add(human); zombie_in_range = 1; if(farest_distance <= currentZombieTarget.second) {farest_distance = currentZombieTarget.second; farest_zombie_in_attack_range = currentZombieTarget;}
                }
            }
            if(zombie_in_range) {AttackerList.add(human);zombieList3.remove(farest_zombie_in_attack_range)}
        }
    }
    if (zombieList3.empty) return true; else {return false;}
    }
}
