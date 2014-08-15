package cluedo.model;

public class Room {
    private final Card.ROOM name;

    public Room(Card.ROOM name) {
        this.name = name;
    }

    /**
     * get method for the name;
     * @return String
     */
    public Card.ROOM getName() {
        return name;
    }
}
