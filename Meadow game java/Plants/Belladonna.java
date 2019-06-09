package meadowgame.Plants;

import static meadowgame.DefineConst.*;
import meadowgame.Organism;
import meadowgame.Point;
import meadowgame.World;

public class Belladonna extends Plant {

    public Belladonna(World world, Point point) {
        super(world, point, BELLADONNA_SIGN, BELLADONNA_COLOR, BELLADONNA_STRENGTH, BELLADONNA_SPREAD);

    }

    public Belladonna(World world, Point point, int age, int id, int strength) {
        super(world, point, BELLADONNA_SIGN, BELLADONNA_COLOR, BELLADONNA_STRENGTH, BELLADONNA_SPREAD,age,id);
    }

    @Override
    public void collision(Organism attacker) {
        deadlyPlantEaten(attacker);
    }

    @Override
    public void spread() {
        Point freePlace = world_.randomFreePlace(this);
        boolean succes = freePlace != null;
        if (succes && isAlive_) {
            world_.addOrganism(new Belladonna(world_, freePlace));
        }
        spreadDescription(succes);
    }

    @Override
    public String toString() {
        return "Belladonna";
    }
}
