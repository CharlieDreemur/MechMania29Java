package mech.mania.starterpack.strategy;

import mech.mania.starterpack.game.GameState;
import mech.mania.starterpack.game.character.Character;
import mech.mania.starterpack.game.character.MoveAction;
import mech.mania.starterpack.game.character.action.AbilityAction;
import mech.mania.starterpack.game.character.action.AttackAction;
import mech.mania.starterpack.game.character.action.AttackActionType;
import mech.mania.starterpack.game.character.action.CharacterClassType;
import mech.mania.starterpack.game.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SimpleHumanStrategy implementation for bot decision-making.
 */
public class AstarZombieStrategy extends Strategy {
    AstarZombie zombie = new AstarZombie();

    @Override
    public Map<CharacterClassType, Integer> decideCharacterClasses(List<CharacterClassType> possibleClasses,
            int numToPick, int maxPerSameClass) {
        return null;
    }

    @Override
    public List<MoveAction> decideMoves(
            Map<String, List<MoveAction>> possibleMoves,
            GameState gameState) {
        List<MoveAction> choices = new ArrayList<>();

        for (Map.Entry<String, List<MoveAction>> entry : possibleMoves.entrySet()) {
            String characterId = entry.getKey();
            List<MoveAction> moves = entry.getValue();
            zombie.Init(characterId, gameState);
            MoveAction moveChoice = zombie.Move(gameState, moves);
            if (moveChoice != null)
                choices.add(moveChoice);
        }

        return choices;
    }

    @Override
    public List<AttackAction> decideAttacks(
            Map<String, List<AttackAction>> possibleAttacks,
            GameState gameState) {
        List<AttackAction> choices = new ArrayList<>();

        for (Map.Entry<String, List<AttackAction>> entry : possibleAttacks.entrySet()) {
            String characterId = entry.getKey();
            List<AttackAction> attacks = entry.getValue();
            zombie.Init(characterId, gameState);
            AttackAction attackChoice = zombie.Attack(gameState, attacks);
            if (attackChoice != null)
                choices.add(attackChoice);
        }

        return choices;
    }

    @Override
    public List<AbilityAction> decideAbilities(Map<String, List<AbilityAction>> possibleAbilities,
            GameState gameState) {
        return null;
    }
}
