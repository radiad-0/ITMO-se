import core.Character;

import core.Sound;
import moves.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    /*
    Карлсон метнулся в большую комнату, и снова началась бешеная погоня по всей квартире.
    Впереди летел Карлсон -- он кудахтал и визжал от удовольствия, за ним мчалась фрекен Бок
    с выбивалкой для ковров, за ней еле поспевал Малыш, а позади всех скакал Бимбо, бешено тявкая.
    Фрекен Бок не отставала от него, но всякий раз, когда она уже готова была его схватить,
    Карлсон взмывал вверх, под самый потолок. А когда фрекен Бок начинала размахивать выбивалкой,
    ему всегда удавалось пролететь мимо, едва ее не коснувшись.
    */
    public static void main(String[] args) throws FileNotFoundException {
        Character Karlson = new Character("Карлсон");
        Character FrekenBok = new Character("Фрекен Бок");
        Character Malish = new Character("Малыш");
        Character Bimbo = new Character("Бимбо");

        Karlson.moving(new Dash());
        Karlson.moving(new Fly());
        Karlson.makeSound(Sound.CACKLE);
        Karlson.makeSound(Sound.SQUEAL_WITH_PLEASURE);
        FrekenBok.moving(new FastRun());
        Malish.moving(new SlowRun());
        Bimbo.moving(new Gallop());
        Bimbo.makeSound(Sound.YAP_FURIOUSLY);
        FrekenBok.moving(new TryingGrab());
        Karlson.moving(new FlyUp());
        FrekenBok.moving(new Attack());
        Karlson.moving(new momessageves.DodgeAttack());
    }
}
