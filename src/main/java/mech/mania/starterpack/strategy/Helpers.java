package mech.mania.starterpack.strategy;

import mech.mania.starterpack.game.character.Character;
import mech.mania.starterpack.game.character.action.AbilityActionType;
import mech.mania.starterpack.game.util.Position;
import java.lang.Math;
import mech.mania.starterpack.game.character.action.AbilityAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mech.mania.starterpack.strategy.Pair;
import mech.mania.starterpack.game.terrain.Terrain;
import mech.mania.starterpack.game.GameState;
import mech.mania.starterpack.game.character.action.CharacterClassType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    public static int GetSpeed(Character _character) {
        switch (_character.classType()) {
            case NORMAL:
                return 3;
            case MARKSMAN:
                return 3;
            case TRACEUR:
                return 4;
            case MEDIC:
                return 3;
            case BUILDER:
                return 3;
            case DEMOLITIONIST:
                return 3;
            default:
                return 3;
        }
    }

    public static int GetAttackRange(Character _character) {
        switch (_character.classType()) {
            case NORMAL:
                return 4;
            case MARKSMAN:
                return 6;
            case TRACEUR:
                return 2;
            case MEDIC:
                return 3;
            case BUILDER:
                return 4;
            case DEMOLITIONIST:
                return 2;
            default:
                return 4;
        }
    }

    // Find nearest, returned as map of <people, distance>.
    public static Pair<Character, Integer> FindNearestZombie(Character _human, Collection<Character> _charList) {
        int _leastDistance = 200;
        Character nearest = null;
        for (Character c : _charList) {
            if (c.isZombie()) {
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
            if (!c.isZombie()) {
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
            if (c.isZombie()) {
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
            if (!c.isZombie()) {
                Pair<Character, Integer> _indv_human = null;
                _indv_human = new Pair<>(c, ManhattonDistanceFunction(_zombie.position(), c.position()));
                _allHumanDist.add(_indv_human);
            }
            ;
        }
        return _allHumanDist;
    }

    public static List<Map<Character, Character>> canAlwaysStun(Collection<Character> _charList) {
        List<Character> zombieList = new ArrayList<>();
        List<Character> humanList = new ArrayList<>();
        List<Map<Character, Character>> stunList = new ArrayList<>();
        // Init
        for (Character c : _charList) {
            if (c.isZombie()) {
                zombieList.add(c);
            } else {
                humanList.add(c);
            }
        }
        List<Character> attackHumansList = new ArrayList<Character>();
        for (int i = 0; i < 3; i++) {
            Map<Character, Character> stunMap = new HashMap<Character, Character>();
            List<Character> attackZombieList = new ArrayList<Character>(zombieList);
            for (Character human : humanList) {
                if (attackHumansList.contains(human)) {
                    continue;
                }
                boolean isZombieInRange = false;
                Character farestTargetZombie = null;
                int farestDistance = 0;
                for (Pair<Character, Integer> currentZombieTarget : FindAllZombies(human, zombieList)) {
                    if (currentZombieTarget.second < (i + 1) * GetSpeed(human) + GetAttackRange(human)) {
                        isZombieInRange = true;
                        if (farestDistance >= (int) currentZombieTarget.second) {
                            farestDistance = (int) currentZombieTarget.second;
                            farestTargetZombie = currentZombieTarget.first;
                        }
                    }
                }
                if (isZombieInRange) {
                    attackHumansList.add(human);
                    attackZombieList.remove(farestTargetZombie);
                    stunMap.put(human, farestTargetZombie);
                }
                if (attackZombieList.isEmpty()) {
                    continue;
                } else {
                    return Collections.emptyList();
                    //return stunList;
                }
            }
        }
        //return stunList;
        return Collections.emptyList();
    }
    public static List<Position> startingBuild = 
    new ArrayList<>(Arrays.asList(
        new Position(46,50),
        new Position(47,50),
        new Position(47,49),
        new Position(47,51)
    ));
    // step 0 : List zombieList; //for (Character c : _charList) {if zombie() add to
    // zombieList}
    // List humanList//all non-normal humans
    // AttackerList = {};
    // step 1 : find first_round attackers :
    // zombieList1 = zombieList.copy
    // for(human : humans) {
    // boolean zombie_in_range = 0; farest_zombie_in_attack_range = null;
    // farest_distance = 0;
    // for(pair currentZombieTarget :FindAllZombies(human)) {
    // if (currentZombieTarget.second < human.movespeed + human.attackrange) {
    // zombie_in_range = 1; if(farest_distance <= currentZombieTarget.second)
    // {farest_distance = currentZombieTarget.second; farest_zombie_in_attack_range
    // = currentZombieTarget;}
    // }
    // }
    // if(zombie_in_range)
    // {AttackerList.add(human);zombieList1.remove(farest_zombie_in_attack_range);}
    // }
    // if (zombieList1.empty) continue; else {return false;}
    // step 2 : find second_round attackers :
    // zombieList2 = zombieList.copy
    // for(human : humans) {
    // if(human not in AttackerList) {
    // boolean zombie_in_range = 0; farest_zombie_in_attack_range = null;
    // farest_distance = 0;
    // for(pair currentZombieTarget :FindAllZombies(human)) {
    // if (currentZombieTarget.second < 2*human.movespeed + human.attackrange) {
    // AttackerList.add(human); zombie_in_range = 1; if(farest_distance <=
    // currentZombieTarget.second) {farest_distance = currentZombieTarget.second;
    // farest_zombie_in_attack_range = currentZombieTarget;}
    // }
    // }
    // if(zombie_in_range)
    // {AttackerList.add(human);zombieList2.remove(farest_zombie_in_attack_range)}
    // }
    // }
    // if (zombieList2.empty) continue; else {return false;}
    // step 3 : find third_round attackers :
    // zombieList3 = zombieList.copy
    // for(human : humans) {
    // if(human not in AttackerList) {
    // boolean zombie_in_range = 0; farest_zombie_in_attack_range = null;
    // farest_distance = 0;
    // for(pair currentZombieTarget :FindAllZombies(human)) {
    // if (currentZombieTarget.second < 3*human.movespeed + human.attackrange) {
    // AttackerList.add(human); zombie_in_range = 1; if(farest_distance <=
    // currentZombieTarget.second) {farest_distance = currentZombieTarget.second;
    // farest_zombie_in_attack_range = currentZombieTarget;}
    // }
    // }
    // if(zombie_in_range)
    // {AttackerList.add(human);zombieList3.remove(farest_zombie_in_attack_range)}
    // }
    // }
    // if (zombieList3.empty) return true; else {return false;}
    // }
}
