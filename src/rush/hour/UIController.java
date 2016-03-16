package rush.hour;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import rush.hour.BoardElements.*;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class UIController implements Initializable {

    @FXML
    HBox gridContainer;

    @FXML
    MenuItem onExit;

    @FXML
    Button onNext;

    @FXML
    Button onPrevious;

    private Board board;
    private Pane[][] tiles;

    public UIController(Board board) {
        this.board = board;
        this.tiles = new Pane[board.getBoardRowCount()][board.getBoardColumnCount()];
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridContainer.getChildren().add(addGameBoard());
        onExit.setOnAction(actionEvent -> exitProgram());
        onPrevious.setOnAction(actionEvent -> nextBoard());
        onNext.setOnAction(actionEvent -> previousBoard());
    }

    /**
     * Creates a new gameboard
     * @return Grind panel of gameboard
     */
    private GridPane addGameBoard() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        grid.setGridLinesVisible(true);
        grid.setStyle("-fx-background-color:white;");

        for(int y=0; y < board.getBoardRowCount(); y++) {
            for (int x=0; x < board.getBoardColumnCount(); x++) {
                Pane cell = new Pane();
                grid.add(cell, x, y);
                tiles[x][y] = cell;
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
            grid.getColumnConstraints().add(new ColumnConstraints(60));
            grid.getRowConstraints().add(new RowConstraints(60));
        }

        return grid;
    }

    /**
     * Updates the styles of the tiles on a gameboard
     */
    private void updateGameBoard() {
        for (BoardElement boardElement : board.getBoardElements()) {
            String randomCarColor = Util.generateColor(new Random());
            for (Tile tile : boardElement.getTiles()) {
                Pane cell = tiles[tile.getX()][tile.getY()];
                if (boardElement instanceof Wall) {
                    cell.getStyleClass().add("tile-wall");
                } else if (boardElement instanceof Empty) {
                    cell.getStyleClass().add("tile-empty");
                } else if (boardElement instanceof Goal) {
                    cell.getStyleClass().add("tile-goal");
                } else if (boardElement instanceof RedCar) {
                    cell.getStyleClass().add("tile-redcar");
                } else if (boardElement instanceof Car) {
                    //TODO SAVE CAR COLOR BETWEEN STEPS
                    cell.setStyle("-fx-background-color: " + randomCarColor + ";");
                }
            }
        }
    }

    private void nextBoard() {
        updateGameBoard();
    }

    private void previousBoard() {
        updateGameBoard();
    }

    /**
     * Exit the program by getting the window from the gridcontainer (gameboard)
     */
    private void exitProgram() {
        Stage stage = (Stage) gridContainer.getScene().getWindow();
        stage.close();
    }

}
