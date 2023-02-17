package moves;

import core.Character;
import core.Move;

public class Scream extends Move {
    public Scream() {
        super("орет");
    }

    @Override
    public void doMove(Character character) {
        System.out.println(character.getName() + " " + getMessage());
    }
}
