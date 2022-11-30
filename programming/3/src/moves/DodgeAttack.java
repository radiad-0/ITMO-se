package momessageves;

import core.Character;
import core.Move;

public class DodgeAttack extends Move {

    public DodgeAttack() {
        super("пролетает мимо выбивалки");
    }

    @Override
    public void doMove(Character character) {
        System.out.println(character.getName() + " " + getMessage());
    }
}
