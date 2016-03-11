package rush.hour;

import java.nio.file.Path;
import java.nio.file.Paths;

public class RushHourGame {

    private Board board;

    public RushHourGame() {
        Path game = Paths.get("/home/yorick/IdeaProjects/RushHour/res/board1a.rushhour");
        board = new Board(game);
        printCurrentBoard(board);
    }

    public static void main(String[] args) {
        RushHourGame rushHourGame = new RushHourGame();
    }

    public void printCurrentBoard(Board board) {
        board.printSerializedBoard();
    }
}
