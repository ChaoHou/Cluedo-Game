package cluedo.model;


import cluedo.controller.action.server.Move;
import cluedo.exception.IllegalRequestException;
import cluedo.view.drawing.Coordinates;

import java.io.*;
import java.util.ArrayList;

//in client mode Board is renewed each time client receives new state
//in server mode Board is just refreshed.
public class Board {
    private final int width = 20 * 24;
    private final int height = 20 * 26;
    private Card[] Suggestion;

    // C = corridor c = corridor next a door R = room D = door S = start J = jump j = jump
    public final String[] map = {
            "#########C####C#########",
            "jRRRRR#CCCRRRRCCC#RRRRRJ",
            "RRRRRRCCRRRRRRRRCCRRRRRR",
            "RRRRRRCCRRRRRRRRCCRRRRRR",
            "RRRRRRCCRRRRRRRRCCRRRRRR",
            "RRRRRRCCRRRRRRRRCCDRRRRR",
            "#RRRDRCcDRRRRRRDcCcRRRR#",
            "CCCCcCCCRRRRRRRRCCCCCCCC",
            "#CCCCCCCRDRRRRDRCCCCCCC#",
            "RRRRRCCCCcCCCCcCCCRRRRRR",
            "RRRRRRRRCCCCCCCCCcDRRRRR",
            "RRRRRRRRCC#####CCCRRRRRR",
            "RRRRRRRRCC#####CCCRRRRRR",
            "RRRRRRRDcC#####CCCRRRRDR",
            "RRRRRRRRCC#####CCCCCcCc#",
            "RRRRRRRRCC#####CCCRRDRR#",
            "RRRRRRDRCC#####CCRRRRRRR",
            "#CCCCCcCCC#####CcDRRRRRR",
            "CCCCCCCCCCCccCCCCRRRRRRR",
            "#CCCCCcCCRRDDRRCCCRRRRR#",
            "RRRRRRDCCRRRRRRCCCCCCCCC",
            "RRRRRRRCCRRRRRDcCcCCCCC#",
            "RRRRRRRCCRRRRRRCCDRRRRRR",
            "RRRRRRRCCRRRRRRCCRRRRRRR",
            "RRRRRRRCCRRRRRRCCRRRRRRR",
            "JRRRRR#C#RRRRRR#C#RRRRRj",
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

    /**
     * constructor for user-client constructor takes nothing
     */
    public Board() {
        players = new ArrayList<Player>();

        setupBoard();
    }

    /**
     * setup default board
     * weapons is not randomly placed in rooms
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
            rooms[i].setInRoom(weapons[i]);
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

    public synchronized void fromByte(byte[] input) throws IOException{
        ByteArrayInputStream bais = new ByteArrayInputStream(input);
        DataInputStream dis = new DataInputStream(bais);

        //retrieve tokens positions
        if (dis.readByte() != 0) {
//            for (Chara c : characters) {
//            	//Bug here
//                c.fromInputStream(dis);
//            }
        	//ERROR WAS HERE NOW FIXED
        	for(int i=0;i<characters.length;i++){
        		characters[i] = Chara.fromInputStream(dis);
        	}
        	
        }

        //retrieve weapon tokens positions
        if (dis.readByte() != 0) {
            for (Room r : rooms) {
                r.fromInputStream(dis,characters);
            }
        }

        int nPlayers = dis.readByte();
        if (nPlayers != 0) {
            players.clear();

            for (int i = 0; i < nPlayers; ++i) {
                players.add(Player.fromInputStream(dis,characters));
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
    public synchronized byte[] toByte() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeByte(characters.length);
        //stores tokens positions
        if (characters.length != 0) {
            for (Chara c : characters) {
                c.toOutputStream(dos);
            }
        }

        dos.writeByte(rooms.length);
        //stores rooms tokens positions
        if (rooms.length != 0) {
            for (Room r : rooms) {
                r.toOutputStream(dos);
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
    public synchronized Player getPlayer(int uid) throws IllegalRequestException{
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
    public synchronized ArrayList<Player> getPlayers() {
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
    public synchronized void movePlayer(int uid, Move.Direction direction) {
        try {
            Player p = getPlayer(uid);
            //get currenti pos
            int cXPos = p.getCharacter().getX();
            int cYPos = p.getCharacter().getY();
            System.out.println("Coords pre move: x:"+cXPos+" y:"+cYPos);
            char curC = map[cYPos].charAt(cXPos);

            //get destination x & y
            int[] des = getDireInXY(direction);
            int dXPos = p.getCharacter().getX()+des[0];
            int dYPos = p.getCharacter().getY()+des[1];
            char desC = map[dYPos].charAt(dXPos);

            //moved decrement stepsRemain()
            //moving on corridor
            if ((curC == 'C' || curC == 'c' )&& (desC == 'c' || desC == 'C')) {
                p.getCharacter().setPosition(dXPos, dYPos);
                p.decStepR();
                // go into a room
            } else if ((curC == 'c' && desC == 'D')) {
                rooms[Room.getRoomIndex(new Coordinates(dXPos, dYPos))].setInRoom(p.getCharacter());
                p.decStepR();
                // get out from a room
                //currently cannot use warp and if there is 2 doors on same side it choose left one
            } else if (p.getCharacter().isInRoom()) {
                        Coordinates c = p.getCharacter().getRoom().getC(direction);
                if (c != null) {
                    p.getCharacter().outFromRoom();
                    p.getCharacter().setPosition(c.x, c.y);
                    p.decStepR();
                }
            }
            else {return;}
            
        } catch (Exception e) {

        }
    }

    /**
     * return all rooms on the board
     * @return
     */
    public synchronized Room[] getRooms() {
        return rooms;
    }

    /**
     * return all characters on the board
     * @return
     */
    public synchronized Chara[] getCharacters() {
        return characters;
    }

    /**
     * return all weapons on the board
     * @return
     */
    public synchronized Weapon[] getWeapons() {
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

    /**
     *
     * move the character's token and weapon into a room announced
     * @param room
     * @param player
     * @param weapon
     */
     public synchronized void annoInRoom(Room room, Player player, Card.WEAPON weapon) {
        player.getCharacter().setInRoom(room);
         weapons[weapon.ordinal()].outFromRoom(room);
        weapons[weapon.ordinal()].setInRoom(room);
    }

    /**
     * return suggestion(Assumption) made by user
     * @return
     */
    public synchronized Card[] getSuggestion() {
        return Suggestion;
    }

    /**
     * set suggestion(Assumption) made by user
     * @param suggestion
     */
    public synchronized void setSuggestion(Card[] suggestion) {
        Suggestion = suggestion;
    }
}
