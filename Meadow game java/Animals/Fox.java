package meadowgame.Animals;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import static meadowgame.DefineConst.*;
import meadowgame.Direction;
import meadowgame.Organism;
import meadowgame.Point;
import meadowgame.World;

public class Fox extends Animal {

    public Fox(World world, Point point) {
//C++ TO JAVA CONVERTER WARNING: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created if it does not yet exist:
//ORIGINAL LINE: super(world, point, SignEnum::FOX_SIGN, DefineConstants.FOX_STRENGTH, DefineConstants.FOX_PRIORITY);
        super(world, point, FOX_SIGN, FOX_COLOR, FOX_STRENGTH, FOX_PRIORITY);
    }

    public Fox(World world, Point point, int age, int id, int strength) {
        super(world, point, FOX_SIGN, FOX_COLOR, FOX_STRENGTH, FOX_PRIORITY,age,id);
    }

    /*public final void close()
	{
		super.close();
	}*/

    @Override
    public void action() {
        /*if (age_ == 0) {
            age_++;
        } else */if (isAlive_) {
            age_++;
            ArrayList<Direction> allDirections = allDirection();

            Point point;
            Organism field_taken;
            Direction direction;
            do {
                int randomNum = ThreadLocalRandom.current().nextInt(0,allDirections.size());
                direction = allDirections.get(randomNum);
                point = new Point(point_.getX(),point_.getY(),direction);
                allDirections.remove(randomNum);
                field_taken = world_.fieldTaken(point);
            } while (allDirections.size() > 0 && field_taken != null && field_taken.getStrength() > strength_); //searches for free spot (if nothing is free, stays in the same filed)

            if (attack(field_taken)) {
                point_.move(direction);
            }
        }
    }

    @Override
    public void breeding() {
        Point freePlace = world_.randomFreePlace(this);
        if (freePlace != null) {
            breedingDescription();
            world_.addOrganism(new Fox(world_, freePlace));
        }
    }

    @Override
    public String toString() {
        return "Fox";
    }
}
