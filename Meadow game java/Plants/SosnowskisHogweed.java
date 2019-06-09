package meadowgame.Plants;

import java.util.*;
import static meadowgame.DefineConst.*;
import meadowgame.Organism;
import meadowgame.Point;
import meadowgame.World;

public class SosnowskisHogweed extends Plant {

    public SosnowskisHogweed(World world, Point point) {
        super(world, point, SOSNOWSKIS_HOGWEED_SIGN, SOSNOWSKIS_HOGWEED_COLOR, SOSNOWSKIS_HOGWEED_STRENGTH, SOSNOWSKIS_HOGWEED_SPREAD);
    }

    public SosnowskisHogweed(World world, Point point, int age, int id, int strength) {
        super(world, point, SOSNOWSKIS_HOGWEED_SIGN, SOSNOWSKIS_HOGWEED_COLOR, SOSNOWSKIS_HOGWEED_STRENGTH, SOSNOWSKIS_HOGWEED_SPREAD,age,id);
    }

    @Override
    public void action() {
        ArrayList<Organism> neighbours = world_.neighbours(this);
        for (int i = 0; i < neighbours.size(); i++) {
            if (neighbours.get(i).getSign() != this.getSign()) {
                neighbours.get(i).setIsAlive(false);
                world_.notifyTurnInfoListener(this.toString() + " killed ->" + neighbours.get(i).toString() + "\n");
            }
        }
        age_++;
        super.action(SOSNOWSKIS_HOGWEED_SPREAD);

    }

    @Override
    public void spread() {
        Point freePlace = world_.randomFreePlace(this);
        boolean succes = freePlace != null;
        if (succes && isAlive_) {
            world_.addOrganism(new SosnowskisHogweed(world_, freePlace));
        }
        spreadDescription(succes);
    }

    @Override
    public void collision(Organism attacker) {
        deadlyPlantEaten(attacker);
    }

    @Override
    public String toString() {
        return "Sosnowski's hogweed";
    }
}
