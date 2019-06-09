package meadowgame;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.log;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import meadowgame.Animals.*;
import static meadowgame.DefineConst.*;
import meadowgame.Game.MyTurnInfoListener;
import meadowgame.Plants.*;
import meadowgame.Organism;

public class World implements java.io.Closeable {

    private ArrayList<Organism> organisms = new ArrayList<Organism>();
    private Color[][] toDraw_;
    private int width_; // <9 , width_/heigh_)
    private int height_;
    private int input_;
    private int turnCount_;
    private char boardType_;
    private List<MyTurnInfoListener> turnInfo;
    private LinkedList<String> turnDescription_ = new LinkedList<String>();

    public World() {
    }

    public World(int width, int height, char boardType) {
        this(width, height, 0, boardType);
    }

    public World(int width, int height, int turnCount, char boardType) {
        this.width_ = width;
        this.height_ = height;
        this.input_ = NONE_INPUT;
        this.turnCount_ = turnCount;
        this.boardType_ = boardType;
        toDraw_ = new Color[height_][width_];
        turnInfo = new LinkedList<>();
    }

    public final void close() {
    }

    public final void initWorld(int exist) {
        if (exist == 0) {
            generateOrganisms();
        }
        drawOrganisms();
    }

    public final void makeTurn() {
        setDefaultColors();
        turnCount_++;
        sortOrganisms(); //organisms are prepared for the begining of the new 
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).action();
        }

        deleteOrganisms();
        drawOrganisms();
        notifyTurnInfoListener("Turn number " + turnCount_);
    }

    public void drawOrganisms() {
        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).draw();
        }
    }

    public final void sortOrganisms() {
        Collections.sort(organisms, new Comparator<Organism>() {
            @Override
            public int compare(Organism o1, Organism o2) {
                if (o1.getPriority() == o2.getPriority()) {
                    return o1.getAge() - o2.getAge(); //sort descending
                } else {
                    return o1.getPriority() - o2.getPriority();
                }
            }
        });

    }

    public final void generateOrganisms() {


        ArrayList<Point> points = new ArrayList<Point>(width_ * height_);
        for (int i = 0; i < width_; i++) {
            for (int j = 0; j < height_; j++) {
                points.add(new Point(i, j));
            }
        }
        int randomNum = ThreadLocalRandom.current().nextInt(0, points.size());
        generateOrganism(points.get(randomNum), ALL_SIGNS[0]);
        points.remove(randomNum);
        for (int i = 0; i < log(width_ * height_); i++) {

            for (int j = 1; j < ALL_SIGNS.length; j++) {
                randomNum = ThreadLocalRandom.current().nextInt(0, points.size());
                generateOrganism(points.get(randomNum), ALL_SIGNS[j]);
                points.remove(randomNum);
            }
        }
    }

    public final void generateOrganism(Point point, char species) {
        switch (species) {
            case ANTELOPE_SIGN:
                organisms.add(new Antelope(this, point));
                break;
            case SOSNOWSKIS_HOGWEED_SIGN:
                organisms.add(new SosnowskisHogweed(this, point));
                break;
            case HUMAN_SIGN:
                organisms.add(new Human(this, point));
                break;
            case GUARANA_SIGN:
                organisms.add(new Guarana(this, point));
                break;
            case FOX_SIGN:
                organisms.add(new Fox(this, point));
                break;
            case DANDELION_SIGN:
                organisms.add(new Dandelion(this, point));
                break;
            case SHEEP_SIGN:
                organisms.add(new Sheep(this, point));
                break;
            case GRASS_SIGN:
                organisms.add(new Grass(this, point));
                break;
            case BELLADONNA_SIGN:
                organisms.add(new Belladonna(this, point));
                break;
            case WOLF_SIGN:
                organisms.add(new Wolf(this, point));
                break;
            case TORTOISE_SIGN:
                organisms.add(new Tortoise(this, point));
                break;
            default:
                break;
        }
    }

    public final void addOrganism(Organism organism) {
        organisms.add(organism);
    }

    public final void deleteOrganisms() {
        Iterator<Organism> it = organisms.iterator();
        while (it.hasNext()) {
            if (it.next().getIsAlive() == false) {
                it.remove();
            }
        }
    }

    public final void showOrganisms() {
        for (int i = 0; i < organisms.size(); i++) {

            notifyTurnInfoListener((i + 1) + ". " + organisms.get(i) + " (" + organisms.get(i).point_.getX() + "," + organisms.get(i).point_.getY() + ")\n");
        }
    }

    public final int organismsNumber() {
        return organisms.size();
    }

    public final ArrayList<Organism> neighbours(Organism organism) {
        int x_neighbour, y_neighbour;
        ArrayList<Organism> neighboursOrganism = new ArrayList<Organism>();
        for (int i = 0; i < organisms.size(); i++) {

            x_neighbour = organisms.get(i).getPoint().getX();
            y_neighbour = organisms.get(i).getPoint().getY();
            if (organisms.get(i).getIsAlive()
                    && organisms.get(i).getId() != organism.getId()
                    && organism.getPoint().aroundPoint(x_neighbour, y_neighbour, boardType_)) {
                neighboursOrganism.add(organisms.get(i));
            }
        }
        return neighboursOrganism;
    }

    public final int neighboursNumer(Organism organism) {
        int sum = 0;
        int x_neighbour, y_neighbour;
        for (int i = 0; i < organisms.size(); i++) {
            x_neighbour = organisms.get(i).getPoint().getX();
            y_neighbour = organisms.get(i).getPoint().getY();
            if (organism.getPoint().aroundPoint(x_neighbour, y_neighbour, boardType_)) {
                sum++;
            }
        }
        return sum;
    }

    public final Point randomFreePlace(Organism organism) {
        boolean[] free = new boolean[8];
        final int x = organism.getPoint().getX();
        final int y = organism.getPoint().getY();
        final int n = 1;
        int q = 0;
        //*********************
        if (y - n < 0) {//TOP
            free[q] = false;
        } else {
            free[q] = fieldFree(x, y - n);
        }
        q++;
        if (x + n >= width_ || y - n < 0||(boardType_==HEXAGON&&x%2==1)) {//RIGHT_TOP
            free[q] = false;
        } else {
            free[q] = fieldFree(x + n, y - n);
        }
        q++;
        if (x + n >= width_) {//RIGHT
            free[q] = false;
        } else {
            free[q] = fieldFree(x + n, y);
        }
        q++;
        if (x + n >= width_ || y + n >= height_||(boardType_==HEXAGON&&x%2==0)) {//RIGHT_DOWN
            free[q] = false;
        } else {
            free[q] = fieldFree(x + n, y + n);
        }
        q++;
        if (y + n >= height_) {//DOWN
            free[q] = false;
        } else {
            free[q] = fieldFree(x, y + n);
        }
        q++;
        if (x - n < 0 || y + n >= height_||(boardType_==HEXAGON&&x%2==0)) {//LEFT_DOWN
            free[q] = false;
        } else {
            free[q] = fieldFree(x - n, y + n);
        }
        q++;
        if (x - n < 0) {//LEFT
            free[q] = false;
        } else {
            free[q] = fieldFree(x - n, y);
        }
        q++;
        if (x - n < 0 || y - n < 0||(boardType_==HEXAGON&&x%2==1)) {//LEFT_TOP
            free[q] = false;
        } else {
            free[q] = fieldFree(x - n, y - n);
        }
        //***************************

        ArrayList<Direction> directions = new ArrayList<Direction>();
        for (int i = 0; i < 8; i++) {
            if (free[i] == true) {
                directions.add(Direction.forValue(i));
            }
        }
        if (directions.isEmpty() != true) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, directions.size());

            return new Point(x, y, directions.get(randomNum));
        }
        return null;
    }

    public final Organism fieldTaken(Point point) {
        int x = point.getX();
        int y = point.getY();

        for (int i = 0; i < organisms.size(); i++) {

            if (organisms.get(i).getPoint().equals(x, y) && organisms.get(i).getIsAlive()) {
                return organisms.get(i);
            }
        }
        return null;
    }

    public final boolean fieldFree(int x, int y) {

        for (int i = 0; i < organisms.size(); i++) {

            if (organisms.get(i).getPoint().equals(x, y)) {
                return false;
            }
        }

        return true;
    }

    public final int getInput() {
        return input_;
    }

    public final void setInput(int input) {
        input_ = input;
    }

    public final int getWidth() {
        return width_;
    }

    public final int getHeight() {
        return height_;
    }

    void setToDraw(int x, int y, Color color) {
        toDraw_[y][x] = color;
    }

    public Color[][] getFieldsColor() {
        return toDraw_;
    }

    void setDefaultColors() {
        for (int i = 0; i < width_; i++) {
            for (int j = 0; j < height_; j++) {
                toDraw_[j][i] = DEFAULT_FIELD_COLOR;
            }
        }
    }

    public void saveGame() throws IOException {
        //System.out.println("Working Directory = " +System.getProperty("user.dir"));

        //String file=new String(System.getProperty("user.dir")+"\\src\\meadowgame\\gameSaves\\save.txt");
        String file = new String("save.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        writer.write(width_ + " " + height_ + " " + turnCount_ + " " + boardType_);
        writer.newLine();

        for (int i = 0; i < organisms.size(); i++) {
            organisms.get(i).save(writer);
            writer.newLine();
        }
        writer.close();
    }

    public void addTurnInfo(MyTurnInfoListener info) {
        turnInfo.add(info);
    }

    public void notifyTurnInfoListener(String description) {
        for (MyTurnInfoListener turnInfoDes : turnInfo) {
            turnInfoDes.addTurnInfo(description);
        }
    }

    public void importOrganism(String line) {
        String[] parts = line.split(" ");
        int age = Integer.parseInt(parts[3]);
        int id = Integer.parseInt(parts[4]);
        int strength = Integer.parseInt(parts[5]);
        Point point = new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        switch (parts[0].charAt(0)) {

            case ANTELOPE_SIGN:
                organisms.add(new Antelope(this, point, age, id, strength));
                break;
            case SOSNOWSKIS_HOGWEED_SIGN:
                organisms.add(new SosnowskisHogweed(this, point, age, id, strength));
                break;
            case HUMAN_SIGN:
                organisms.add(new Human(this, point, age, id, strength, Integer.parseInt(parts[6])));
                break;
            case GUARANA_SIGN:
                organisms.add(new Guarana(this, point, age, id, strength));
                break;
            case FOX_SIGN:
                organisms.add(new Fox(this, point, age, id, strength));
                break;
            case DANDELION_SIGN:
                organisms.add(new Dandelion(this, point, age, id, strength));
                break;
            case SHEEP_SIGN:
                organisms.add(new Sheep(this, point, age, id, strength));
                break;
            case GRASS_SIGN:
                organisms.add(new Grass(this, point, age, id, strength));
                break;
            case BELLADONNA_SIGN:
                organisms.add(new Belladonna(this, point, age, id, strength));
                break;
            case WOLF_SIGN:
                organisms.add(new Wolf(this, point, age, id, strength));
                break;
            case TORTOISE_SIGN:
                organisms.add(new Tortoise(this, point, age, id, strength));
                break;
            default:
                break;
        }

    }

    public void changeBoardType() {
        switch (boardType_) {

            case RECTANGLE:
                boardType_ = HEXAGON;
                break;

            case HEXAGON:
                boardType_ = RECTANGLE;
                break;
        }
    }

    public char getBoardType() {
        return boardType_;
    }

}
