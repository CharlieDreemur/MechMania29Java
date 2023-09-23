package mech.mania.starterpack.strategy;

import mech.mania.starterpack.game.GameState;
import mech.mania.starterpack.game.character.Character;
import mech.mania.starterpack.game.character.MoveAction;
import mech.mania.starterpack.game.character.action.AbilityAction;
import mech.mania.starterpack.game.character.action.AttackAction;
import mech.mania.starterpack.game.character.action.AttackActionType;
import mech.mania.starterpack.game.character.action.CharacterClassType;
import mech.mania.starterpack.game.util.Position;

import java.util.*;

/**
 * A simple human which runs away from zombies
 */
public class NaiveHumanStrategy extends Strategy {
    NaiveHuman human = new NaiveHuman();
    NaiveBuilder builder = new NaiveBuilder();

    @Override
    public Map<CharacterClassType, Integer> decideCharacterClasses(
            List<CharacterClassType> possibleClasses,
            int numToPick,
            int maxPerSameClass) {
        // Selecting character classes following a specific distribution
        return Map.of(
                CharacterClassType.MARKSMAN, 5,
                CharacterClassType.MEDIC, 5,
                CharacterClassType.TRACEUR, 5,
                CharacterClassType.DEMOLITIONIST, 1);
    }

    @Override
    public List<MoveAction> decideMoves(
            Map<String, List<MoveAction>> possibleMoves,
            GameState gameState) {
        List<MoveAction> choices = new ArrayList<>();
        for (Map.Entry<String, List<MoveAction>> entry : possibleMoves.entrySet()) {
            String characterId = entry.getKey();
            List<MoveAction> moves = entry.getValue();
            Character character = gameState.characters().get(characterId);
            MoveAction moveChoice = null;
            switch (character.classType()) {
                case BUILDER:
                    moveChoice = builder.Move(characterId, gameState, moves);
                default:
                    moveChoice = human.Move(characterId, gameState, moves);
            }

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
            Character character = gameState.characters().get(characterId);
            AttackAction attackChoice = null;
            switch (character.classType()) {
                case BUILDER:
                    attackChoice = builder.Attack(characterId, gameState, attacks);
                    break;
                default:
                    attackChoice = human.Attack(characterId, gameState, attacks);
            }
            if (attackChoice != null)
                choices.add(attackChoice);
        }
        return choices;
    }

    @Override
    public List<AbilityAction> decideAbilities(
            Map<String, List<AbilityAction>> possibleAbilities,
            GameState gameState) {
        List<AbilityAction> choices = new ArrayList<>();

        for (Map.Entry<String, List<AbilityAction>> entry : possibleAbilities.entrySet()) {
            String characterId = entry.getKey();
            List<AbilityAction> abilities = entry.getValue();
            Character character = gameState.characters().get(characterId);
            AbilityAction abilityAction = null;
            switch (character.classType()) {
                case BUILDER:
                    abilityAction = builder.Ability(characterId, gameState, abilities);
                    break;
                default:
                    abilityAction = human.Ability(characterId, gameState, abilities);
            }
            if (abilityAction != null)
                choices.add(abilityAction);
        }

        return choices;
    }
}
