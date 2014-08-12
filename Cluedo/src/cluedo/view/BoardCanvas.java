package cluedo.view;

import cluedo.model.Board;

import java.awt.*;

public class BoardCanvas extends Canvas {
    private final Board board;
    // A = collidor R = room D = door S = start J = jump
    private final String[] map = {
            "#########S####S#########",
            "RRRRRJ#AAARRRRAAA#RRRRRR",
            "RRRRRRAARRRRRRRRAARRRRRR",
            "RRRRRRAARRRRRRRRAARRRRRR",
            "RRRRRRAARRRRRRRRAARRRRRR",
            "RRRRRRAARRRRRRRRAADRRRRR",
            "#RRRDRAADRRRRRRDAAA#####",
            "AAAAAAAARRRRRRRRAAAAAAAS",
            "#AAAAAAARDRRRRDRAAAAAAA#",
            "RRRRRAAAAAAAAAAAAARRRRRR",
            "RRRRRRRRAAAAAAAAAARRRRRR",
            "RRRRRRRRAA#####AAARRRRRR",
            "RRRRRRRRAA#####AAARRRRRR",
            "RRRRRRRDAA#####AAARRRRRR",
            "RRRRRRRRAA#####AAAAAAAA#",
            "RRRRRRRRAA#####AAARRRRR#",
            "RRRRRRDRAA#####AARRRRRRR",
            "#AAAAAAAAA#####AADRRRRRR",
            "SAAAAAAAAAAAAAAAARRRRRRR",
            "#AAAAAAAARRRRRRAAARRRRR#",
            "JRRRRRRAARRRRRRAAAAAAAAS",
            "RRRRRRRAARRRRRRAAAAAAAA#",
            "RRRRRRRAARRRRRRAARRRRRRJ",
            "RRRRRRRAARRRRRRAARRRRRRR",
            "RRRRRRRAARRRRRRAARRRRRRR",
            "RRRRRR#S#RRRRRR#A#RRRRRR",
    };

    /**
     * constructor
     * @param board
     */
    public BoardCanvas(Board board) {
        this.board = board;

        setSize(new Dimension(board.width(),board.height()));
    }

    /**
     * paints canvas.
     * @param g
     */
    public void paint(Graphics g) {
        int width = board.width();
        int height = board.height();
        int cell = width/24;

        for (int x = 0; x < 24;x++) {
            for (int y = 0; y < 26; y++) {
                //draw board
                if (map[y].charAt(x) == 'A' || map[y].charAt(x) == 'S') {
                    g.setColor(new Color(255, 237, 0));
                    g.fillRect(x*cell, y*cell, cell, cell);
                    g.setColor(Color.BLACK);
                    g.drawRect(x*cell, y*cell, cell, cell);
                } else if (map[y].charAt(x) == 'R' || map[y].charAt(x) == 'D' || map[y].charAt(x) == 'J' ) {
                    g.setColor(new Color(58, 233, 22));
                    g.fillRect(x*cell,y*cell,cell,cell);
                } else {
                    g.setColor(new Color(0, 141, 255));
                    g.fillRect(x*cell,y*cell,cell,cell);
                }

                //draw characters


//                System.out.println(x+", "+y);
            }

        }

    }
}
