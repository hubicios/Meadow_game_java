package meadowgame.Animals;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import static meadowgame.DefineConst.*;
import meadowgame.Organism;
import meadowgame.Point;
import meadowgame.World;
import meadowgame.Direction;

public class Human extends Animal {
    //Human();

    /*Human::Human()
	{
	}*/
    public Human(World world, Point point) {
//C++ TO JAVA CONVERTER WARNING: The following line was determined to contain a copy constructor call - this should be verified and a copy constructor should be created if it does not yet exist:
//ORIGINAL LINE: super(world, point, DefineConstants.HUMAN_SIGN, DefineConstants.HUMAN_STRENGTH, DefineConstants.HUMAN_PRIORITY);
        super(world, point, HUMAN_SIGN, HUMAN_COLOR_AVAILABLE, HUMAN_STRENGTH, HUMAN_PRIORITY);
        this.colorActive_ = HUMAN_COLOR_ACTIVE;
        this.colorUnactive_ = HUMAN_COLOR_UNACTIVE;
        this.skillCooldown_ = 0;
    }

    public Human(World world, Point point, int age, int id, int strength, int cooldown) {
        super(world, point, HUMAN_SIGN, HUMAN_COLOR_AVAILABLE, HUMAN_STRENGTH, HUMAN_PRIORITY, age, id);
        this.colorActive_ = HUMAN_COLOR_ACTIVE;
        this.colorUnactive_ = HUMAN_COLOR_UNACTIVE;
        this.skillCooldown_ = cooldown;
    }

    public final int userInput() {

        int c = world_.getInput();
        if (c != NONE_INPUT) {
            int n = 1;
            if (c == KeyEvent.VK_Q) {
                if (skillCooldown_ == 0) {

                    System.out.println("Alzur's shield has been activated for " + (HUMAN_SKILL_TIME) + " turns");
                    return 'q';
                } else if (skillCooldown_ > HUMAN_SKILL_TIME) {
                    System.out.println("Alzur's shield is still active for " + (skillCooldown_ - HUMAN_SKILL_TIME) + " turns");
                } else {
                    System.out.println("You have to wait " + skillCooldown_ + " turns Alzur's shield");
                }
            } else {
                if (c == KeyEvent.VK_S || c == KeyEvent.VK_UP) { //TOP
                    if (point_.getY() - n >= 0) { //TOP
                        return Direction.TOP.getValue();
                    }
                } else if (c == KeyEvent.VK_X || c == KeyEvent.VK_DOWN) { //DOWN
                    if (point_.getY() + n < world_.getHeight()) { //DOWN
                        return Direction.DOWN.getValue();
                    }
                }

                if (world_.getBoardType() == RECTANGLE) { //RECTANGLE

                    if (c == KeyEvent.VK_LEFT && point_.getX() - n >= 0) { //LEFT
                        return Direction.LEFT.getValue();
                    } else if (c == KeyEvent.VK_RIGHT && point_.getX() + n < world_.getWidth()) { //RIGHT
                        return Direction.RIGHT.getValue();
                    }
                } else { //HEXAGON
                    if (point_.getX() % 2 == 1) { //odd
                        switch (c) {
                            
                            case KeyEvent.VK_A: //LEFT
                                return Direction.LEFT.getValue();
                            case KeyEvent.VK_D: //RIGHT
                                return Direction.RIGHT.getValue();
                            case KeyEvent.VK_Z: //LEFT DOWN
                                return Direction.LEFT_DOWN.getValue();
                            case KeyEvent.VK_C://RIGHT DOWN
                                return Direction.RIGHT_DOWN.getValue();

                        }
                    } else { //even
                        switch (c) {
                            case KeyEvent.VK_A: //TOP LEFT
                                return Direction.LEFT_TOP.getValue();
                            case KeyEvent.VK_D: //TOP RIGHT
                                return Direction.RIGHT_TOP.getValue();
                            case KeyEvent.VK_Z: //LEFT
                                return Direction.LEFT.getValue();
                            case KeyEvent.VK_C: //RIGHT
                                return Direction.RIGHT.getValue();
                        }
                    }
                }
            }
        }
        return NONE_INPUT;
    }

    @Override
    public void action() {
        /*if (age_ == 0) {
            age_++;
        } else */if (isAlive_) {
            age_++;

            int input = userInput();
            if (input != NONE_INPUT) {
                if (input == 'q') {
                    skillCooldown_ = HUMAN_SKILL_TIME * 2;
                } else {
                    Point point = new Point(point_.getX(), point_.getY(), Direction.forValue(input));
                    Organism fieldTaken = world_.fieldTaken(point);

                    if (attack(fieldTaken)) {
                        point_.move(Direction.forValue(input));
                    }
                }
            }
            if (skillCooldown_ > 0) {
                skillCooldown_--;
            }
        }

    }

    @Override
    public void breeding() {
        ;
    }

    @Override
    public boolean isFightOff(Organism attacker
    ) {
        return (skillCooldown_ > HUMAN_SKILL_TIME); //skill is active
    }

    @Override
    public String toString() {
        return "Human";
    }

    @Override
    public void draw() {
        if (skillCooldown_ == 0) {
            super.draw(color_);//availabe
        } else if (skillCooldown_ > HUMAN_SKILL_TIME) {
            super.draw(colorActive_);//active
        } else {
            super.draw(colorUnactive_);//cooldown
        }

    }

    @Override
    public void save(BufferedWriter writer) throws IOException {
        if (isAlive_) {
            writer.write(sign_ + " " + point_.getX() + " " + point_.getY() + " " + age_ + " " + id_ + " " + strength_ + " " + skillCooldown_);
        }
    }
    //how long skill is active >HUMAN_SKILL_TIME (5,10)
    private int skillCooldown_; //how long till skill will be available (0,5)
    private Color colorUnactive_;
    private Color colorActive_;
}
