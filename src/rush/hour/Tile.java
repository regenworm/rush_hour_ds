package rush.hour;

public class Tile {

    private int x;
    private int y;
    private boolean occupied;

    public Tile(int x, int y, boolean occupied) {
        this.x = x;
        this.y = y;
        this.occupied = occupied;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
