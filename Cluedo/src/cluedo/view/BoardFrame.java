package cluedo.view;

import cluedo.model.*;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.MouseListener;

public class BoardFrame extends JFrame {
    private final BoardCanvas canvas;

    public BoardFrame(String title, Board game, MouseListener mouse) {
        super(title);

        canvas = new BoardCanvas(/* uid,game */);
        //it needs just one Listener?
        canvas.addMouseListener(mouse);

        //does this line necessary?
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //pack components nicely
        pack();
        //we don't want user to change window size
        setResizable(false);

        //set UI visible
        setVisible(true);
    }

    public void repaint() {
        canvas.repaint();
    }

}
