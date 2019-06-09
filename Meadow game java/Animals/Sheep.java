package meadowgame.Animals;

import meadowgame.Point;
import meadowgame.World;
import static meadowgame.DefineConst.*;


public class Sheep extends Animal {

    public Sheep(World world, Point point) {

        super(world, point, SHEEP_SIGN, SHEEP_COLOR, SHEEP_STRENGTH, SHEEP_PRIORITY);
    }

    public Sheep(World world, Point point, int age, int id, int strength) {
       super(world, point, SHEEP_SIGN, SHEEP_COLOR, SHEEP_STRENGTH, SHEEP_PRIORITY,age,id);
    }


    @Override
    public void breeding() {
        Point freePlace = world_.randomFreePlace(this);
        if (freePlace != null) {
            breedingDescription();
            world_.addOrganism(new Sheep(world_, freePlace));
        }
    }

    @Override
    public String toString() {
        return "Sheep";
    }
}

