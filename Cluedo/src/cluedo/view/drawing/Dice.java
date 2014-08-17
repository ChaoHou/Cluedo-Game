package cluedo.view.drawing;

import java.awt.*;

/**
 * Created by innocence on 18/08/2014.
 */
public class Dice {

    public static void drawDice(Graphics2D g, int steps) {

        g.drawRect(630,440,50,50);
        g.drawString(Integer.toString(steps),652,467);
        g.drawRect(510,450,80,20);
        g.drawString("ROLL A DIE",510,465);
    }

}
