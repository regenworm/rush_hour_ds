package rush.hour.BoardElements;


import rush.hour.Tile;

import java.util.List;

public class Car extends BoardElement {

    private Car.Orientation orientation;
    private List<Tile> tiles;

    public Car(char id, boolean moveable, List<Tile> tiles, Orientation orientation) {
        super(id, moveable, tiles);
        this.orientation = orientation;
    }

    public void move(int moveAmount) {
        for (Tile tile: tiles) {
            if (orientation == Orientation.VERTICAL) {
                tile.setX(tile.getX() + moveAmount);
            }
            if (orientation == Orientation.HORIZONTAL) {
                tile.setY(tile.getY() + moveAmount);
            }
        }
    }

    public enum Orientation { VERTICAL, HORIZONTAL, UNDEFINED }
}
