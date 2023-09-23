package mech.mania.starterpack.strategy;

import java.util.List;

import mech.mania.starterpack.game.GameState;
import mech.mania.starterpack.game.character.Character;
import mech.mania.starterpack.game.character.MoveAction;
import mech.mania.starterpack.game.character.action.AbilityAction;
import mech.mania.starterpack.game.character.action.AttackAction;
import mech.mania.starterpack.game.util.Position;
public abstract class IndividualStrategy {
    public String id;
    public Position pos;
    public Character self;
    public void Init(String id, GameState gameState){
        this.id = id;
        this.self = gameState.characters().get(id);
        this.pos = self.position();
    }
    public abstract MoveAction Move(GameState gameState, List<MoveAction> moveActions);
    public abstract AttackAction Attack(GameState gameState, List<AttackAction> attackActions);
    public abstract AbilityAction Ability(GameState gameState, List<AbilityAction> abilityActions);
}
