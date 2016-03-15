package rush.hour;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import rush.hour.BoardElements.*;

import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {

    @FXML
    HBox gridContainer;
    @FXML
    MenuItem onExit;
    private Board board;
    private Pane[][] tiles;

    public UIController(Board board) {
        this.board = board;
        this.tiles = new Pane[board.getBoardRowCount()][board.getBoardColumnCount()];
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridContainer.getChildren().add(addGameBoard());
        onExit.setOnAction((event) -> exitProgram());
    }

    private GridPane addGameBoard() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        grid.setGridLinesVisible(true);
        grid.setStyle("-fx-background-color:white;");

        for (BoardElement boardElement : board.getBoardElements()) {
            for (Tile tile : boardElement.getTiles()) {
                Pane cell = new Pane();
                if (boardElement instanceof Wall) {
                    cell.getStyleClass().add("tile-wall");
                } else if (boardElement instanceof Empty) {
                    cell.getStyleClass().add("tile-empty");
                } else if (boardElement instanceof Goal) {
                    cell.getStyleClass().add("tile-goal");
                } else if (boardElement instanceof RedCar) {
                    cell.getStyleClass().add("tile-redcar");
                } else if (boardElement instanceof Car) {
                    //TODO randomize normal car colors
                    cell.setStyle("-fx-background-color: " + "#AA66CC" + ";");
                }
                grid.add(cell, tile.getX(), tile.getY());
                tiles[tile.getX()][tile.getY()] = cell;
            }
        }

        // Sets the height and width of the board tiles/cells
        int loopOver;
        if (board.getBoardColumnCount() > board.getBoardRowCount()) {
            loopOver = board.getBoardColumnCount();
        } else {
            loopOver = board.getBoardRowCount();
        }
        for (int i=0; i < loopOver; i++) {
            grid.getColumnConstraints().add(new ColumnConstraints(100));
            grid.getRowConstraints().add(new RowConstraints(100));
        }

        return grid;
    }

    private void exitProgram() {
        Stage stage = (Stage) gridContainer.getScene().getWindow();
        stage.close();
    }

}
