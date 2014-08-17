package cluedo.view.drawing;

import cluedo.model.Card;
import cluedo.model.Player;

import java.awt.*;

/**
 * Created by innocence on 18/08/2014.
 */
public class Hand {
    public static void drawHands(Graphics2D g, Player p) {
        int j = 0;
        int s = p.getCards().size();
        g.translate(500,140);
        for (int i = 0; i < s; ++i) {
            if (i % 3 == 0 && i != 0) {
                g.translate(-61*3,81);
                ++j;
            }
            drawSingleHand(g, p.getCards().get(i));
            g.translate(61,0);
        }

        int ty;
        if (s%3 == 0){ty = 3;}
        else {ty = s%3;}
        g.translate(-(500+61*ty),-(140+81*j));

        g.fillRect(561,390,60,20);
        g.setColor(Color.BLACK);
        g.drawString("PASS",571,405);
        g.setColor(new Color(255,0,255));

    }

    private static void drawSingleHand(Graphics2D g, Card c) {
        g.fillRect(0,0,60,80);
        g.setColor(Color.BLACK);
        g.drawString(c.getName(),0,30);
        g.setColor(new Color(255,0,255));
    }
}
