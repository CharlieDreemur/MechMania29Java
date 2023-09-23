package mech.mania.starterpack.strategy;
import mech.mania.starterpack.game.character.Character;
import mech.mania.starterpack.game.util.Position;
import java.lang.Math;
public class Helpers{
    public static int ManhattonDistanceFunction(
        Character _currentPerson,
        Position _destination
    ) {
        return Math.abs(_currentPerson.position().x() - _destination.x()) + Math.abs(_currentPerson.position().y()-_destination.y());
    }
    public static int ChebyshevDistanceFunction(
        Character _currentPerson,
        Position _destination
    ) {
        return Math.max(Math.abs(_currentPerson.position().x() - _destination.x()), Math.abs(_currentPerson.position().y()-_destination.y()));
    }
}
