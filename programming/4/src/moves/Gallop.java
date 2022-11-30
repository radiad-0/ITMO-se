package moves;

import core.Character;
import core.Move;

public class Gallop extends Move {
    public Gallop() {
        super("скачет");
    }

    @Override
    public void doMove(Character character) {
        character.addxCoordinate(4);
        System.out.println(character.getName() + " " + getMessage());
    }
}
