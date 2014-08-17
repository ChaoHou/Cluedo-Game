package cluedo.view.drawing;

import java.awt.*;

/**
 * Created by innocence on 18/08/2014.
 */
public class Arrow {
    public enum DIRECTION{
        UP,
        RIGHT,
        DOWN,
        LEFT,
    }

    public static void drawArrow(Graphics2D g) {
        g.translate(595,10);
        Arrow.drawSingleArrow(g,Arrow.DIRECTION.UP);
        g.translate(0,100);
        Arrow.drawSingleArrow(g,Arrow.DIRECTION.DOWN);
        g.translate(50,-50);
        Arrow.drawSingleArrow(g,Arrow.DIRECTION.RIGHT);
        g.translate(-100,0);
        Arrow.drawSingleArrow(g,Arrow.DIRECTION.LEFT);
        g.translate(-545,-60);
    }

    private static void drawSingleArrow(Graphics2D g, DIRECTION direction) {
        int[] xs = {-15,0,15};
        int[] ys = {30,0,30};
        g.rotate((Math.PI/2)*direction.ordinal());
        g.setColor(Color.WHITE);
        g.fillRect(-15,0,30,30);
        g.setColor(Color.BLUE);
        g.fillPolygon(xs,ys,3);
        g.rotate(-(Math.PI/2)*direction.ordinal());

    }
}
