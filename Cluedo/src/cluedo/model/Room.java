package cluedo.model;

import java.util.ArrayList;

public class Room {
    private final Card.ROOM name;
    private ArrayList<Chara> charasInside;
    private ArrayList<Weapon> weaponsInside;
    //Array of inner coordinates

    public Room(Card.ROOM name) {
        this.name = name;
    }

    /**
     * get method for the name;
     * @return String
     */
    public Card.ROOM getName() {
        return name;
    }
}
