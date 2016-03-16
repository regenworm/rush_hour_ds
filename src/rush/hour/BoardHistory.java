package rush.hour;

import java.util.ArrayList;
import java.util.List;

public class BoardHistory {
    private Board board;
    private BoardHistory parent;
    private List<BoardHistory> children;
    private int level;
    public int[] moves = new int[2];

    BoardHistory(Board board) {
        this.board = board;
        this.level = 0;
        this.children = new ArrayList<BoardHistory>();
    }

    BoardHistory(Board board, int moves[], BoardHistory parent) {
        this.board = board;
        this.parent = parent;
        this.moves = moves;
        this.level = parent.getLevel()+1;
        this.children = new ArrayList<BoardHistory>();
    }

    public int getLevel() {
        return level;
    }

//    public List<BoardHistory> getNodesOfLevel

    public void addNode(Board board,int elementMoved, int tilesMoved) {
        int[] moves = new int[2];
        moves[0] = elementMoved;
        moves[1] = tilesMoved;

        children.add(new BoardHistory(board, moves, this));
    }
}
