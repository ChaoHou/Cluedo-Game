package cluedo.view;

import cluedo.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

public class BoardFrame extends JFrame {
    private final BoardCanvas canvas;
    private boolean rollDisabled;
    private boolean actionDisabled;
    private boolean refuteDisabled;

    public BoardFrame(String title, Board game, MouseListener... mouse) {
        super(title);

        canvas = new BoardCanvas(/* uid,*/game);
        //Master doesn't have listener
        for (MouseListener m: mouse) {
            canvas.addMouseListener(m);
        }

        //does this line necessary?
        setLayout(new BorderLayout());

        add(canvas, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create panel for buttons
//        JPanel p = new JPanel();

        //pack components nicely
        pack();
        //we don't want user to change window size
        setResizable(false);

        //set UI visible
        setVisible(true);
    }

    /**
     *  disable the roll button
     */
    public void disableRoll() {

    }

    public void enableRoll() {

    }

    /**
     * disabled the ability to make announcement or accusation. For example disable buttons.
     */
    public void disableAction() {

    }

    public void enableAction() {

    }

    /**
     * disable the ability to select a card to refute.
     *
     */
    public void disableRefute() {

    }

    public void enableRefute() {

    }

    public void initPlayer() {

    }

    public void showMessage(String str) {

    }

    public void repaint() {
        canvas.repaint();
    }


}
