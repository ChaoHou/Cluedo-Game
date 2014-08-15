package cluedo.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
    private String uName;
    private ArrayList<Card> cards;
    private Chara character;
    private int dice;
    private int stepsRemain;

    public Player(long uid) {
        this.uid = uid;

    }

    public void toOutputStream(DataOutputStream dos) throws IOException{
        dos.writeByte(status.ordinal());
        dos.writeLong(uid);
        dos.writeByte(uName.length());
        dos.writeBytes(uName);
        dos.writeByte(character.getName().ordinal());
        dos.writeByte(dice);
        dos.writeByte(stepsRemain);
        //write numbers of cards
        dos.writeByte(cards.size());
        for (Card c: cards) {
            c.toOutputStream(dos);
        }
    }

    public void fromInputStream(DataInputStream dis) {

    }

    /**
     * set the player's status to given status
     * @param status
     */
    public void setStatus(STATUS status) {
        this.status = status;
    }

    public void setUName(String name) {
        this.uName = name;
    }

    public void setCharacter(Chara character){
        this.character = character;
    }

}
