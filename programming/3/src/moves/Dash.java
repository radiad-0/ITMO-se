package moves;

import core.Character;
import core.Move;

public class Dash extends Move {
    public Dash() {
        super("метнулся");
    }

    @Override
    public void doMove(Character character) {
        character.addxCoordinate(20);
        System.out.println(character.getName() + " " + getMessage());
    }
}
