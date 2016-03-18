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
//        historyBoards = new BoardHistory(board.getCopy());
    }

    // heuristic
    private float getScore(Board board) {

        return 0;
    }

    public boolean gameWon(Board board) {
        List<Tile> goalTiles = board.getGoalTiles();

        for (BoardElement boardElement : board.getBoardElements()) {
            if (boardElement instanceof RedCar) {
                List<Tile> redCarTiles = boardElement.getTiles();
                return goalTiles.containsAll(redCarTiles) && redCarTiles.containsAll(goalTiles);
            }
        }
        return false;
    }

    // solve current board
    public BoardHistory solveBoard(Board board) {
        List<BoardHistory> currentNodes;
        BoardHistory won;
        historyBoards = new BoardHistory(board.getCopy());
        won = getCurrentMoves(board, historyBoards);



        // search for solution
        while (this.moves < 1) {
            System.out.println(this.moves);
            // get all nodes of current level (current number of moves)
            currentNodes = historyBoards.getNodesOfLevel(moves);
//            System.out.println(currentNodes.size());

            // for all boards get all possible moves
            for (BoardHistory currentNode : currentNodes) {
//                System.out.println("boardsss");
                won = getCurrentMoves(board, currentNode);
                if (gameWon(won.getBoard()) ) {
                    break;
                }

            }

            this.moves += 1;
        }

        return null;
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
//                        System.out.println(tempBoard.toString());

                        // if move succeeds, add to history
                        tempNode = currentNode.addNode(tempBoard,i,k);
//                        System.out.println(tempNode.getBoard().toString());

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

//                        System.out.println(tempBoard.toString());
                        // if move succeeds, add to history
                        tempNode = currentNode.addNode(tempBoard,i,k);
//                        System.out.println(tempNode.getBoard().toString());

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
    public Board getCurrentMoves(Board board){
        Board tempBoard = board.getCopy();
        BoardHistory tempNode;
        int numberOfElements = tempBoard.getBoardElements().size();

        try {
            RedCar redCar = (RedCar) board.getBoardElement(BoardElement.redcar);
            board.move(redCar,1);
        } catch (BoardElementClashException e) {
            System.out.println("sdhfo");
        }

        if (gameWon(board)) {
            System.out.println("solved");
        }

        return board;


//        // loop over board elements
//        for (int i = 0; i < numberOfElements; i++) {
//            // if element is a car
//            if (tempBoard.getBoardElements().get(i) instanceof Car) {
//                // move element -MAX_MOVES < k < MAX_MOVES tiles
//                try {
//                    for (int k = -1; k > -MAX_MOVES; k--) {
//                        tempBoard = board.getCopy();
//
//                        tempBoard.move((Car) tempBoard.getBoardElements().get(i), k);
//                        System.out.println(tempBoard.toString());
//
//                        // if move succeeds, add to history
//                        historyBoards.addNode(tempBoard,i,k);
////                        tempNode = currentNode.addNode(tempBoard,i,k);
////
////                        // if board solved return node of history
////                        if (gameWon(tempBoard)) {
////                            return tempNode;
////                        }
//                    }
//                } catch (BoardElementClashException|ArrayIndexOutOfBoundsException e) {
//                }
//
//                try {
//                    for (int k = 1; k < MAX_MOVES; k++) {
//                        tempBoard = board.getCopy();
//                        tempBoard.move((Car) tempBoard.getBoardElements().get(i), k);
//
//                        System.out.println(tempBoard.toString());
//                        // if move succeeds, add to history
//                        historyBoards.addNode(tempBoard,i,k);
////                        tempNode = currentNode.addNode(tempBoard,i,k);
////
////                        // if board solved return node of history
////                        if (gameWon(tempBoard)) {
////                            return tempNode;
////                        }
//                    }
//                } catch (BoardElementClashException|ArrayIndexOutOfBoundsException e) {
//                }
//            }
//        }
//
//        Random generator = new Random();
//        int el = historyBoards.getChildren().size();
////        return historyBoards.getChildren().get(el - generator.nextInt(el)).getBoard();
//        return historyBoards.getChildren().get(1).getBoard();
    }
}