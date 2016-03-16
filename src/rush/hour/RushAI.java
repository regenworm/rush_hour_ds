package rush.hour;

import rush.hour.BoardElements.Car;

import java.util.List;

public class RushAI {
    private static final int MAX_MOVES = 3;
    public List<Board> historyBoards;
    private int moves = 0;


    public RushAI() {
        // historyBoards = new ArrayList<Board>();
        System.out.println("Lets AI! <(0_0<) <(0_0)> (>0_0)>");
    }

    // add to history
    private void addToHistory(Board board) {
        System.out.println("Added to history");
        // historyBoards.add(board);
        // get String version of board
        // store in history structure
    }

    // heuristic
    private float getScore(Board board) {
        return 0;
    }

    private Board cloneBoard (Board board) {
        return board;

    }


    // get all moves for current board
    public Board getCurrentMoves(Board board) {
        // init variables
        int numberOfElements = board.getBoardElements().size();
        Board tempBoard = cloneBoard(board);


        // loop over board elements
        for (int i = 0; i < 2; i++) {
            // if element is a car
            if (tempBoard.getBoardElements().get(i) instanceof Car) {
                // move element -MAX_MOVES < k < MAX_MOVES tiles
                try {
                    for (int k = -1; k > -MAX_MOVES; k--) {
                        tempBoard.move((Car) tempBoard.getBoardElements().get(i), k);

                        // if move succeeds, add to history
                        addToHistory(tempBoard);
                        System.out.println("Legal move!");
                        tempBoard.printSerializedBoard();
                        System.out.println("\n\n\n");

                        // reset board
                        tempBoard = cloneBoard(board);
                    }
                } catch (BoardElementClashException|ArrayIndexOutOfBoundsException e) {
                    System.out.println("Illegal move\n");
                    tempBoard = cloneBoard(board);
                }

                // reset board
                tempBoard = cloneBoard(board);
                try {
                    for (int k = 1; k < MAX_MOVES; k++) {
                        tempBoard.move((Car) tempBoard.getBoardElements().get(i), k);

                        // if move succeeds, add to history
                        addToHistory(tempBoard);
                        System.out.println("Legal move!");
                        tempBoard.printSerializedBoard();
                        board.printSerializedBoard();
                        System.out.println("\n\n\n");

                        // reset board
                        tempBoard = cloneBoard(board);
                    }
                } catch (BoardElementClashException|ArrayIndexOutOfBoundsException e) {
                    System.out.println("Illegal move\n");
                    tempBoard = cloneBoard(board);
                }
            }
        }

        return board;
    }
}