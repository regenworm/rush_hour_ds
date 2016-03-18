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
    char id;

    BoardElement(char id, boolean moveable, List<Tile> tiles) {
        this.id = id;
        this.moveable = moveable;
        this.tiles = tiles;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public char getId() {
        return id;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public void appendTiles(List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (!this.tiles.contains(tile)) {
                this.tiles.add(tile);
            }
        }
    }

    public void removeTiles(List<Tile> tiles) {
        for (Tile tile : tiles) {
            this.tiles.remove(tile);
        }
    }

    /**
     * Get a specific tile by x and y coordinates
     * @param x x-coordinate
     * @param y y-coordinate
     * @return a tile
     */
    public Tile getTile (int x, int y) {
        for (Tile tile : tiles) {
            if (tile.getX() == x && tile.getY() == y) {
                return tile;
            }
        }
        System.err.println("Tile could not be found in empty");
        return null;
    }

    /**
     * Adds a new tile to a board element
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void addTile (int x, int y) {
        tiles.add(new Tile(x, y));
    }

    /**
     * Removes a tile from a board element
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public void removeTile (int x, int y) {
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getX() == x && tiles.get(i).getY() == y) {
                tiles.remove(i);
            }
        }
    }
}
