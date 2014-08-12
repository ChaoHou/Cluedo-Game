package cluedo.model;

public class Card {
    private final TYPE type;
    private final String name;

    public enum TYPE {
        ROOM,
        WEAPON,
        CHARCTER,
    }

    public enum ROOM {
        KITCHEN,
        BALL_ROOM,
        CONSERVATORY,
        DINING_ROOM,
        BILLIARD_ROOM,
        LIBRARY,
        LOUNGE,
        HALL,
        STUDY,
    }

    public enum WEAPON {
        CANDLESTICK,
        DAGGER,
        LEAD_PIPE,
        REVOLVER,
        ROPE,
        SPANNER,
    }

    public enum CHARACTER {
        SCARLETT,
        MUSTARD,
        WHITE,
        GREEN,
        PEACOCK,
        PLUM,
    }

    public Card(TYPE type, String name) {
        this.type = type;
        this.name = name;
    }
}
