package meadowgame.Plants;

import static meadowgame.DefineConst.*;
import meadowgame.Point;
import meadowgame.World;

public class Grass extends Plant {

    public Grass(World world, Point point) {
        super(world, point, GRASS_SIGN, GRASS_COLOR, GRASS_STRENGTH, GRASS_SPREAD);
    }

    public Grass(World world, Point point, int age, int id, int strength) {
        super(world, point, GRASS_SIGN, GRASS_COLOR, GRASS_STRENGTH, GRASS_SPREAD,age,id);
    }

    @Override
    public void spread() {
        Point freePlace = world_.randomFreePlace(this);
        boolean succes = freePlace != null;
        if (succes && isAlive_) {
            world_.addOrganism(new Grass(world_, freePlace));
        }
        spreadDescription(succes);
    }

    @Override
    public String toString() {
        return "Grass";
    }
}
