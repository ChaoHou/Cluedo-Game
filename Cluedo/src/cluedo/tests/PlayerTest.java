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
        getCards().add(new Card(Card.TYPE.CHARCTER, "SCARLETT"));
        getCards().add(new Card(Card.TYPE.CHARCTER, "WHITE"));
        getCards().add(new Card(Card.TYPE.CHARCTER, "GREEN"));
        getCharacter().setPosition(8,8);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);

        toOutputStream(dout);
        dout.flush();

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        DataInputStream din = new DataInputStream(bin);

        Player test = fromInputStream(din,null);

        System.out.printf("Uid: %d %d\n", this.getUid(), test.getUid());
        System.out.printf("UName: %s %s\n",this.getUName(),test.getUName());
        System.out.printf("dice: %d %d\n", this.getDice(), test.getDice());
        System.out.printf("stepRemain: %d %d\n",this.getStepsRemain(),test.getStepsRemain());
        System.out.printf("x: %d %d\n",this.getCharacter().getX(),test.getCharacter().getX());
        System.out.printf("y: %d %d\n",this.getCharacter().getY(),test.getCharacter().getY());
        System.out.printf(" \n");

        assert(this.equals(test));
        assert(this.getCards().equals(test.getCards()));
    }

    @Test
    public void playerToArrayTest2() throws IOException{
        setStatus(STATUS.INITIALIZING);
        setUName("Kyohei");
        setCharacter(new Chara(Card.CHARACTER.SCARLETT));
        setDice(7);
        getCards().add(new Card(Card.TYPE.CHARCTER, "SCARLETT"));
        getCards().add(new Card(Card.TYPE.CHARCTER, "WHITE"));
        getCards().add(new Card(Card.TYPE.CHARCTER, "GREEN"));
        getCharacter().setPosition(8,8);

        Player p2 = new Player(4);
        p2.setStatus(STATUS.INITIALIZING);
        p2.setUName("Chao");
        p2.setCharacter(new Chara(Card.CHARACTER.GREEN));
        p2.setDice(7);
        p2.getCards().add(new Card(Card.TYPE.WEAPON, "DAGGER"));
        p2.getCards().add(new Card(Card.TYPE.WEAPON, "ROPE"));
        p2.getCards().add(new Card(Card.TYPE.WEAPON, "REVOLVER"));
        p2.getCharacter().setPosition(10,10);

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);

        toOutputStream(dout);
        p2.toOutputStream(dout);
        dout.flush();

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        DataInputStream din = new DataInputStream(bin);

        Player test = fromInputStream(din,null);
        Player test2 = fromInputStream(din,null);

        System.out.printf("Uid: %d %d\n", this.getUid(), test.getUid());
        System.out.printf("UName: %s %s\n",this.getUName(),test.getUName());
        System.out.printf("dice: %d %d\n", this.getDice(), test.getDice());
        System.out.printf("stepRemain: %d %d\n",this.getStepsRemain(),test.getStepsRemain());
        System.out.printf("x: %d %d\n",this.getCharacter().getX(),test.getCharacter().getX());
        System.out.printf("y: %d %d\n",this.getCharacter().getY(),test.getCharacter().getY());

        System.out.printf("Card: %s %s %s\n",test.getCards().get(0).getName(),test.getCards().get(1).getName(),test.getCards().get(2).getName());

        System.out.printf("Uid: %d %d\n", p2.getUid(), test2.getUid());
        System.out.printf("UName: %s %s\n",p2.getUName(),test2.getUName());
        System.out.printf("dice: %d %d\n", p2.getDice(), test2.getDice());
        System.out.printf("stepRemain: %d %d\n",p2.getStepsRemain(),test2.getStepsRemain());
        System.out.printf("x: %d %d\n",p2.getCharacter().getX(),test2.getCharacter().getX());
        System.out.printf("y: %d %d\n",p2.getCharacter().getY(),test2.getCharacter().getY());

        System.out.printf("Card: %s %s %s\n",test2.getCards().get(0).getName(),test2.getCards().get(1).getName(),test2.getCards().get(2).getName());

        assert(this.equals(test));
        assert(this.getCards().equals(test.getCards()));
        assert(p2.equals(test2));
        assert(p2.getCards().equals(test2.getCards()));
    }

    @Test
    public void playerToArrayTest3() throws IOException{
        setStatus(STATUS.INITIALIZING);
        setUName("Kyohei");
        setDice(7);
        getCards().add(new Card(Card.TYPE.CHARCTER, "SCARLETT"));
        getCards().add(new Card(Card.TYPE.CHARCTER, "WHITE"));
        getCards().add(new Card(Card.TYPE.CHARCTER, "GREEN"));

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        DataOutputStream dout = new DataOutputStream(bout);

        toOutputStream(dout);
        dout.flush();

        ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
        DataInputStream din = new DataInputStream(bin);

        Player test = fromInputStream(din,null);

        System.out.printf("Uid: %d %d\n", this.getUid(), test.getUid());
        System.out.printf("UName: %s %s\n",this.getUName(),test.getUName());
        System.out.printf("dice: %d %d\n", this.getDice(), test.getDice());
        System.out.printf("stepRemain: %d %d\n",this.getStepsRemain(),test.getStepsRemain());
        System.out.printf(" \n");

        assert(this.equals(test));
        assert(this.getCards().equals(test.getCards()));
    }

}
