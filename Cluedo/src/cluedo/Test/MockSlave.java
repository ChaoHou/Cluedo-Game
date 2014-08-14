package cluedo.Test;

import cluedo.model.*;
import cluedo.view.*;

import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by innocence on 12/08/2014.
 */
public class MockSlave implements MouseListener{
    //private final Board board;

    public MockSlave() {
        //this.board = new Board(20*24, 20*26);
    }


    public void run() {
        int cell = 20;
        //new BoardFrame("test",board,this,null);
    }


    //main method for test
    public static void main(String[] arg) {
        //Board board = new Board(20*24,20*26);
        new MockSlave().run();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
