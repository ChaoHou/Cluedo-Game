package cluedo.model;

import cluedo.view.drawing.Coordinates;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Chara {
    private final Card.CHARACTER name;
    private int xCoordinate;
    private int yCoordinate;
    private boolean isInRoom; // is it in a room?
    private Room room; // yes, where? no, null

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
        dos.writeBoolean(isInRoom);
        dos.writeByte(xCoordinate);
        dos.writeByte(yCoordinate);
    }

    public static Chara fromInputStream(DataInputStream dis) throws IOException{
        Chara temp = new Chara(Card.CHARACTER.values()[dis.readByte()]);
        temp.isInRoom = dis.readBoolean();
        temp.setPosition(dis.readByte(), dis.readByte());
        return temp;
    }


    public void setInRoom(Room room) {
        isInRoom = true;
        this.room = room;
    }

    public boolean isInRoom() {
        return isInRoom;
    }

    public Room getRoom() {
        return room;
    }

    public void draw(Graphics2D g2, int cell) {
//        if (isInRoom) {return;}
            g2.setColor(getCColor(getName()));
            g2.fillOval(getX() * cell, getY() * cell, cell, cell);
    }

    /**
     * help method for drawing chara
     * @param c
     * @return
     */
    public Color getCColor(Card.CHARACTER c) {
        switch (c.ordinal()) {
            case 0:
                return Color.red;
            case 1:
                return Color.yellow;
            case 2:
                return Color.white;
            case 3:
                return Color.green;
            case 4:
                return Color.blue;
            case 5:
                return new Color(255, 0, 255);
            default:
                return null;
        }
    }

    public void outFromRoom() {
        isInRoom = false;
        room.outFromRoom(this);
    }
}
