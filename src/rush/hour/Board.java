package rush.hour;

import rush.hour.BoardElements.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    List<BoardElement> boardElements;
    int[] boardSize;
    char[][] board;

    Board(Path path) {
        this.board = readInBoardFromFile(path);
        this.boardElements = deSerializeBoard();
    }

    Board (char[][] serializedBoard, int[] boardSize){
        this.board = serializedBoard;
        this.boardSize = boardSize;
        this.boardElements = deSerializeBoard();
    }

    public Board getCopy() {
        return new Board(serializeBoard(), boardSize);
    }

    /**
     * Getter function for the board size
     * @return An array where [0] is the x count, and [1] is the y count
     */
    public int[] getBoardSize() {
        return boardSize;
    }

    /**
     * Getter function for the amount of columns in a board
     * @return Column count
     */
    public int getBoardColumnCount() {
        return boardSize[0];
    }

    /**
     * Getter function for the amount of rows in a board.
     * @return Row count
     */
    public int getBoardRowCount() {
        return boardSize[1];
    }

    /**
     * Getter function for the list of board elements
     * @return boardElements
     */
    public List<BoardElement> getBoardElements() {
        return boardElements;
    }

    /**
     * Gets the goal tiles of the red car. These are the tiles in front of the Goal object tile. WARNING: only works for
     * goals placed in the right or bottom wall.
     * @return A list of tiles where the RedCar should be placed.
     */
    public List<Tile> getGoalTiles() {
        // Get the coorindates from the Goal Tile
        Goal goal = (Goal) getBoardElement(BoardElement.goal);
        List<Tile> tiles = goal.getTiles();
        int xGoal = tiles.get(0).getX();
        int yGoal = tiles.get(0).getY();

        // RedCar only for length of the RedCar
        RedCar redCar = (RedCar) getBoardElement(BoardElement.redcar);
        int redCarLength = redCar.getTiles().size();

        List<Tile> goalTiles = new ArrayList<>();
        for (int i=0; i<redCarLength; i++) {
            if (redCar.getOrientation() == Car.Orientation.HORIZONTAL) {
                goalTiles.add(new Tile(xGoal - i, yGoal));
            } else {
                goalTiles.add(new Tile(xGoal, yGoal - i));
            }
        }
        return goalTiles;
    }

    /**
     * Gets a board element for the list of board elements by id
     * @param id the id of the board element
     * @return board element when found, otherwise returns null
     */
    public BoardElement getBoardElement(char id) {
        for (BoardElement boardElement : boardElements) {
            if (boardElement.getId() == id) {
                return boardElement;
            }
        }
        return null;
    }

    /**
     * Moves a car with a certain amount of tiles.
     * @param car The car object to move
     * @param moveAmount The amount of tiles to move
     * @throws BoardElementClashException When a car is not able to move to a tile location, because the tile is
     * already occupied
     */
    public void move(Car car, int moveAmount) throws BoardElementClashException {
        Car.Orientation orientation = car.getOrientation();

        List<Tile> carTiles = car.getTiles();

        List<Tile> newCarTiles = new ArrayList<>();
        List<Tile> newEmptyTiles = new ArrayList<>();

        Empty empty = (Empty) getBoardElement('.');

        char[][] board = serializeBoard();

        for (Tile carTile : carTiles) {
            if (orientation == Car.Orientation.VERTICAL) {
                int changedCoor = carTile.getY() + moveAmount;
                if (board[carTile.getX()][changedCoor] == '.' || board[carTile.getX()][changedCoor] == car.getId()) {
                    newEmptyTiles.add(new Tile(carTile.getX(), carTile.getY()));
                    newCarTiles.add(new Tile(carTile.getX(), changedCoor));
                } else {
                    throw new BoardElementClashException();
                }
            }
            if (orientation == Car.Orientation.HORIZONTAL) {
                int changedCoor = carTile.getX() + moveAmount;
                if (board[changedCoor][carTile.getY()] == '.' || board[changedCoor][carTile.getY()] == car.getId()) {
                    newEmptyTiles.add(new Tile(carTile.getX(), carTile.getY()));
                    newCarTiles.add(new Tile(changedCoor, carTile.getY()));
                } else {
                    throw new BoardElementClashException();
                }
            }
        }

        // Intersection of lists
        newEmptyTiles.removeIf(newCarTiles::contains);

        car.removeTiles(newEmptyTiles);
        empty.removeTiles(newCarTiles);

        car.appendTiles(newCarTiles);
        empty.appendTiles(newEmptyTiles);
    }

    /**
     * Converts a board to a character array. A character is the id of a board element.
     * @return array of board id characters
     */
    public char[][] serializeBoard () {
        char[][] serializedBoard = new char[boardSize[0]][boardSize[1]];
        for (BoardElement boardElement : boardElements) {
            char id = boardElement.getId();
            for (Tile tile : boardElement.getTiles()) {
                serializedBoard[tile.getX()][tile.getY()] = id;
            }
        }
        return serializedBoard;
    }

    /**
     * Converts a board represented as a character array to a list of BoardElements.
     * @return list of board elements
     */
    public List<BoardElement> deSerializeBoard() {
        List<BoardElement> boardElements = new ArrayList<>();
        HashMap<Character, List<Tile>> readInCharacters = new HashMap<>();

        for (int yCount = 0; yCount < getBoardRowCount(); yCount++) {
            for (int xCount = 0; xCount < getBoardColumnCount(); xCount++) {
                if (readInCharacters.containsKey(board[xCount][yCount])) {
                    List<Tile> tiles = readInCharacters.get(board[xCount][yCount]);
                    if (board[xCount][yCount] == '.') {
                        tiles.add(new Tile(xCount, yCount));
                    } else {
                        tiles.add(new Tile(xCount, yCount));
                    }
                    readInCharacters.put(board[xCount][yCount], tiles);
                } else {
                    ArrayList<Tile> tiles = new ArrayList<>();
                    if (board[xCount][yCount] == '.') {
                        tiles.add(new Tile(xCount, yCount));
                    } else {
                        tiles.add(new Tile(xCount, yCount));
                    }
                    readInCharacters.put(board[xCount][yCount], tiles);
                }
            }
        }

        Car.Orientation orientation;
        for (Map.Entry<Character, List<Tile>> entry : readInCharacters.entrySet()) {
            switch (entry.getKey()) {
                case BoardElement.border:
                    boardElements.add(new Wall(entry.getKey(), false, entry.getValue()));
                    break;
                case BoardElement.empty:
                    boardElements.add(new Empty(entry.getKey(), false, entry.getValue()));
                    break;
                case BoardElement.goal:
                    boardElements.add(new Goal(entry.getKey(), false, entry.getValue()));
                    break;
                case BoardElement.redcar:
                    orientation = determineOrientation(entry.getValue());
                    boardElements.add(new RedCar(entry.getKey(), true, entry.getValue(), orientation));
                    break;
                default:
                    orientation = determineOrientation(entry.getValue());
                    boardElements.add(new Car(entry.getKey(), true, entry.getValue(), orientation));
                    break;
            }
        }

        return boardElements;
    }

    /**
     * Reads in a board from a text file.
     * @param path The path to the board file
     * @return A hasmap containing the character used in the text file as key and a list of occupied tiles by the
     * character as value.
     */
    private char[][] readInBoardFromFile(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            boardSize = new int[2];
            boardSize[0] = Integer.parseInt(reader.readLine());
            boardSize[1] = Integer.parseInt(reader.readLine());

            char[][] board = new char[boardSize[0]][boardSize[1]];

            String line;
            int yCount = 0;
            int xCount = 0;
            while((line = reader.readLine()) != null) {
                xCount = 0;
                for (char c : line.toCharArray()) {
                    board[xCount][yCount] = c;
                    xCount++;
                }
                yCount++;
            }
            return board;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Determines the orientation of a board element by comparing the x-axis from two tiles.
     * @param tiles A list of tiles
     * @return The enum value of the orientation
     */
    private Car.Orientation determineOrientation(List<Tile> tiles) {
        try {
            int x1 = tiles.get(0).getX();
            int x2 = tiles.get(1).getX();
            if (x1 > x2 || x2 > x1) {
                return Car.Orientation.HORIZONTAL;
            } else {
                return Car.Orientation.VERTICAL;
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            System.err.println("Orientation could not be determined because element has only one tile.");
        }
        return Car.Orientation.UNDEFINED;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < getBoardRowCount(); i++)
        {
            for(int j = 0; j < getBoardColumnCount(); j++)
            {
                builder.append(board[j][i]);
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
