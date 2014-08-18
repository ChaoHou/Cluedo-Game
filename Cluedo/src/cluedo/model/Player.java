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
        REINITIALIZING,
        INITIALIZING,
        WAITING,
        FINISHREFUTE,
    }

    private final int uid;
    private STATUS status;
    private String uName = "";
    private ArrayList<Card> cards;
    private Chara character;
    private int dice;
    private int stepsRemain;
    private String message = "";

    /**
     * stores uid from constructor
     * set default status WATCHING
     * @param uid
     */
    public Player(int uid) {
        this.uid = uid;
        cards = new ArrayList<Card>();
        setStatus(STATUS.WATCHING);
    }

    /**
     * make a Player into ByteArray information
     * @param dos
     * @throws IOException
     */
    public void toOutputStream(DataOutputStream dos) throws IOException{
        dos.writeByte(uid);
        dos.writeByte(status.ordinal());
        dos.writeByte(uName.length());
        byte[] b = uName.getBytes("UTF-8");
        dos.write(b);
        dos.writeByte(message.length());
        byte[] m = message.getBytes("UTF-8");
        dos.write(m);
        if (character != null) {
            dos.writeBoolean(true);
            character.toOutputStream(dos);
        }
        else {dos.writeBoolean(false);}
        dos.writeByte(dice);
        //write numbers of cards
        dos.writeByte(cards.size());
        for (Card c: cards) {
            c.toOutputStream(dos);
        }
    }

    /**
     * initialise a Player from ByteArray
     * @param dis
     * @return
     * @throws IOException
     */
    public static Player fromInputStream(DataInputStream dis) throws IOException{
        Player temp = new Player(dis.readByte());
        temp.setStatus(Player.STATUS.values()[dis.readByte()]);
        //read name
        int nameLength = dis.readByte();
        byte[] nTemp = new byte[nameLength];
        dis.read(nTemp);
        temp.setUName(new String(nTemp,"UTF-8"));
        int mesgLength = dis.readByte();
        nTemp = new byte[mesgLength];
        dis.read(nTemp);
        temp.setUName(new String(nTemp,"UTF-8"));
        if (dis.readBoolean()) {
            Chara tempC = Chara.fromInputStream(dis);
            temp.setCharacter(tempC);
        }
        temp.setDice(dis.readByte());

        //read cards
        temp.getCards().clear();
        int size = dis.readByte();
//        System.out.printf("%d\n",size);
        for (int i = 0; i < size; ++i) {
            temp.getCards().add(Card.newCardFromByte(dis));
        }

        return temp;
    }

    /**
     * set the player's status to given status
     * @param status
     */
    public void setStatus(STATUS status) {
        this.status = status;
    }

    /**
     * return current player's status
     * @return
     */
    public STATUS getStatus() {
        return status;
    }

    /**
     * set user name user picked
     * @param name
     */
    public void setUName(String name) {
        this.uName = name;
    }

    /**
     * set character user picked
     * @param character
     */
    public void setCharacter(Chara character){
        this.character = character;
    }

    /**
     * return character in the Player
     * @return
     */
    public Chara getCharacter() {
        return character;
    }

    /**
     * set dice and corresponding remain steps
     * @param dice
     */
    public void setDice(int dice) {
        this.dice = dice;
        this.stepsRemain = dice;
    }

    /**
     * decrement remaining steps
     */
    public void decStepR() {
        --stepsRemain;
    }

    /**
     * return uid of the player
     * @return
     */
    public int getUid() {
        return uid;
    }

    /**
     * return hand's of the player
     * @return
     */
    public ArrayList<Card> getCards(){
        return cards;
    }

    public boolean equals(Object o) {
        if (o instanceof Player) {
            Player temp =(Player) o;
            return temp.getUid() == this.uid && temp.getUName().equals(this.uName);
        }

        return false;
    }

    /**
     * return user name input by user
     * @return
     */
    public String getUName() {
        return uName;
    }

    /**
     * return how many steps remain
     * @return
     */
    public int getStepsRemain() {
        return stepsRemain;
    }

    /**
     * return how much user have got
     * @return
     */
    public int getDice() {
        return dice;
    }

    /**
     * used for storing personal message on each player's canvas
     * @param str
     */
    public void setString(String str) {
        message = str;
    }

    /**
     * get stored personal information for the user
     * @return
     */
    public String getString() {
        return message;
    }
}
