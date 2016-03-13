package rush.hour.BoardElements;


import rush.hour.Tile;

import java.util.List;

public class Car extends BoardElement {

    private Car.Orientation orientation;

    public Car(char id, boolean moveable, List<Tile> tiles, Orientation orientation) {
        super(id, moveable, tiles);
        this.orientation = orientation;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public enum Orientation { VERTICAL, HORIZONTAL, UNDEFINED }
}
