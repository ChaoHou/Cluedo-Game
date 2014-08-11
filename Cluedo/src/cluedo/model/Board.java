package cluedo.model;


import cluedo.exception.IllegalRequestException;

import java.util.ArrayList;

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
    private final ArrayList<Room> rooms = new ArrayList<Room>();

    /**
     * stores all characters
     */
    private final ArrayList<Character> characters = new ArrayList<Character>();

    /**
     * sotres all weapons
     */
    private final ArrayList<Weapon> weapons = new ArrayList<Weapon>();

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

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }
}
