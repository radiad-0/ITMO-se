package moves;

import core.Character;
import core.Move;

public class TryingGrab extends Move {
    public TryingGrab() {
        super("пытается схватить");
    }

    @Override
    public void doMove(Character character) {
        System.out.println(character.getName() + " " + getMessage());
    }
}
