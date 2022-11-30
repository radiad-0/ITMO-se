package moves;

import core.Character;
import core.Move;

public class FastRun extends Move {
    public FastRun(){
        super("мчится");
    }
    @Override
    public void doMove(Character character) {
        character.addxCoordinate(10);
        System.out.println(character.getName() + " " + getMessage());
    }
}
