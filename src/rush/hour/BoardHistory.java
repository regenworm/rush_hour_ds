package rush.hour;

import java.util.ArrayList;
import java.util.List;

public class BoardHistory {
    private Board board;
    private BoardHistory parent;
    private List<BoardHistory> children;
    private int level;
    private int[] moves = new int[2];
    private int score;

    public int[] getMoves() {
        return moves;
    }

    public Board getBoard() {

        return board;
    }

    BoardHistory(Board board) {
        this.board = board;
        this.level = 0;
        this.children = new ArrayList<BoardHistory>();
        this.score = getScore();
    }

    BoardHistory(Board board, int moves[], BoardHistory parent) {
        this.board = board;
        this.parent = parent;
        this.moves = moves;
        this.level = parent.getLevel()+1;
        this.children = new ArrayList<BoardHistory>();
        this.score = getScore();
    }

    private int getScore() {
        return 0;
    }

    public int getLevel() {
        return level;
    }

    public List<BoardHistory> getChildren() {
        return children;
    }

//    throws ex no children
    private void getNodes(List<BoardHistory> nodesOfLevel, int level) {
        if (this.children == null) {
            return;
        }
        for (int i = 0; i < this.children.size(); i++) {
            int currentLevel = this.children.get(i).getLevel();
            if (currentLevel < level) {
                getNodes(this.children.get(i).getChildren(),level);
            } else if (currentLevel == level) {
                nodesOfLevel.add(this);
            } else {
                break;
            }
        }
    }

    public List<BoardHistory> getNodesOfLevel(int level) {
        List<BoardHistory> nodesOfLevel = new ArrayList<BoardHistory>();
        getNodes(nodesOfLevel, level);
        return nodesOfLevel;
    }

//    throws ex redundant move
    public BoardHistory addNode(Board board,int elementMoved, int tilesMoved) {
        int[] moves = new int[2];
        moves[0] = elementMoved;
        moves[1] = tilesMoved;

        children.add(new BoardHistory(board, moves, this));

        return children.get(children.size()-1);
    }
}
