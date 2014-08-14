package cluedo.model;

import java.util.ArrayList;

public class Player {
    public enum STATUS {
        WATCHING,
        MOVING,
        MAKINGANNOUNCEMENT,
        ROLLING,
        REFUTING,
        ELIMINATED,
    }

    private STATUS status;
    private final long uid;
    private ArrayList<Card> cards;
    private final Chara character;
    private int dice;

    public Player(long uid, Chara character) {
        this.uid = uid;
        this.character = character;

    }


}
