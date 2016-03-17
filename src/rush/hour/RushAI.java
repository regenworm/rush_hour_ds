package rush.hour;

import rush.hour.BoardElements.BoardElement;
import rush.hour.BoardElements.Car;
import rush.hour.BoardElements.RedCar;

import java.util.List;
import java.util.Random;

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

    public boolean gameWon(Board board) {
        BoardElement temp;
        List<Tile> goalTiles = board.getGoalTiles();
        List<Tile> carTiles;
        int xGoal;
        int yGoal;

        for (int i = 0; i < board.boardElements.size(); i++) {
            temp = (Car) board.boardElements.get(i);
            if (temp instanceof RedCar) {
                continue;
            }
            carTiles = temp.getTiles();

            for (Tile goalTile : goalTiles) {
                xGoal = goalTile.getX();
                yGoal = goalTile.getY();

                for (Tile carTile : carTiles) {
                    if (xGoal == carTile.getX() || yGoal == carTile.getX()) {
                        return false;
                    }
                }
            }

        }

        return true;
    }

    // solve current board
    public BoardHistory solveBoard(Board board) {
        List<BoardHistory> currentNodes;
        BoardHistory won;

        // search for solution
        while (true) {

            // get all nodes of current level (current number of moves)
            currentNodes = historyBoards.getNodesOfLevel(moves);

            // for all boards get all possible moves
            for (BoardHistory currentNode : currentNodes) {
                won = getCurrentMoves(board, currentNode);
                if (won != null ) {
                    break;
                }
            }

            this.moves += 1;
        }

//        return won;
    }

    // get all moves for current board
    public BoardHistory getCurrentMoves(Board board, BoardHistory currentNode) {
        // init variables
        Board tempBoard = board.getCopy();
        BoardHistory tempNode;
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
                        System.out.println(tempBoard.toString());

                        // if move succeeds, add to history
                        tempNode = currentNode.addNode(tempBoard,i,k);

                        // if board solved return node of history
                        if (gameWon(tempBoard)) {
                            return tempNode;
                        }
                    }
                } catch (BoardElementClashException|ArrayIndexOutOfBoundsException e) {
                }

                try {
                    for (int k = 1; k < MAX_MOVES; k++) {
                        tempBoard = board.getCopy();
                        tempBoard.move((Car) tempBoard.getBoardElements().get(i), k);

                        System.out.println(tempBoard.toString());
                        // if move succeeds, add to history
                        tempNode = currentNode.addNode(tempBoard,i,k);

                        // if board solved return node of history
                        if (gameWon(tempBoard)) {
                            return tempNode;
                        }
                    }
                } catch (BoardElementClashException|ArrayIndexOutOfBoundsException e) {
                }
            }
        }

        return null;
    }

    // get all moves for current board
    public Board getCurrentMoves(Board board){//, BoardHistory currentNode) {
        // init variables
        Board tempBoard = board.getCopy();
        BoardHistory tempNode;
        int numberOfElements = tempBoard.getBoardElements().size();

//        try {
//            tempBoard.move((Car) tempBoard.getBoardElements().get(1), 1);
//        } catch (BoardElementClashException e) {
//            System.out.println("sdhfo");
//        }
//
//        return tempBoard;


        // loop over board elements
        for (int i = 0; i < numberOfElements; i++) {
            // if element is a car
            if (tempBoard.getBoardElements().get(i) instanceof Car) {
                // move element -MAX_MOVES < k < MAX_MOVES tiles
                try {
                    for (int k = -1; k > -MAX_MOVES; k--) {
                        tempBoard = board.getCopy();

                        tempBoard.move((Car) tempBoard.getBoardElements().get(i), k);
                        System.out.println(tempBoard.toString());

                        // if move succeeds, add to history
                        historyBoards.addNode(tempBoard,i,k);
//                        tempNode = currentNode.addNode(tempBoard,i,k);
//
//                        // if board solved return node of history
//                        if (gameWon(tempBoard)) {
//                            return tempNode;
//                        }
                    }
                } catch (BoardElementClashException|ArrayIndexOutOfBoundsException e) {
                }

                try {
                    for (int k = 1; k < MAX_MOVES; k++) {
                        tempBoard = board.getCopy();
                        tempBoard.move((Car) tempBoard.getBoardElements().get(i), k);

                        System.out.println(tempBoard.toString());
                        // if move succeeds, add to history
                        historyBoards.addNode(tempBoard,i,k);
//                        tempNode = currentNode.addNode(tempBoard,i,k);
//
//                        // if board solved return node of history
//                        if (gameWon(tempBoard)) {
//                            return tempNode;
//                        }
                    }
                } catch (BoardElementClashException|ArrayIndexOutOfBoundsException e) {
                }
            }
        }

        Random generator = new Random();
        int el = historyBoards.getChildren().size();
//        return historyBoards.getChildren().get(el - generator.nextInt(el)).getBoard();
        return historyBoards.getChildren().get(1).getBoard();
    }
}