package cluedo.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Weapon {
    private final Card.WEAPON name;
    private int xCoordinate;
    private int yCoordinate;

    public Weapon(Card.WEAPON name) {
        this.name = name;
    }

    /**
     * get method for the name
     * @return String
     */
    public Card.WEAPON getName() {
        return name;
    }

    public void toOutputStream(DataOutputStream dos) throws IOException{
        dos.writeByte(xCoordinate);
        dos.writeByte(yCoordinate);
    }

    public void fromInputStream(DataInputStream dis) throws IOException{
        xCoordinate = dis.readByte();
        yCoordinate = dis.readByte();
    }
}
