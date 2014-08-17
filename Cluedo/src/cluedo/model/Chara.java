package cluedo.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Chara {
    private final Card.CHARACTER name;
    private int xCoordinate;
    private int yCoordinate;

    public Chara(Card.CHARACTER name) {
        this.name = name;

        setStartPos();
    }

    /**
     * get character's name
     * @return Card.CHARACTER
     */
    public Card.CHARACTER getName() {
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

    /**
     * set position to given value x = 0..24, y = 0..26
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
        this.xCoordinate = x;
        this.yCoordinate = y;
    }

    /**
     * set the character into certain start position depends on character
     */
    private void setStartPos() {
        switch(name.ordinal()){
            case 0:
                xCoordinate = 7;
                yCoordinate = 25;
                break;
            case 1:
                xCoordinate = 0;
                yCoordinate = 18;
                break;
            case 2:
                xCoordinate = 9;
                yCoordinate = 0;
                break;
            case 3:
                xCoordinate = 14;
                yCoordinate = 0;
                break;
            case 4:
                xCoordinate = 23;
                yCoordinate = 7;
                break;
            case 5:
                xCoordinate = 23;
                yCoordinate = 20;
                break;
        }

    }

    public void toOutputStream(DataOutputStream dos) throws IOException{
        dos.writeByte(getName().ordinal());
        dos.writeByte(xCoordinate);
        dos.writeByte(yCoordinate);
    }

    public static Chara fromInputStream(DataInputStream dis) throws IOException{
        Chara temp = new Chara(Card.CHARACTER.values()[dis.readByte()]);
        temp.setPosition(dis.readByte(),dis.readByte());
        return temp;
    }
}
