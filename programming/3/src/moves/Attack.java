package moves;

import core.Character;
import core.Move;

public class Attack extends Move {

    public Attack() {
        super("размахивает выбивалкой");
    }

    @Override
    public void doMove(Character character) {
        System.out.println(character.getName() + " " + getMessage());
    }
}
