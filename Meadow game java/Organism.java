package meadowgame;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.IOException;
import static meadowgame.DefineConst.DEFAULT_FIELD_COLOR;

public abstract class Organism implements java.io.Closeable {

    
    protected int strength_;
    protected int priority_;
    protected Color color_;
    protected char sign_;
    protected int age_;
    protected boolean isAlive_;
    protected Point point_ = new Point();
    protected int id_;
    protected static int nOrganism = 0;

    protected World world_;
    
    
    public Organism(World world, Point point, char sign, Color color, int strength, int priority, int age, int id) {
        this.world_ = world;
        this.point_ = point;
        this.color_ = color;
        this.sign_ = sign;
        this.strength_ = strength;
        this.priority_ = priority;
        this.age_ = age;
        this.isAlive_ = true;
       /* this.id_ = id;
        nOrganism++;*/
        if (id >nOrganism) {
            this.id_ = id;
            nOrganism = id++;
        } else {
            this.id_ = nOrganism;
            nOrganism++;
        }
    }

    public Organism(World world, Point point, char sign, Color color, int strength, int priority) {
        this(world, point, sign, color, strength, priority, 0, nOrganism);
    }

    public final void close() {
    }

    @Override
    public abstract String toString();

    public abstract void action();

    public abstract void collision(Organism intruz);

    public abstract boolean isFightOff(Organism attacker); //checks if attack was countered

    public void draw() {
        this.draw(color_);
    }

    public void draw(Color color) {
        if (isAlive_ == true) {
            world_.setToDraw(point_.getX(), point_.getY(), color);
        }
    }

    public final int getStrength() {
        return strength_;
    }

    public final void setStrength(int strength) {
        strength_ = strength;
    }

    public final void increaseStrength(int add) {
        strength_ += add;
    }

    public final int getPriority() {
        return priority_;
    }

    public final char getSign() {
        return sign_;
    }

    public final Color getColor() {
        return color_;
    }

    public final int getAge() {
        return age_;
    }

    public final void setAge(int age) {
        age_ = age;
    }

    public final void increasAge() {
        age_++;
    }

    public final int getId() {
        return id_;
    }

    public final void setId(int id) {
        id_ = id;
    }

    public final boolean getIsAlive() {
        return isAlive_;
    }

    public final void setIsAlive(boolean isAlive) {
        isAlive_ = isAlive;
        if (isAlive_ == false) {
            point_.setXY(-1, -1);
        }
    }

    public final Point getPoint() {
        return point_;
    }


    public void save(BufferedWriter writer) throws IOException {
        if (isAlive_) {
            writer.write(sign_ + " " + point_.getX() + " " + point_.getY() + " " + age_ + " " + id_ + " " + strength_);
        }
    }

    public void load(String line) {
        String[] parts = line.split(" ");
    }
}
