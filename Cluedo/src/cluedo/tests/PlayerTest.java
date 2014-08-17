package cluedo.tests;

import cluedo.model.*;
import org.junit.Test;

import java.io.*;

/**
 * Created by innocence on 16/08/2014.
 */
public class PlayerTest extends Player{

    public PlayerTest() {
        super(5);
    }

    @Test
    public void playerToArrayTest() throws IOException{
        setStatus(STATUS.INITIALIZING);
        setUName("Kyohei");
        setCharacter(new Chara(Card.CHARACTER.SCARLETT));
        setDice(7);
        setStepsRemain(9);
        getCards().add(new Card(Card.TYPE.CHARCTER, "SCARLETT"));
        getCards().add(new Card(Card.TYPE.CHARCTER, "WHITE"));
        getCards().add(new Card(Card.TYPE.CHARCTER, "GREEN"));

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);

        toOutputStream(dout);
        dout.flush();

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        DataInputStream din = new DataInputStream(bin);

        Player test = fromInputStream(din);

        System.out.printf("Uid: %d %d\n", this.getUid(), test.getUid());
        System.out.printf("UName: %s %s\n",this.getUName(),test.getUName());
        System.out.printf("dice: %d %d\n", this.getDice(), test.getDice());
        System.out.printf("stepRemain: %d %d\n",this.getStepsRemain(),test.getStepsRemain());

        assert(this.equals(test));
    }

}
