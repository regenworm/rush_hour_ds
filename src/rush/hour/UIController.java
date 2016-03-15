package rush.hour;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {

    Board board;
    int[] boardSize;
    private Pane[][] mCells;

    @FXML
    HBox gridContainer;

    public UIController(Board board, int[] boardSize) {
        this.board = board;
        this.boardSize = boardSize;
        this.mCells = new Pane[board.getBoardRowCount()][board.getBoardColumnCount()];
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridContainer.getChildren().add(addGameBoard());
    }

    private GridPane addGameBoard() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        grid.setGridLinesVisible(true);
        grid.setStyle("-fx-background-color:white;");

        for (int row = 0; row < board.getBoardRowCount(); ++row) {
            for (int col = 0; col < board.getBoardColumnCount(); ++col) {
                Pane cell = new Pane();
                cell.getStyleClass().add("tile-default");
                grid.add(cell, col, row);
                mCells[row][col] = cell;
            }
            grid.getColumnConstraints().add(new ColumnConstraints(100));
            grid.getRowConstraints().add(new RowConstraints(100));
        }

        return grid;
    }
}
