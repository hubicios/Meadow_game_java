package meadowgame.Plants;

import static meadowgame.DefineConst.*;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
import meadowgame.Organism;
import meadowgame.Point;
import meadowgame.World;

public abstract class Plant extends Organism {

    public Plant(World world, Point point, char sign, Color color, int strength, double spread) {
        super(world, point, sign, color, strength, 0);
        this.spread_ = spread;
    }
     public Plant(World world, Point point, char sign, Color color, int strength, double spread,int age,int id) {
        super(world, point, sign, color, strength, 0,age,id);
        this.spread_ = spread;
    }

    public final double getSpread() {
        return spread_;
    }

    public final void setSpread(double spread) {
        spread_ = spread;
    }

    @Override
    public void action() {
        this.action(spread_);
    }

    public final void action(double chances) {
        int base = 1000;
        int randomNum = ThreadLocalRandom.current().nextInt(0, base);
        if (randomNum % base < chances * base && age_ >= FERTILITY_AGE) {
            spread();
        }
        age_++;
    }

    @Override
    public void collision(Organism attacker) {
        setIsAlive(false);
        world_.notifyTurnInfoListener(attacker.toString() + " destroyed " + this.toString() + " by stepping on it" + "\n");
    }

    public final void deadlyPlantEaten(Organism attacker) {
        setIsAlive(false);
        if (strength_ > attacker.getStrength()) {
            attacker.setIsAlive(false);
            world_.notifyTurnInfoListener(attacker.toString() + " killed himself by eating " + this.toString() + "\n");
        } else {
            world_.notifyTurnInfoListener(attacker.toString() + " has eaten " + this.toString() + " but manage to survive" + "\n");
        }

    }

    public abstract void spread();

    public final void spreadDescription(boolean succes) {
        if (succes == true) {
            world_.notifyTurnInfoListener("New "+this.toString()+"\n");
        }
    }

    @Override
    public boolean isFightOff(Organism attacker) {
        return false;
    }

    @Override
    public String toString() {
        return "Plant";
    }
    protected double spread_;
}
