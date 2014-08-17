package cluedo.tests;

import cluedo.model.*;
import org.junit.Test;

import java.io.*;

/**
 * Created by innocence on 17/08/2014.
 */
public class CharaTest extends Chara {

    public CharaTest() {
        super(Card.CHARACTER.SCARLETT);
    }
    @Test
    public void testChara() throws IOException{
        setPosition(8,8);
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);

        toOutputStream(dout);
        dout.flush();

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        DataInputStream din = new DataInputStream(bin);

        Chara test = fromInputStream(din);

        System.out.printf("Chara: %s %s\n",getName(), test.getName());
        System.out.printf("x: %d %d\n",getX(), test.getX());
        System.out.printf("y: %d %d\n",getY(), test.getY());
    }
}
