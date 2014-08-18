package cluedo.model;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Weapon {
    private final Card.WEAPON name;

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
        dos.writeByte(getName().ordinal());
    }

    public void fromInputStream(DataInputStream dis) throws IOException{
        Weapon temp = new Weapon(Card.WEAPON.values()[dis.readByte()]);
    }

    public void setInRoom(Room room) {
        room.setInRoom(this);
    }
}
