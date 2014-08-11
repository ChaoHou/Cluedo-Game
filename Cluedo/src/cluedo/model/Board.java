package cluedo.model;


import java.util.ArrayList;

//in client mode Board is refreshed each time client receives new state
public class Board {
    private final int width;
    private final int height;

    /**
     * stores solution
     **/
    private final Card solution;

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

}
