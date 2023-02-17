package moves;

import core.Character;
import core.Move;

public class Сhase extends Move {
    public Сhase() {
        super("погналась");
    }

    @Override
    public void doMove(Character character) {
        character.addxCoordinate(10);
        System.out.println(character.getName() + " " + getMessage());
    }
}
