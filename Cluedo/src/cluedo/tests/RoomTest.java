package cluedo.tests;

import cluedo.model.Card;
import cluedo.model.Chara;
import cluedo.model.Room;
import cluedo.model.Weapon;
import org.junit.Test;

import java.awt.*;
import java.io.*;

/**
 * Created by innocence on 18/08/2014.
 */

public class RoomTest extends Room {
    public RoomTest() {
        super(Card.ROOM.STUDY);
    }

    @Test
    public void roomTest() throws IOException{
        setInRoom(new Weapon(Card.WEAPON.DAGGER));
        setInRoom(new Chara(Card.CHARACTER.SCARLETT));

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);

        toOutputStream(dout);
        dout.flush();

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        DataInputStream din = new DataInputStream(bin);

        Room test = fromInputStream(din);

        assert(this.getName().equals(test.getName()));
        assert(this.getCharactersInside().get(0).getName().equals(test.getCharactersInside().get(0).getName()));
        assert(this.getWeaponsInside().get(0).getName().equals(test.getWeaponsInside().get(0).getName()));
    }
}
