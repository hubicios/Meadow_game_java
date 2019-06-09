package meadowgame.Animals;

import java.awt.Color;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import meadowgame.DefineConst;
import static meadowgame.DefineConst.HEXAGON;
import meadowgame.Direction;
import meadowgame.Organism;
import meadowgame.Point;
import meadowgame.World;

public abstract class Animal extends Organism {

    public Animal(World world, Point point, char sign, Color color, int strength, int priority) {
        super(world, point, sign, color, strength, priority);
    }

    public Animal(World world, Point point, char sign, Color color, int strength, int priority, int age, int id) {
        super(world, point, sign, color, strength, priority, age, id);
    }

    @Override
    public void action() {
        /*if (age_ == 0) {
            age_++;
        } else */if (isAlive_) {
            age_++;
            Direction direction = randomDirection();
            Point point = new Point(point_.getX(), point_.getY(), direction);
            Organism fieldTaken = world_.fieldTaken(point);

            if (attack(fieldTaken)) {
                point_.move(direction);
            }
        }
    }

    @Override
    public void collision(Organism attacker) {
        if (strength_ < attacker.getStrength()) {
            setIsAlive(false);
            world_.notifyTurnInfoListener(attacker.toString() + " killed ->" + this.toString() + "\n");
        } else {
            attacker.setIsAlive(false);
            world_.notifyTurnInfoListener(attacker.toString() + " <- has been killed by " + this.toString() + "\n");
        }
    }

    public abstract void breeding();

    public final void breedingDescription() {
        world_.notifyTurnInfoListener("New " + this.toString() + " has been born" + "\n");
    }

    @Override
    public String toString() {
        return "Animal";
    }

    public final Direction randomDirection() {
        return randomDirection(1);
    }

    public final Direction randomDirection(int n) {
        ArrayList<Direction> directionAvailabe = allDirection(n);
        if (directionAvailabe.size() > 0) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, directionAvailabe.size());
            if(randomNum%2==1&&randomNum>0) randomNum--;
            return directionAvailabe.get(randomNum);
        } else {
            return Direction.DIRECTION_NONE;
        }
    }

    public final ArrayList<Direction> allDirection() {
        return allDirection(1);
    }

    public final ArrayList<Direction> allDirection(int n) {
        int x = point_.getX();
        int y = point_.getY();
        ArrayList<Direction> directionAvailabe = new ArrayList<>();

        if (x + n < world_.getWidth()) //RIGHT
        {
            directionAvailabe.add(Direction.RIGHT);
            if (world_.getBoardType() == HEXAGON) {
                if (y - n >= 0 && x % 2 == 1) //RIGHT_TOP
                {
                    directionAvailabe.add(Direction.RIGHT_TOP);
                }
                if (y + n < world_.getHeight() && x % 2 == 0) //RIGHT_DOWN
                {
                    directionAvailabe.add(Direction.RIGHT_DOWN);
                }
            }
        }
        if (x - n >= 0) //LEFT
        {
            directionAvailabe.add(Direction.LEFT);
            if (world_.getBoardType() == HEXAGON) {
                if (y - n >= 0 && x % 2 == 1) //LEFT_TOP
                {
                    directionAvailabe.add(Direction.LEFT_TOP);
                }
                if (y + n < world_.getHeight() && x % 2 == 0) //LEFT_DOWN
                {
                    directionAvailabe.add(Direction.LEFT_DOWN);
                }
            }
        }
        if (y - n >= 0) //TOP
        {
            directionAvailabe.add(Direction.TOP);
        }
        if (y + n < world_.getHeight()) //DOWN
        {
            directionAvailabe.add(Direction.DOWN);
        }

        return directionAvailabe;
    }

    @Override
    public boolean isFightOff(Organism attacker) {
        return false;
    }

    protected final boolean attack(Organism defender) {
        boolean makeMove = true;
        if (defender != null) //field was taken
        {
            // System.out.println("atak");
            //if (sign_ == defender.getSign()) {
            if (this.getClass() == defender.getClass()) {
                makeMove = false;
                if (age_ >= DefineConst.FERTILITY_AGE && defender.getAge() >= DefineConst.FERTILITY_AGE) {
                    breeding();
                }
            } else if (defender.isFightOff(this) == true) {
                world_.notifyTurnInfoListener(defender.toString() + " has fight off " + this.toString() + "\n");
                makeMove = false;
            } else {
                defender.collision(this);
                if (isAlive_ == false) {
                    makeMove = false;
                }
            }
        }
        return makeMove;
    }
}
