package meadowgame.Plants;

import static meadowgame.DefineConst.*;
import meadowgame.Point;
import meadowgame.World;

public class Dandelion extends Plant {

    public Dandelion(World world, Point point) {
        super(world, point, DANDELION_SIGN, DANDELION_COLOR, DANDELION_STRENGTH, DANDELION_SPREAD);
    }

    public Dandelion(World world, Point point, int age, int id, int strength) {
       super(world, point, DANDELION_SIGN, DANDELION_COLOR, DANDELION_STRENGTH, DANDELION_SPREAD,age,id);
    }

    @Override
    public void action() {
        for (int i = 0; i < DANDELION_SPREAD_TIMES; i++) {
            super.action(DANDELION_SPREAD);
        }
        age_ = age_ - DANDELION_SPREAD_TIMES + 1;
    }

    @Override
    public void spread() {
        Point freePlace = world_.randomFreePlace(this);
        boolean succes = freePlace != null;
        if (succes && isAlive_) {
            world_.addOrganism(new Dandelion(world_, freePlace));
        }
        spreadDescription(succes);
    }

    @Override
    public String toString() {
        return "Dandelion";
    }
}
