package cluedo.model;


import cluedo.exception.IllegalRequestException;

//in client mode Board is renewed each time client receives new state
//in server mode Board is just refreshed.
public class Board {
    private final int width;
    private final int height;

    /**
     * stores solution
     **/
    private Card[] solution;

    /**
     * stores all rooms
     */
    private final Room[] rooms = {
            new Room("KITCHEN"),
            new Room("BALL ROOM"),
            new Room("CONSERVATORY"),
            new Room("DINING ROOM"),
            new Room("BILLIARD ROOM"),
            new Room("LIBRARY"),
            new Room("LOUNGE"),
            new Room("HALL"),
            new Room("STUDY")
    };

    /**
     * stores all characters
     */
    private final Chara[] characters = {
            new Chara("SCARLETT"),
            new Chara("MUSTARD"),
            new Chara("WHITE"),
            new Chara("GREEN"),
            new Chara("PEACOCK"),
            new Chara("PLUM"),
    };

    /**
     * sotres all weapons
     */
    private final Weapon[] weapons = {
            new Weapon("CANDLESTICK"),
            new Weapon("DAGGER"),
            new Weapon("LEAD_PIPE"),
            new Weapon("REVOLVER"),
            new Weapon("ROPE"),
            new Weapon("SPANNER"),
    };

    /**
     * constructor
     * initialise all rooms, characters and weapons on default position.
     * @param width
     * @param height
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;

    }

    /**
     * solution is an array stores Cards
     * each index stores certain cards.
     * 0 = Character
     * 1 = Room
     * 2 = Weapon
     * throws exception when an index stores different type of card
     * @param solution
     */
    public void setSolution(Card[] solution) throws IllegalRequestException { //this Exception needs to be original Exception
        this.solution = solution;
    }

    /**
     * returns solution (to compare users accusation)
     * @return Card[]
     */
    public Card[] getSolution() {
        return solution;
    }

    /**
     * return the game board's height
     * @return int
     */
    public int height() {
        return height;
    }

    /**
     * return the game board's width
     * @return int
     */
    public int width() {
        return width;
    }

    /**
     * decode current game state and apply it
     * @param input
     */

    public void fromBit(byte[] input) {

    }

    /**
     * encode current game state into Bitwise
     * @return int
     */
    public byte[] toBit() {
        return null;
    }
}
