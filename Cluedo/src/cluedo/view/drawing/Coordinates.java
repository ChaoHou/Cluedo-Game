package cluedo.view.drawing;

/**
 * Created by innocence on 18/08/2014.
 */
public class Coordinates {
    public final int x;
    public final int y;
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }

}
