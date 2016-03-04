package rush.hour;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<Tile> board;

    public Board() {
    }

    Board(Path path) {
        this.board = importBoard(path);
    }

    private List<Tile> importBoard(Path path) {
        List<BoardElement> boardElements = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int yCount, xCount = 0;
            while((line = reader.readLine()) != null) {
                xCount = 0;
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case '@':
                            if (boardElements.con)
                            boardElements.add(new RedCar({xCount, yCount}, ));
                            break;
                        case '#':
                            break;
                        case '!':
                            break;
                        default:
                            if (boardElements.contains(Redcar))
                            break;
                    }
                    xCount++;
                }
                yCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Tile getGoal() {
        return tile;
    }
}
