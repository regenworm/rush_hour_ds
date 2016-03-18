package rush.hour;

import rush.hour.BoardElements.BoardElement;
import rush.hour.BoardElements.Car;
import rush.hour.BoardElements.RedCar;

import java.util.*;

public class RushYorickBFS {

    HashMap<Board, Node> tree;
    Set<String> visitedBoards;
    Queue<Node> newBoards;
    Board initialBoard;

    public RushYorickBFS(Board initialBoard) {
        this.tree = new HashMap<>();
        this.newBoards = new LinkedList<>();
        this.visitedBoards = new HashSet<>();
        this.initialBoard = initialBoard;
    }

    public List<Board> BFSearch() {
        newBoards.add(new Node(initialBoard, null));

        Node currentNode;
        int count = 0;
        while (!newBoards.isEmpty()) {
            currentNode = newBoards.poll();

            // Add current board to the tree
            tree.put(currentNode.getBoard(), currentNode.getParent());

            // If game won exit loop
            if (gameWon(currentNode.getBoard())) {
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

    private List<Board> backTraverseTree(Node windNode) {
        List<Board> boards = new ArrayList<>();
        Node currentNode = windNode;
        while (currentNode.getParent() != null) {
            boards.add(currentNode.getBoard());
            currentNode = currentNode.getParent();
        }
        Collections.reverse(boards);
        return boards;
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

    public List<Board> generateNextBoards(Board previousBoard) {
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

    public class Node {
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
