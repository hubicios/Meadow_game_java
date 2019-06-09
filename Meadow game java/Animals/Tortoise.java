package meadowgame.Animals;

import static meadowgame.DefineConst.*;
import java.util.concurrent.ThreadLocalRandom;
import meadowgame.Animals.Animal;
import meadowgame.Organism;
import meadowgame.Point;
import meadowgame.World;

public class Tortoise extends Animal {

    public Tortoise(World world, Point point) {
        super(world, point, TORTOISE_SIGN, TORTOISE_COLOR, TORTOISE_STRENGTH, TORTOISE_PRIORITY);
    }

    public Tortoise(World world, Point point, int age, int id, int strength) {
        super(world, point, TORTOISE_SIGN, TORTOISE_COLOR, TORTOISE_STRENGTH, TORTOISE_PRIORITY,age,id);
    }

    @Override
    public void action() {
        /*if (age_ == 0) {
            age_++;
        } else */{
            int randomNum = ThreadLocalRandom.current().nextInt(0,4);
            if (randomNum==0) { //25% chances to move
                super.action();
            } else {
                age_++;
            }
        }
    }

    @Override
    public void collision(Organism attacker) {
        super.collision(attacker);
    }

    @Override
    public void breeding() {
        Point freePlace = world_.randomFreePlace(this);
        if (freePlace != null) {
            breedingDescription();
            world_.addOrganism(new Tortoise(world_, freePlace));
        }
    }

    @Override
    public boolean isFightOff(Organism attacker) {
        return (attacker.getStrength() < 5);

    }

    @Override
    public String toString() {
        return "Tortoise";
    }
}
