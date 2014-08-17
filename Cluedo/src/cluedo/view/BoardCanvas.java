package cluedo.view;

import cluedo.model.*;
import cluedo.view.drawing.Arrow;
import cluedo.view.drawing.Dice;
import cluedo.view.drawing.Hand;

import java.awt.*;

public class BoardCanvas extends Canvas {
    private final Board board;
    private final int uid;


    /**
     * constructor
     * @param board
     */
    public BoardCanvas(Board board, int uid) {
        this.board = board;
        this.uid = uid;

        setSize(new Dimension(board.width(),board.height()));
    }

    /**
     * paints canvas.
     * @param g
     */
    public void paint(Graphics g) {
        Graphics2D g2 =(Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cell = board.width()/24;

        for (int x = 0; x < 24;x++) {
            for (int y = 0; y < 26; y++) {
                //draw board
                if (board.map[y].charAt(x) == 'C' || board.map[y].charAt(x) == 'S') {
                    g2.setColor(new Color(255, 237, 0));
                    g2.fillRect(x*cell, y*cell, cell, cell);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(x*cell, y*cell, cell, cell);
                } else if (board.map[y].charAt(x) == 'R'
                        || board.map[y].charAt(x) == 'D'
                        || board.map[y].charAt(x) == 'J' ) {
                    g2.setColor(new Color(58, 233, 22));
                    g2.fillRect(x*cell,y*cell,cell,cell);
                } else {
                    g2.setColor(new Color(0, 141, 255));
                    g2.fillRect(x*cell,y*cell,cell,cell);
                }

                //draw characters
                for (Chara c: board.getCharacters()) {
                    g2.setColor(getCColor(c.getName()));
                    g2.fillOval(c.getX() * cell, c.getY() * cell, cell, cell);
                }

                //draw lines for assisting making GUI
//                for (int X = 480; X < getBounds().width; X+=10) {
//                    g2.drawLine(X,0,X,getBounds().height);
//                }
//                for (int Y = 0; Y < 520; Y+=10) {
//                    g2.drawLine(480,Y,getBounds().width,Y);
//                }

                //height = 520, 480 < width < 710 is control panel
                Arrow.drawArrow(g2);
                // creates imitaition player for test purpose
                Player testP = new Player(uid);
                testP.getCards().add(new Card(Card.TYPE.CHARCTER, "SCARLETT"));
                testP.getCards().add(new Card(Card.TYPE.CHARCTER, "WHITE"));
                testP.getCards().add(new Card(Card.TYPE.CHARCTER, "GREEN"));

                //ends
                try {
                    Hand.drawHands(g2,testP/*board.getPlayer(uid)*/);
                } catch (Exception e) {
                    System.out.printf("No user! %d\n",uid);
                }

                testP.setDice(5);
                Dice.drawDice(g2,testP.getStepsRemain());

//                System.out.println(x+", "+y);
            }

        }

    }

    private Color getCColor(Card.CHARACTER c) {
        switch (c.ordinal()) {
            case 0:
                return Color.red;
            case 1:
                return Color.yellow;
            case 2:
                return Color.white;
            case 3:
                return Color.green;
            case 4:
                return Color.blue;
            case 5:
                return new Color(255, 0, 255);
            default:
                return null;
        }
    }

    public String defineClick() {
        return null;
    }
}
