package meadowgame.Plants;

import static meadowgame.DefineConst.*;
import meadowgame.Organism;
import meadowgame.Point;
import meadowgame.World;

public class Guarana extends Plant {

    public Guarana(World world, Point point) {
        super(world, point, GUARANA_SIGN, GUARANA_COLOR, GUARANA_STRENGTH, GUARANA_SPREAD);
    }

    public Guarana(World world, Point point, int age, int id, int strength) {
        super(world, point, GUARANA_SIGN, GUARANA_COLOR, GUARANA_STRENGTH, GUARANA_SPREAD, age, id);
    }

    @Override
    public void collision(Organism attacker) {

        attacker.increaseStrength(GUARANA_BONUS_GIVEN); //increase strenght by bonus (default 3)
        setIsAlive(false);
        world_.notifyTurnInfoListener(attacker.toString() + " strength is now " + attacker.getStrength() + ". " + this.toString() + " has been eaten\n");
    }

    @Override
    public void spread() {
        Point freePlace = world_.randomFreePlace(this);
        boolean succes = freePlace != null;
        if (succes && isAlive_) {
            world_.addOrganism(new Guarana(world_, freePlace));
        }
        spreadDescription(succes);
    }

    @Override
    public String toString() {
        return "Guarana";
    }
}
