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

    public static void drawArrow(Graphics2D g,DIRECTION direction) {
        int[] xs = {-15,0,15};
        int[] ys = {30,0,30};
        g.rotate((Math.PI/2)*direction.ordinal());
        g.fillPolygon(xs,ys,3);
        g.rotate(-(Math.PI/2)*direction.ordinal());
    }

}
