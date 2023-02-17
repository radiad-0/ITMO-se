package moves;

import core.Character;
import core.Move;

public class TakeWeapon extends Move {
    public TakeWeapon() {
        super("хватает выбивалку");
    }

    @Override
    public void doMove(Character character) {
        System.out.println(character.getName() + " " + getMessage());
    }
}
