package moves;

import core.Character;
import core.Move;

public class SlowRun extends Move {
    public SlowRun() {
        super("еле поспевает");
    }

    @Override
    public void doMove(Character character) {
        character.addxCoordinate(2);
        System.out.println(character.getName() + " " + getMessage());
    }
}
