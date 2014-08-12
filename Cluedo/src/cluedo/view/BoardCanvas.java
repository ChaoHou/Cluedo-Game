package cluedo.view;

import cluedo.model.Board;

import java.awt.*;

public class BoardCanvas extends Canvas {
    private final Board board;
    // C = collidor R = room D = door S = start J = jump
    private final String[] map = {
            "#########S####S#########",
            "RRRRRJ#CCCRRRRCCC#RRRRRR",
            "RRRRRRCCRRRRRRRRCCRRRRRR",
            "RRRRRRCCRRRRRRRRCCRRRRRR",
            "RRRRRRCCRRRRRRRRCCRRRRRR",
            "RRRRRRCCRRRRRRRRCCDRRRRR",
            "#RRRDRCCDRRRRRRDCCC#####",
            "CCCCCCCCRRRRRRRRCCCCCCCS",
            "#CCCCCCCRDRRRRDRCCCCCCC#",
            "RRRRRCCCCCCCCCCCCCRRRRRR",
            "RRRRRRRRCCCCCCCCCCRRRRRR",
            "RRRRRRRRCC#####CCCRRRRRR",
            "RRRRRRRRCC#####CCCRRRRRR",
            "RRRRRRRDCC#####CCCRRRRRR",
            "RRRRRRRRCC#####CCCCCCCC#",
            "RRRRRRRRCC#####CCCRRRRR#",
            "RRRRRRDRCC#####CCRRRRRRR",
            "#CCCCCCCCC#####CCDRRRRRR",
            "SCCCCCCCCCCCCCCCCRRRRRRR",
            "#CCCCCCCCRRRRRRCCCRRRRR#",
            "JRRRRRRCCRRRRRRCCCCCCCCS",
            "RRRRRRRCCRRRRRRCCCCCCCC#",
            "RRRRRRRCCRRRRRRCCRRRRRRJ",
            "RRRRRRRCCRRRRRRCCRRRRRRR",
            "RRRRRRRCCRRRRRRCCRRRRRRR",
            "RRRRRR#S#RRRRRR#C#RRRRRR",
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
        int cell = board.width()/24;

        for (int x = 0; x < 24;x++) {
            for (int y = 0; y < 26; y++) {
                //draw board
                if (map[y].charAt(x) == 'C' || map[y].charAt(x) == 'S') {
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
