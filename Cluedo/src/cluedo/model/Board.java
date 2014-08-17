package cluedo.model;


import cluedo.controller.action.server.Move;
import cluedo.exception.IllegalRequestException;

import java.io.*;
import java.util.ArrayList;

//in client mode Board is renewed each time client receives new state
//in server mode Board is just refreshed.
public class Board {
    private final int width = 20 * 24;
    private final int height = 20 * 26;

    // C = collidor R = room D = door S = start J = jump
    public final String[] map = {
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
     * stores solution
     */
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
     * stores all weapons
     */
    private final Weapon[] weapons = new Weapon[6];

    /**
     * stores all players
     */
    private final ArrayList<Player> players;

    /**
     * constructor
     * initialise all rooms, characters and weapons on default position.
     *
     * @param players
     */
    public Board(ArrayList<Player> players) {
        this.players = players;

        setupBoard();
    }

    public Board() {
        players = new ArrayList<Player>();

        setupBoard();
    }

    /**
     * setup default board
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
     * 1 = Weapon
     * 2 = Room
     * throws exception when an index stores different type of card
     *
     * @param solution
     */
    public void setSolution(Card[] solution) {
        this.solution = solution;
    }

    /**
     * returns solution (to compare users accusation)
     *
     * @return Card[]
     */
    public Card[] getSolution() {
        return solution;
    }

    /**
     * return the game board's height
     *
     * @return int
     */
    public int height() {
        return height;
    }

    /**
     * return the game board's width
     *
     * @return int
     */
    public int width() {
        return width;
    }

    /**
     * decode current game state and apply it
     *
     * @param input
     */

    public void fromByte(byte[] input) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        DataInputStream dis = new DataInputStream(bais);

        //retrieve tokens positions
        if (dis.readByte() != 0) {
            for (Chara c : characters) {
                c.fromInputStream(dis);
            }
        }

        //retrieve weapon tokens positions
        if (dis.readByte() != 0) {
            for (Weapon w : weapons) {
                w.fromInputStream(dis);
            }
        }

        int nPlayers = dis.readByte();
        if (nPlayers != 0) {
            players.clear();

            for (int i = 0; i < nPlayers; ++i) {
                players.add(Player.fromInputStream(dis));
            }
        }

    }

    /**
     * encode current game state into Bitwise
     * REQUIRES:
     * Token positions
     * Weapon positions
     * Player status & uid & token's name & cards & dice
     *
     * @return int
     */
    public byte[] toByte() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeByte(characters.length);
        //stores tokens positions
        if (characters.length != 0) {
            for (Chara c : characters) {
                c.toOutputStream(dos);
            }
        }

        dos.writeByte(characters.length);
        //stores weapon tokens positions
        if (weapons.length != 0) {
            for (Weapon w : weapons) {
                w.toOutputStream(dos);
            }
        }

        //stores number of players
        dos.writeByte(players.size());

        //stores player info
        if (!players.isEmpty()) {
            for (Player p : players) {
                p.toOutputStream(dos);
            }
        }

        dos.flush();

        return baos.toByteArray();
    }

    /**
     * return a player corresponding to the given uid
     *
     * @param uid
     * @return
     */
    public Player getPlayer(int uid) throws IllegalRequestException{
        if (!players.isEmpty())
        for (Player p: players) {
            if (p.getUid() == uid) {
                return p;
            }
        }
        throw new IllegalRequestException("uid not exists");
    }

    /**
     * return players in the game!
     * @return
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * if it's possible move a player's token to given direction
     * subtract stepsRemain stored in a player class
     * other wise do nothing
     *if a player is moving into a room, take a unused room inner coordinate to the player
     *if player is moving out of a room, take the door which meet the player's direction
     * @param uid
     * @param direction
     */
    public void movePlayer(int uid, Move.Direction direction) {
        try {
            Player p = getPlayer(uid);

            //get destination x & y
            int[] des = getDireInXY(direction);
            int dXPos = p.getCharacter().getX()+des[0];
            int dYPos = p.getCharacter().getY()+des[1];
            char desC = map[dYPos].charAt(dXPos);

            //moved decrement stepsRemain()
            if (desC == 'C' || desC == 'D') {
                p.getCharacter().setPosition(dXPos,dYPos);
                p.decStepR();
            }
            else {return;}
        } catch (Exception e) {

        }
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

    private int[] getDireInXY(Move.Direction dire) {
        int[] temp = new int[2];
        switch (dire.ordinal()) {
            case 0:
                temp[0] = 0;
                temp[1] = -1;
                break;
            case 1:
                temp[0] = 0;
                temp[1] = 1;
                break;
            case 2:
                temp[0] = -1;
                temp[1] = 0;
                break;
            default:
                temp[0] = 1;
                temp[1] = 0;
                break;
        }
        return temp;
    }


}
