package cluedo.Test;

import cluedo.model.*;
import cluedo.view.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by innocence on 12/08/2014.
 */
public class MockSlave implements MouseListener{

    //main method for test
    public static void main(String[] arg) {
        int cell = 20;
        new BoardFrame("test",new Board(cell*24,cell*26),null);
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
