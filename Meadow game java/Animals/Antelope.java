package meadowgame.Animals;

import static meadowgame.DefineConst.*;
import java.util.concurrent.ThreadLocalRandom;
import meadowgame.Direction;
import meadowgame.Organism;
import meadowgame.Point;
import meadowgame.World;

public class Antelope extends Animal {

    public Antelope(World world, Point point) {
//C++ TO JAVA CONVERTER WARNING: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created if it does not yet exist:
//ORIGINAL LINE: super(world, point, SignEnum::ANTELOPE_SIGN, DefineConstants.ANTELOPE_STRENGTH, DefineConstants.ANTELOPE_PRIORITY);
        super(world, point, ANTELOPE_SIGN, ANTELOPE_COLOR, ANTELOPE_STRENGTH, ANTELOPE_PRIORITY);
    }

    public Antelope(World world, Point point, int age, int id, int strength) {
        super(world, point, ANTELOPE_SIGN, ANTELOPE_COLOR, strength, ANTELOPE_PRIORITY,age,id);
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
            Direction direction = randomDirection(ANTELOPE_JUMP);
            Point point = new Point(point_.getX(),point_.getY(),direction);
            Organism fieldTaken = world_.fieldTaken(point);

            if (attack(fieldTaken)) {
                point_.move(direction, ANTELOPE_JUMP);
            }
        }
    }

    @Override
    public void breeding() {
        Point freePlace = world_.randomFreePlace(this);
        if (freePlace != null) {
            breedingDescription();
            world_.addOrganism(new Antelope(world_, freePlace));
        }
    }

    @Override
    public void collision(Organism attacker) {
        int randomNum = ThreadLocalRandom.current().nextInt(0,2);
        if (randomNum==0) // antelope stays //50% chances
        {
            if (strength_ < attacker.getStrength()) {
                setIsAlive(false);
                world_.notifyTurnInfoListener(attacker.toString() + " killed ->" + this.toString()+"\n");
            } else {
                attacker.setIsAlive(false);
                world_.notifyTurnInfoListener(attacker.toString() + " <- has been killed by " + this.toString()+"\n");
            }
        } else //runs to nearest field
        {
            Point freePlace = world_.randomFreePlace(this);
            if (freePlace != null) {
                point_.setXY(freePlace.getX(), freePlace.getY());
                world_.notifyTurnInfoListener(this.toString() + " has escaped from " + attacker.toString()+"\n");
                if (freePlace != null) {
                    freePlace.close();
                }
            } else {
                super.collision(attacker); //not a single field was free 
            }
        }
    }

    @Override
    public String toString() {
        return "Antelope";
    }
}
