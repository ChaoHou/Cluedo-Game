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
                    g2.fillRect(x * cell, y * cell, cell, cell);
                    g2.setColor(Color.BLACK);
                    g2.drawRect(x * cell, y * cell, cell, cell);
                } else if (board.map[y].charAt(x) == 'c') {
                    drawADoor(g2,x,y,cell);
                } else if (board.map[y].charAt(x) == 'R') {
                    g2.setColor(new Color(58, 233, 22));
                    g2.fillRect(x * cell + 1, y * cell + 1, cell, cell);
                } else if (board.map[y].charAt(x) == 'j') {
                    g2.setColor(new Color(142, 139, 146));
                    g2.fillRect(x * cell + 1, y * cell + 1, cell, cell);
                } else if (board.map[y].charAt(x) == 'J') {
                    g2.setColor(new Color(48, 48, 48));
                    g2.fillRect(x * cell + 1, y * cell + 1, cell, cell);
                } else if (board.map[y].charAt(x) == 'D') {
                    g2.setColor(new Color(58, 233, 22));
                    g2.fillRect(x * cell+1, y * cell+1, cell, cell);
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

    private void drawADoor(Graphics2D g2, int x, int y, int cell) {
        g2.setColor(new Color(255, 237, 0));
        g2.fillRect(x*cell+1, y*cell+1, cell, cell);

        g2.setColor(Color.BLACK);
        if (board.map[y-1].charAt(x) != 'D') {
            g2.drawLine(x*cell, y*cell, x*cell+cell, y*cell);
        }
        if (board.map[y+1].charAt(x) != 'D') {
            g2.drawLine(x*cell, y*cell+cell, x*cell+cell, y*cell+cell);
        }
        if (board.map[y].charAt(x-1) != 'D') {
            g2.drawLine(x*cell, y*cell, x*cell, y*cell+cell);
        }
        if (board.map[y].charAt(x+1) != 'D') {
            g2.drawLine(x*cell+cell, y*cell, x*cell+cell, y*cell+cell);
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

    /**
     *
     * @return an array of String
     * 0 = Dice, Card or Move
     * 1 = number of enum index
     * 2 = Card's name
     */
    public String[] defineClick(int x, int y) {
        String[] temp = new String[3];
        try {
//            Player p = board.getPlayer(uid);
            // creates imitaition player for test purpose
            Player p = new Player(uid);
            p.getCards().add(new Card(Card.TYPE.CHARCTER, "SCARLETT"));
            p.getCards().add(new Card(Card.TYPE.CHARCTER, "WHITE"));
            p.getCards().add(new Card(Card.TYPE.CHARCTER, "GREEN"));
            //ends
            if (x >= 510 && x <= 590 && y >= 450 && y <= 470) {
                temp[0] = "Dice";
            }
            if (x >= 500 && x <= 683 && y >= 140 && y <= 383) {
                int index = getCardIndex(x - 500, y - 140);
                if (index >= p.getCards().size()) {
                    return temp;}
                    temp[0] = "Card";
                    temp[1] = Integer.toString(p.getCards().get(index).getType().ordinal());
                    temp[2] = p.getCards().get(index).getName();
            }
            if (x >= 545 && x <= 645 && y >= 10 && y <= 110) {
                int direction = getArrowDirection(x-545,y-10);
                if (direction == -1) {return temp;}
                temp[0] = "Move";
                temp[1] = Integer.toString(direction);
            }
            if (x >= 560 && y >= 390 && x <= 620 && y <= 410) {
                temp[0] = "Pass";
            }
        } catch (Exception e) {

        }
        return temp;
    }

    private int getArrowDirection(int x, int y) {
        int temp = -1;
        if (x >= 35 && x <= 65 && y >= 0 && y <= 40) {return 0;}
        if (x >= 35 && x <= 65 && y >= 70 && y <= 100) {return 1;}
        if (x >= 0 && x <= 30 && y >= 35 && y <= 65) {return 2;}
        if (x >= 70 && x <= 100 && y >= 35 && y <= 65) {return 3;}
        return temp;
    }

    private int getCardIndex(int x, int y) {
        int temp = 0;
        if (x > 122) {temp = 2;}
        else if (x > 61) {temp = 1;}
        if (y > 162) {temp += 6;}
        else if (y > 81) {temp += 3;}
        return temp;
    }
}
