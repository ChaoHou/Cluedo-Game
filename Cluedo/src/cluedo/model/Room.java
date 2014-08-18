package cluedo.model;

import cluedo.controller.action.server.Move;
import cluedo.view.drawing.Coordinates;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Room {
    private final Card.ROOM name;
    private ArrayList<Chara> charasInside;
    private ArrayList<Weapon> weaponsInside;
    //Array of inner coordinates

    public Room(Card.ROOM name) {
        this.name = name;
        charasInside = new ArrayList<Chara>();
        weaponsInside = new ArrayList<Weapon>();
    }

    /**
     * get method for the name;
     * @return String
     */
    public Card.ROOM getName() {
        return name;
    }

    /**
     * return index of a room corresponding given coordinate
     * @param c
     * @return
     */
    public static int getRoomIndex(Coordinates c) {
        if (c.equals(4,6)) {return Card.ROOM.KITCHEN.ordinal();}
        if (c.equals(6,16)
                ||c.equals(7,13)) {return Card.ROOM.DINING_ROOM.ordinal();}
        if (c.equals(6,20)) {return Card.ROOM.LOUNGE.ordinal();}
        if (c.equals(8, 6)
                || c.equals(9, 8)
                || c.equals(14, 8)
                || c.equals(15,6 )) {return Card.ROOM.BALL_ROOM.ordinal();}
        if (c.equals(11,19)
                || c.equals(12,19)
                || c.equals(14,21)) {return Card.ROOM.HALL.ordinal();}
        if (c.equals(17,17)
                ||c.equals(20,15)) {return Card.ROOM.LIBRARY.ordinal();}
        if (c.equals(17 ,22)) {return Card.ROOM.STUDY.ordinal();}
        if (c.equals(18, 5)) {return Card.ROOM.CONSERVATORY.ordinal();}
        if (c.equals(18 ,10)
                ||c.equals(22, 13)) {return Card.ROOM.BILLIARD_ROOM.ordinal();}
        return 0;
    }

    public Coordinates getC(Move.Direction direction) {
        if (direction == Move.Direction.UP) {
            if(this.getName() == Card.ROOM.LOUNGE){
                return new Coordinates(6,19);
            }
            if(this.getName() == Card.ROOM.LIBRARY){
                return new Coordinates(20,14);
            }
            if(this.getName() == Card.ROOM.STUDY){
                return new Coordinates(17,21);
            }
        }
        if (direction == Move.Direction.DOWN) {
            if(this.getName() == Card.ROOM.KITCHEN){
                return new Coordinates(4,7);
            }
            if(this.getName() == Card.ROOM.CONSERVATORY){
                return new Coordinates(18,6);
            }
            if(this.getName() == Card.ROOM.BILLIARD_ROOM){
                return new Coordinates(22,14);
            }
            if(this.getName() == Card.ROOM.DINING_ROOM){
                return new Coordinates(6,17);
            }
        }
        if (direction == Move.Direction.LEFT) {
            if(this.getName() == Card.ROOM.BALL_ROOM){
                return new Coordinates(7,6);
            }
            if(this.getName() == Card.ROOM.BILLIARD_ROOM){
                return new Coordinates(17,10);
            }
            if(this.getName() == Card.ROOM.LIBRARY){
                return new Coordinates(16,17);
            }

        }
        if (direction == Move.Direction.RIGHT) {
            if(this.getName() == Card.ROOM.BALL_ROOM){
                return new Coordinates(16,6);
            }
            if(this.getName() == Card.ROOM.DINING_ROOM){
                return new Coordinates(8,13);
            }
            if(this.getName() == Card.ROOM.HALL){
                return new Coordinates(15,21);
            }

        }
        return null;
    }

    public Coordinates getRoomOrigin() {
        if (name == Card.ROOM.KITCHEN){return new Coordinates(1,2);}
        if (name == Card.ROOM.BALL_ROOM){return new Coordinates(9,2);}
        if (name == Card.ROOM.CONSERVATORY){return new Coordinates(19,2);}
        if (name == Card.ROOM.DINING_ROOM){return new Coordinates(1,11);}
        if (name == Card.ROOM.BILLIARD_ROOM){return new Coordinates(19,9);}
        if (name == Card.ROOM.LIBRARY){return new Coordinates(18,16);}
        if (name == Card.ROOM.LOUNGE){return new Coordinates(1,21);}
        if (name == Card.ROOM.HALL){return new Coordinates(10,20);}
        if (name == Card.ROOM.STUDY){return new Coordinates(19,23);}
        return null;
    }

    /**
     * set given param in this room
     * @param chara
     */
    public void setInRoom(Chara chara) {
        charasInside.add(chara);
    }

    /**
     * set given param in the room
     * @param weapon
     */
    public void setInRoom(Weapon weapon) {
        weaponsInside.add(weapon);
    }

    /**
     * remove given param from the room
     * @param chara
     */
    public void outFromRoom(Chara chara) {
        charasInside.remove(chara);
    }

    /**
     * remove given param from the room
     * @param weapon
     */
    public void outFromRoom(Weapon weapon) {
        charasInside.remove(weapon);
    }

    public void draw(Graphics2D g2, int cell) {
        if (charasInside.isEmpty()){return;}
        for (int i = 0; i<charasInside.size();++i) {
            Chara chara = charasInside.get(i);
            g2.setColor(chara.getCColor(chara.getName()));
            Coordinates c = getRoomOrigin();
            g2.fillOval((c.x+i) * cell, c.y * cell, cell, cell);
        }
        if (weaponsInside.isEmpty()) {return;}
        for (int i = 0; i<weaponsInside.size();++i){
            Weapon weapon = weaponsInside.get(i);
            g2.setColor(Color.BLACK);
            Coordinates c = getRoomOrigin();
            g2.fillOval((c.x+1) * cell, (c.y+i) * cell, cell, cell);

        }
    }

    public void toOutputStream(DataOutputStream dos) throws  IOException{
        dos.writeByte(charasInside.size());
        //stores tokens positions
        if (charasInside.size() != 0) {
            for (Chara c : charasInside) {
                c.toOutputStream(dos);
            }
        }

        dos.writeByte(weaponsInside.size());
        //stores rooms tokens positions
        if (weaponsInside.size() != 0) {
            for (Weapon w: weaponsInside) {
                w.toOutputStream(dos);
            }
        }
    }

    public void fromInputStream(DataInputStream dis) throws IOException{
        //retrieve tokens positions
        if (dis.readByte() != 0) {
            for (Chara c : charasInside) {
                c.fromInputStream(dis);
            }
        }

        //retrieve weapon tokens positions
        if (dis.readByte() != 0) {
            for (Weapon w: weaponsInside) {
                w.fromInputStream(dis);
            }
        }
    }
}
