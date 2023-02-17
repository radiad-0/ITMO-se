package moves;

import core.Character;
import core.Move;

public class Jump extends Move {
    public Jump() {
        super("прыгает");
    }

    @Override
    public void doMove(Character character) {
        System.out.println(character.getName() + " " + getMessage());
    }
}
