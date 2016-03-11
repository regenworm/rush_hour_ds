package rush.hour.BoardElements;

import rush.hour.Tile;

import java.util.List;

public class Empty extends BoardElement {
    public Empty(char id, boolean moveable, List<Tile> tiles) {
        super(id, moveable, tiles);
    }
}
