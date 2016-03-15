package rush.hour.BoardElements;

import rush.hour.Tile;

import java.util.List;

public class Wall extends BoardElement {

    public Wall(char id, boolean moveable, List<Tile> tiles) {
        super(id, moveable, tiles);
    }
}
