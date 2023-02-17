import core.Character;

import core.Move;
import core.Sound;
import moves.*;

public class Main {
    /*
    Но тут фрекен Бок как с цепи сорвалась. Она заорала не своим голосом, схватила выбивалку для ковров и,
    размахивая ею, погналась за Карлсоном. Карлсон, ликуя, кружил вокруг лампы.
    Карлсон метнулся в большую комнату, и снова началась бешеная погоня по всей квартире.
    Впереди летел Карлсон -- он кудахтал и визжал от удовольствия, за ним мчалась фрекен Бок
    с выбивалкой для ковров, за ней еле поспевал Малыш, а позади всех скакал Бимбо, бешено тявкая.
    Фрекен Бок не отставала от него, но всякий раз, когда она уже готова была его схватить,
    Карлсон взмывал вверх, под самый потолок. А когда фрекен Бок начинала размахивать выбивалкой,
    ему всегда удавалось пролететь мимо, едва ее не коснувшись. Фрекен Бок запыхалась,
    но продолжала подпрыгивать, и её большие босые ноги шлёпали по паркету.
    */
    public static void main(String[] args) {
        Character lamp = new Character("лампа"){
            public void moving(Move move) {
                System.out.println("этот объкт не может двигаться");
            }
            public void makeSound(Sound sound) {
                System.out.println("этот объкт не может издавать звук");
            }
        };
        Character Karlson = new Character("Карлсон");
        Character FrekenBok = new Character("Фрекен Бок");
        Character Malish = new Character("Малыш");
        Character Bimbo = new Character("Бимбо");

        FrekenBok.moving(new Scream());
        FrekenBok.moving(new TakeWeapon());
        FrekenBok.moving(new Attack());
        FrekenBok.moving(new Сhase());
        Karlson.makeSound(Sound.EXULT);
        Karlson.moving(new CircleAroundSomething(lamp));
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
        Karlson.moving(new DodgeAttack());
        FrekenBok.moving(new Jump());
        FrekenBok.moving(new Spank());
    }
}
