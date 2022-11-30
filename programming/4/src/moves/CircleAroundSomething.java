package moves;

import core.Character;
import core.Move;

public class CircleAroundSomething extends Move {
    private Character thing;
    public CircleAroundSomething(Character thing) {
        super("кружит");
        this.thing = thing;
    }

    @Override
    public void doMove(Character character) {
        System.out.println(character.getName() + " " + getMessage() + " вокруг " + thing.getName());
    }
}
