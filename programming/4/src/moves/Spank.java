package moves;

import core.Character;
import core.Move;

public class Spank extends Move {
    public Spank() {
        super("шлепает ногами");
    }

    @Override
    public void doMove(Character character) {
        System.out.println(character.getName() + " " + getMessage());
    }
}
