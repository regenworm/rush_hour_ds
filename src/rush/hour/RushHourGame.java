package rush.hour;

import rush.hour.BoardElements.Car;

import java.nio.file.Path;
import java.nio.file.Paths;

public class RushHourGame {

    private Board board;

    public RushHourGame() {
        Path game = Paths.get("/home/yorick/IdeaProjects/RushHour/res/board1a.rushhour");
        board = new Board(game);
        printCurrentBoard(board);
        if (board.getBoardElements().get(1) instanceof Car) {
            try {
                board.move((Car) board.getBoardElements().get(1), 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        printCurrentBoard(board);
    }

    public static void main(String[] args) {
        RushHourGame rushHourGame = new RushHourGame();
    }

    public void printCurrentBoard(Board board) {
        board.printSerializedBoard();
    }
}
