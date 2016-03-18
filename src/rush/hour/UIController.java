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
import java.util.HashMap;
import java.util.List;
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

    private List<Board> boards;
    private Pane[][] tiles;
    private HashMap<Character, String> carColors;
    private int currentBoardShown = 0;

    public UIController(List<Board> boards) {
        this.boards = boards;
        System.err.println(boards);
        this.tiles = new Pane[boards.get(0).getBoardRowCount()][boards.get(0).getBoardColumnCount()];
        this.carColors = initializeNormalCarColors(this.boards.get(0));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gridContainer.getChildren().add(addGameBoard(boards.get(0)));
        onExit.setOnAction(actionEvent -> exitProgram());
        onPrevious.setOnAction(actionEvent -> previousBoard());
        onNext.setOnAction(actionEvent -> nextBoard());
    }

    /**
     * Creates a new gameboard
     * @return Grind panel of gameboard
     */
    private GridPane addGameBoard(Board board) {
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
        updateGameBoard(board);
        return grid;
    }

    private HashMap<Character, String> initializeNormalCarColors(Board board) {
        HashMap<Character, String> carColors = new HashMap<>();
        for (BoardElement boardElement : board.getBoardElements()) {
            if (boardElement instanceof Car) {
                String randomCarColor = Util.generateColor(new Random());
                carColors.put(boardElement.getId(), randomCarColor);
            }
        }
        return carColors;
    }

    /**
     * Updates the styles of the tiles on a gameboard
     */
    private void updateGameBoard(Board board) {
        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                tiles[x][y].getStyleClass().clear();
                tiles[x][y].getStyleClass().add("tile-empty");
            }
        }
        for (BoardElement boardElement : board.getBoardElements()) {
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
                    System.out.println(carColors.get(boardElement.getId()));
                    cell.setStyle("-fx-background-color: " + carColors.get(boardElement.getId()) + ";");
                }
            }
        }
    }

    private void nextBoard() {
        updateGameBoard(boards.get(currentBoardShown + 1));
        currentBoardShown++;
    }

    private void previousBoard() {
        updateGameBoard(boards.get(currentBoardShown - 1));
        currentBoardShown--;
    }

    /**
     * Exit the program by getting the window from the gridcontainer (gameboard)
     */
    private void exitProgram() {
        Stage stage = (Stage) gridContainer.getScene().getWindow();
        stage.close();
    }

}
