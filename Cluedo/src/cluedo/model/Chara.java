package cluedo.model;

public class Chara {
    private final Card.CHARACTER name;
    private int xCoordinate;
    private int yCoordinate;

    public Chara(Card.CHARACTER name) {
        this.name = name;

        setStartPos();
    }

    /**
     * get character's name
     * @return Card.CHARACTER
     */
    public Card.CHARACTER getName() {
        return name;

    }

    /**
     * get X coordinate of the character 0..24
     * @return int
     */
    public int getX() {
        return xCoordinate;
    }

    /**
     * get Y coorinate of character 0..26
     * @return int
     */
    public int getY() {
        return yCoordinate;
    }

    /**
     * set the character into certain start position depends on character
     */
    private void setStartPos() {
        switch(name.ordinal()){
            case 0:
                xCoordinate = 7;
                yCoordinate = 25;
                break;
            case 1:
                xCoordinate = 0;
                yCoordinate = 18;
                break;
            case 2:
                xCoordinate = 9;
                yCoordinate = 0;
                break;
            case 3:
                xCoordinate = 14;
                yCoordinate = 0;
                break;
            case 4:
                xCoordinate = 23;
                yCoordinate = 7;
                break;
            case 5:
                xCoordinate = 23;
                yCoordinate = 20;
                break;
        }

    }
}
