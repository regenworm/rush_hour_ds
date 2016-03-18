package rush.hour;

/**
 * Represents tile on the board
 */
public class Tile {

    private int x;
    private int y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Equals when tiles x and y coordinates are the same
     *
     * @param o Tile object
     * @return True when match
     */
    @Override
    public boolean equals(Object o) {
        boolean equal = false;
        if (o != null && o instanceof Tile) {
            equal = (x == ((Tile) o).x && y == ((Tile) o).y);
        }
        return equal;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
