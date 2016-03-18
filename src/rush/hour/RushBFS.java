package rush.hour;

import rush.hour.BoardElements.BoardElement;
import rush.hour.BoardElements.Car;
import rush.hour.BoardElements.RedCar;

import java.util.*;

/**
 * RushHour breadth first search
 */
public class RushBFS {

    HashMap<Board, Node> tree;
    Set<String> visitedBoards;
    Queue<Node> newBoards;
    Board initialBoard;

    public RushBFS(Board initialBoard) {
        this.tree = new HashMap<>();
        this.newBoards = new LinkedList<>();
        this.visitedBoards = new HashSet<>();
        this.initialBoard = initialBoard;
    }

    /**
     * Breadth first search
     *
     * @return a list of boards when a solution is found, null when there no solution could be found
     */
    public List<Board> BFSearch() {
        newBoards.add(new Node(initialBoard, null));
        long startTime = System.currentTimeMillis();
        Node currentNode;
        while (!newBoards.isEmpty()) {
            currentNode = newBoards.poll();

            // Add current board to the tree
            tree.put(currentNode.getBoard(), currentNode.getParent());

            // If game won exit loop
            if (gameWon(currentNode.getBoard())) {
                long stopTime = System.currentTimeMillis();
                System.out.println("Search execution time: " + Long.toString(stopTime - startTime) + "ms");
                return backTraverseTree(currentNode);
            }

            // Generate new boards from a board
            List<Board> newPossibleBoards = generateNextBoards(currentNode.getBoard());

            // Create new nodes for queue
            List<Node> nodes = new ArrayList<>();
            for (Board newPossibleBoard : newPossibleBoards) {
                if (!visitedBoards.contains(newPossibleBoard.toString())) {
                    nodes.add(new Node(newPossibleBoard, currentNode));
                    visitedBoards.add(newPossibleBoard.toString());
                }
            }
            // Add new boards to queue
            newBoards.addAll(nodes);
        }
        return null;
    }

    /**
     * Builds a list of boards from the tree in the form a of hashmap.
     *
     * @param winNode the last node
     * @return boards from start to goal
     */
    private List<Board> backTraverseTree(Node winNode) {
        List<Board> boards = new ArrayList<>();
        Node currentNode = winNode;
        while (currentNode.getParent() != null) {
            boards.add(currentNode.getBoard());
            currentNode = currentNode.getParent();
        }
        Collections.reverse(boards);
        boards.add(0, initialBoard);
        return boards;
    }

    /**
     * Tests if the redcar is placed on the win tiles in front of the goal
     *
     * @param board a board object
     * @return True when the redcar's tiles are exactly the same as the win tiles.
     */
    private boolean gameWon(Board board) {
        List<Tile> goalTiles = board.getGoalTiles();
        for (BoardElement boardElement : board.getBoardElements()) {
            if (boardElement instanceof RedCar) {
                List<Tile> redCarTiles = boardElement.getTiles();
                return goalTiles.containsAll(redCarTiles) && redCarTiles.containsAll(goalTiles);
            }
        }
        return false;
    }

    /**
     * Creates a list of boards which are possible next boards from a previous board
     *
     * @param previousBoard the previous bord object
     * @return list of boards
     */
    private List<Board> generateNextBoards(Board previousBoard) {
        List<Board> newBoards = new ArrayList<>();
        for (BoardElement boardElement : previousBoard.getBoardElements()) {
            char id = boardElement.getId();
            if (boardElement instanceof Car) {
                int amount = 0;
                Board tempBoard = null;
                while (true) {
                    try {
                        char[][] temp = previousBoard.serializeBoard();
                        int[] tempSize = previousBoard.getBoardSize();
                        tempBoard = new Board(temp, tempSize);
                        amount += 1;
                        tempBoard.move((Car) tempBoard.getBoardElement(id), amount);
                    } catch (BoardElementClashException e) {
                        try {
                            tempBoard.move((Car) tempBoard.getBoardElement(id), amount - 1);
                            newBoards.add(tempBoard);
                        } catch (Exception r) {
                        }
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        try {
                            tempBoard.move((Car) tempBoard.getBoardElement(id), amount - 1);
                            newBoards.add(tempBoard);

                        } catch (Exception r) {
                        }
                        break;
                    }
                }
                amount = 0;
                while (true) {
                    try {
                        char[][] temp = previousBoard.serializeBoard();
                        int[] tempSize = previousBoard.getBoardSize();
                        tempBoard = new Board(temp, tempSize);
                        amount -= 1;
                        tempBoard.move((Car) tempBoard.getBoardElement(id), amount);

                    } catch (BoardElementClashException e) {
                        try {
                            tempBoard.move((Car) tempBoard.getBoardElement(id), amount + 1);
                            newBoards.add(tempBoard);

                        } catch (Exception r) {
                        }
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        try {
                            tempBoard.move((Car) tempBoard.getBoardElement(id), amount + 1);
                            newBoards.add(tempBoard);

                        } catch (Exception r) {
                        }
                        break;
                    }
                }
            }
        }

        return newBoards;
    }

    /**
     * Helper class for tree structure, contains a board and a parent Node
     */
    private class Node {
        Node parent;
        Board board;

        public Node(Board board, Node parent) {
            this.parent = parent;
            this.board = board;
        }

        public Node getParent() {
            return parent;
        }

        public Board getBoard() {
            return board;
        }
    }

}
