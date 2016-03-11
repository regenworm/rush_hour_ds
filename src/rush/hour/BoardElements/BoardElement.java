package rush.hour.BoardElements;

import rush.hour.Tile;

import java.util.List;

public class BoardElement {

    public static final char border = '#';
    public static final char goal = '!';
    public static final char empty = '.';
    public static final char redcar = '@';

    boolean moveable;
    List<Tile> tiles;

    BoardElement(char id, boolean moveable, List<Tile> tiles) {

    }

    public boolean isMoveable() {
        return moveable;
    }
}
