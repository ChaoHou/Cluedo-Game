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
    private final Room[] rooms = new Room[9];

    /**
     * stores all characters
     */
    private final Chara[] characters = new Chara[6];

    /**
     * sotres all weapons
     */
    private final Weapon[] weapons = new Weapon[6];

    /**
     * constructor
     * initialise all rooms, characters and weapons on default position.
     * @param width
     * @param height
     */
    public Board(int width, int height) {
        this.width = width;
        this.height = height;

        setupBoard();
    }

    /**
     * setup defalut board
     */
    private void setupBoard() {
        Card.ROOM[] valuesR = Card.ROOM.values();
        Card.WEAPON[] valuesW = Card.WEAPON.values();
        Card.CHARACTER[] valuesC = Card.CHARACTER.values();

        for (int i = 0; i < valuesR.length; i++) {
            rooms[i] = new Room(valuesR[i]);
        }
        for (int i = 0; i < valuesW.length; i++) {
            weapons[i] = new Weapon(valuesW[i]);
        }
        for (int i = 0; i < valuesW.length; i++) {
            characters[i] = new Chara(valuesC[i]);
        }
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

    public Room[] getRooms() {
        return rooms;
    }

    public Chara[] getCharacters() {
        return characters;
    }

    public Weapon[] getWeapons() {
        return weapons;
    }
}
