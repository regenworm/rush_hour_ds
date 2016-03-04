package rush.hour;

import java.nio.file.Path;

public class Board {

    private BoardElement[] board;

    public Board() {
    }

    Board(Path path) {
        this.board = importBoard(path);
    }

    private static BoardElement[] importBoard(Path path) {
        return null;
    }
}
