package rush.hour;

import rush.hour.BoardElements.Car;

import java.util.List;

public class RushAI {
    private static final int MAX_MOVES = 8;
    public BoardHistory historyBoards;
    private int moves = 0;


    public RushAI(Board board) {
        // historyBoards = new ArrayList<Board>();
        System.out.println("Lets AI! <(0_0<) <(0_0)> (>0_0)>");
        historyBoards = new BoardHistory(board.getCopy());
    }

    // heuristic
    private float getScore(Board board) {
        return 0;
    }


    // get all moves for current board
    public Board getCurrentMoves(Board board) {
        // init variables
        Board tempBoard = board.getCopy();
        int numberOfElements = tempBoard.getBoardElements().size();


        // loop over board elements
        for (int i = 0; i < numberOfElements; i++) {
            // if element is a car
            if (tempBoard.getBoardElements().get(i) instanceof Car) {
                // move element -MAX_MOVES < k < MAX_MOVES tiles
                try {
                    for (int k = -1; k > -MAX_MOVES; k--) {
                        tempBoard = board.getCopy();

                        tempBoard.move((Car) tempBoard.getBoardElements().get(i), k);

                        // if move succeeds, add to history
                        historyBoards.addNode(tempBoard,i,k);
                        System.out.println("Legal move!");
                    }
                } catch (BoardElementClashException|ArrayIndexOutOfBoundsException e) {
                }

                try {
                    for (int k = 1; k < MAX_MOVES; k++) {
                        tempBoard = board.getCopy();
                        tempBoard.move((Car) tempBoard.getBoardElements().get(i), k);

                        // if move succeeds, add to history
                        historyBoards.addNode(tempBoard,i,k);
                        System.out.println("Legal move!");
                    }
                } catch (BoardElementClashException|ArrayIndexOutOfBoundsException e) {
                }
            }
        }

        return tempBoard;
    }
}