package rush.hour;

import rush.hour.BoardElements.Car;

import java.util.List;

public class RushAI {
    private static final int MAX_MOVES = 3;
    public List<Board> historyBoards;
    private int moves = 0;


    public RushAI() {
        System.out.println("Lets AI! <(0_0<) <(0_0)> (>0_0)>");
    }

    // add to history
    private void addToHistory(Board board) {
        System.out.println("Added to history");
        historyBoards.add(board);
        // get String version of board
        // store in history structure
    }

    // heuristic
    private float getScore(Board board) {
        return 0;
    }

    // get all moves for current board
    public Board getCurrentMoves(Board board) {
        // init variables
        int numberOfElements = board.getBoardElements().size();
        Board tempBoard = board;

        // loop over board elements
        for (int i = 0; i < 3; i++) {
            // if element is a car
            if (tempBoard.getBoardElements().get(i) instanceof Car) {
                // move element -MAX_MOVES < k < MAX_MOVES tiles
                for (int k = -MAX_MOVES; k < MAX_MOVES; k++) {
                    try {
                        tempBoard.move((Car) tempBoard.getBoardElements().get(i), k);

                        // if move succeeds, add to history
                        addToHistory(tempBoard);
                    } catch (BoardElementClashException e) {
                        System.out.println("Illegal move");
                    }
                    board.printSerializedBoard();
                    tempBoard = board;
                }
            }
        }

        return tempBoard;
    }
}