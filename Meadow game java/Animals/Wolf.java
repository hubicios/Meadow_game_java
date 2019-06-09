package meadowgame.Animals;

import static meadowgame.DefineConst.*;
import meadowgame.Point;
import meadowgame.World;


public class Wolf extends Animal {

    public Wolf(World world, Point point) {
        super(world, point, WOLF_SIGN, WOLF_COLOR, WOLF_STRENGTH, WOLF_PRIORITY);
    }

    public Wolf(World world, Point point, int age, int id, int strength) {
          super(world, point, WOLF_SIGN, WOLF_COLOR, WOLF_STRENGTH, WOLF_PRIORITY,age,id);
    }

    @Override
    public void breeding() {
        Point freePlace = world_.randomFreePlace(this);
        if (freePlace != null) {
            breedingDescription();
            world_.addOrganism(new Wolf(world_, freePlace));
        }
    }

    @Override
    public String toString() {
        return "Wolf";
    }
}
