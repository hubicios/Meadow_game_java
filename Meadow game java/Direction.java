package meadowgame;

import java.util.*;

//KIERUNKI
public enum Direction {
    TOP(0),
    RIGHT_TOP(1),
    RIGHT(2),
    RIGHT_DOWN(3),
    DOWN(4),
    LEFT_DOWN(5),
    LEFT(6),
    LEFT_TOP(7),
    DIRECTION_NONE(8);

    public static final int SIZE = java.lang.Integer.SIZE;

    private int intValue;
    private static java.util.HashMap<Integer, Direction> mappings;

    private static java.util.HashMap<Integer, Direction> getMappings() {
        if (mappings == null) {
            synchronized (Direction.class) {
                if (mappings == null) {
                    mappings = new java.util.HashMap<Integer, Direction>();
                }
            }
        }
        return mappings;
    }

    private Direction(int value) {
        intValue = value;
        getMappings().put(value, this);
    }

    public int getValue() {
        return intValue;
    }

    public static Direction forValue(int value) {
        return getMappings().get(value);
    }

    public static Direction forValue(Direction value) {
        return getMappings().get(value);
    }

}
