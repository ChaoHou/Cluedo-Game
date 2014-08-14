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
    private Chara character;
    private int dice;

    public Player(long uid) {
        this.uid = uid;

    }

    public void setCharacter(Chara character){
        this.character = character;
    }

}
