package rush.hour.Search;

import rush.hour.Board;
import rush.hour.BoardElementClashException;
import rush.hour.BoardElements.BoardElement;
import rush.hour.BoardElements.Car;
import rush.hour.BoardElements.RedCar;
import rush.hour.Tile;

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
        List<Board> backwardSearchList = new ArrayList<>();

        newBoards.add(new Node(initialBoard, null));

        Node currentNode;
        int count = 0;
        while (!newBoards.isEmpty()) {
            currentNode = newBoards.poll();

            System.out.println("LOOP COUNT: " + count++);
//            System.out.println(currentNode.getBoard());

            // Add the current board to visited boards
//            visitedBoards.add(currentNode.getBoard().serializeBoard());
            // Add current board to the tree
            tree.put(currentNode.getBoard(), currentNode.getParent());

            // If game won exit loop
            if (gameWon(currentNode.getBoard())) {
                System.out.println("won");
                break;
            }

            // Generate new boards from a board
            List<Board> newPossibleBoards = generateNextBoards(currentNode.getBoard());

            // Create new nodes for queue
            List<Node> nodes = new ArrayList<>();
            for (Board newPossibleBoard : newPossibleBoards) {
                if (!visitedBoards.contains(newPossibleBoard.toString())) {
                    nodes.add(new Node(newPossibleBoard, currentNode));
                    visitedBoards.add(newPossibleBoard.toString());

                    System.out.println(newPossibleBoard);
                }
            }
            // Add new boards to queue
            newBoards.addAll(nodes);

//            if (count > 1) {
//                break;
//            }
        }


        return backwardSearchList;
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
//        for (BoardElement boardElement : previousBoard.getBoardElements()) {
        for (BoardElement boardElement : previousBoard.getBoardElements()) {
            char id = boardElement.getId();
            if (boardElement instanceof Car) {
                System.out.println("Moving: " + boardElement.getId());
                int amount = 0;
                Board tempBoard = null;
                while (true) {
                    try {
                        char[][] temp = previousBoard.serializeBoard();
                        int[] tempSize = previousBoard.getBoardSize();
                        tempBoard = new Board(temp, tempSize);
                        amount += 1;
                        tempBoard.move((Car) tempBoard.getBoardElement(id), amount);
//                        tempBoard.move((Car) boardElement, amount);
//                        System.out.println("Between: \n" + tempBoard);
                    } catch (BoardElementClashException e) {
//                        System.out.println("Vehicle clash");
                        try {
                            tempBoard.move((Car) tempBoard.getBoardElement(id), amount - 1);
                            newBoards.add(tempBoard);
                        } catch (Exception r) {
                        }
//                        System.out.println("amount " + amount);
//                        System.out.println("Final: \n" + tempBoard);
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
//                        System.out.println("Vehicle out of bounds");
                        try {
                            tempBoard.move((Car) tempBoard.getBoardElement(id), amount - 1);
                            newBoards.add(tempBoard);

                        } catch (Exception r) {
                        }
//                        System.out.println("amount " + amount);
//                        System.out.println("Final: \n" + tempBoard);
                        break;
//                    } catch (CloneNotSupportedException e) {
//                        System.err.println("Cloning not supported");
//                        break;
                    }
                }
                amount = 0;
                while (true) {
                    try {
                        char[][] temp = previousBoard.serializeBoard();
                        int[] tempSize = previousBoard.getBoardSize();
                        tempBoard = new Board(temp, tempSize);
                        amount -= 1;
//                        tempBoard.move((Car) boardElement, amount);
                        tempBoard.move((Car) tempBoard.getBoardElement(id), amount);

//                        System.out.println("Between: \n" + tempBoard);
                    } catch (BoardElementClashException e) {
//                        System.out.println("Vehicle clash");
                        try {
                            tempBoard.move((Car) tempBoard.getBoardElement(id), amount + 1);
                            newBoards.add(tempBoard);

                        } catch (Exception r) {
                        }
//                        System.out.println("amount " + amount);
//                        System.out.println("Final: \n" + tempBoard);
                        break;
                    } catch (ArrayIndexOutOfBoundsException e) {
//                        System.out.println("Vehicle out of bounds");
                        try {
                            tempBoard.move((Car) tempBoard.getBoardElement(id), amount + 1);
                            newBoards.add(tempBoard);

                        } catch (Exception r) {
                        }
//                        System.out.println("amount " + amount);
//                        System.out.println("Final: \n" + tempBoard);
                        break;
//                    } catch (CloneNotSupportedException e) {
//                        System.out.println("Cloning not supported");
//                        break;
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
