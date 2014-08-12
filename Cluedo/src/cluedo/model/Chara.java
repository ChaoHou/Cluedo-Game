package cluedo.model;

import java.awt.*;

public class Chara {
    private final String name;
    private int xCoordinate;
    private int yCoordinate;

    public Chara(String name) {
        this.name = name;
    }

    /**
     * get character's name
     * @return String
     */
    public String getName() {
        return name;
    }
    /**
     * get X coordinate of the character 0..24
     * @return int
     */
    public int getX() {
        return xCoordinate;
    }

    /**
     * get Y coorinate of character 0..26
     * @return int
     */
    public int getY() {
        return yCoordinate;
    }
}
