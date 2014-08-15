package cluedo.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Card {
    private final TYPE type;
    private String name;

    public enum TYPE {
        ROOM,
        WEAPON,
        CHARCTER,
    }

    public enum ROOM {
        KITCHEN,
        BALL_ROOM,
        CONSERVATORY,
        DINING_ROOM,
        BILLIARD_ROOM,
        LIBRARY,
        LOUNGE,
        HALL,
        STUDY,
    }

    public enum WEAPON {
        CANDLESTICK,
        DAGGER,
        LEAD_PIPE,
        REVOLVER,
        ROPE,
        SPANNER,
    }

    public enum CHARACTER {
        SCARLETT,
        MUSTARD,
        WHITE,
        GREEN,
        PEACOCK,
        PLUM,
    }

    public Card(TYPE type, String name) {
        this.type = type;
        switch (type.ordinal()){
            case 1:
                for (int i = 0; i < WEAPON.values().length; ++i){
                    if (name.equals(WEAPON.values()[i].toString())) {
                        this.name = name;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < CHARACTER.values().length; ++i){
                    if (name.equals(CHARACTER.values()[i].toString())) {
                        this.name = name;
                    }
                }
                break;
            default:
                for (int i = 0; i < ROOM.values().length; ++i){
                    if (name.equals(ROOM.values()[i].toString())) {
                        this.name = name;
                    }
                }
                break;
        }
    }

    public TYPE getType() {
        return this.type;
    }

    public String getName() {
        return name;
    }

    public void toOutputStream(DataOutputStream dos) throws IOException{
        dos.writeByte(type.ordinal());
        dos.writeByte(name.length());
        byte[] b = name.getBytes("UTF-8");
        dos.write(b);
    }

    public static Card newCardFromByte(DataInputStream dis) throws IOException {
        int type = dis.readByte();
        int length = dis.readByte();
        byte[] tName = new byte[length];
        dis.read(tName);
        return new Card(Card.TYPE.values()[type],new String(tName, "UTF-8"));
    }
}
