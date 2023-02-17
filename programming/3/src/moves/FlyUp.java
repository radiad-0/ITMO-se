package moves;

import core.Character;
import core.Move;

public class FlyUp extends Move {;

    public FlyUp() {
        super("взмыл вверх");
    }

    @Override
    public void doMove(Character character) {
        character.addyCoordinate(20);
        System.out.println(character.getName() + " " + getMessage());
    }
}
