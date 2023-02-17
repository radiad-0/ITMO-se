package moves;

import core.Character;
import core.Move;

public class Fly extends Move {
    public Fly() {
        super("летит");
    }

    @Override
    public void doMove(Character character) {
        character.addxCoordinate(5);
        System.out.println(character.getName() + " " + getMessage());
    }
}
