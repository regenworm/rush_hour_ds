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

    private List<Tile> board;

    public Board() {
    }

    Board(Path path) {

    }

    /**
     * Initializes the board from a hashmap produced by {@link #readInBoard(Path path) readInBoard}
     * @param readInCharacters The hashmap of characters and lists of tiles
     * @return A list of initialized board elements
     */
    private List<BoardElement> initializeBoard(HashMap<Character, List<Tile>> readInCharacters) {
        List<BoardElement> boardElements = new ArrayList<>();
        Car.Orientation orientation;
        for (Map.Entry<Character, List<Tile>> entry : readInCharacters.entrySet()) {
            switch (entry.getKey()) {
                case BoardElement.border:
                    boardElements.add(new Border(entry.getKey(), false));
                    break;
                case BoardElement.empty:
                    boardElements.add(new Empty(entry.getKey(), false));
                    break;
                case BoardElement.goal:
                    boardElements.add(new Goal(entry.getKey(), false));
                    break;
                case BoardElement.redcar:
                    orientation = determineOrientation(entry.getValue());
                    boardElements.add(new RedCar(entry.getKey(), true, orientation));
                    break;
                default:
                    orientation = determineOrientation(entry.getValue());
                    boardElements.add(new Car(entry.getKey(), true, orientation));
                    break;
            }
        }
        return boardElements;
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

    /**
     * Reads in a board from a text file.
     * @param path The path to the board file
     * @return A hasmap containing the character used in the text file as key and a list of occupied tiles by the
     * character as value.
     */
    private HashMap<Character, List<Tile>> readInBoard(Path path) {
        HashMap<Character, List<Tile>> readInCharacters = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int yCount = 0;
            int xCount = 0;
            while((line = reader.readLine()) != null) {
                xCount = 0;
                for (char c : line.toCharArray()) {
                        if (readInCharacters.containsKey(c)) {
                            List<Tile> tiles = readInCharacters.get(c);
                            if (c == '.') {
                                tiles.add(new Tile(xCount, yCount, false));
                            } else {
                                tiles.add(new Tile(xCount, yCount, true));
                            }
                            readInCharacters.put(c, tiles);
                        } else {
                            ArrayList<Tile> tiles = new ArrayList<>();
                            if (c == '.') {
                                tiles.add(new Tile(xCount, yCount, false));
                            } else {
                                tiles.add(new Tile(xCount, yCount, true));
                            }
                            readInCharacters.put(c, tiles);
                        }
                    xCount++;
                }
                yCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readInCharacters;
    }
}
