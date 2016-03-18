package rush.hour;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import rush.hour.BoardElements.*;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Controller class for the UI
 */
public class UIController implements Initializable {

    @FXML
    HBox gridContainer;

    @FXML
    HBox stepCountLabelContainer;

    @FXML
    MenuItem onExit;

    @FXML
    Button onNext;

    @FXML
    Button onPrevious;

    private List<Board> boards;

    private Pane[][] tiles;
    private HashMap<Character, String> carColors;
    private int totalSteps;
    private int currentStep = 0;
    private Label stepsLabel;

    public UIController(List<Board> boards) {
        this.boards = boards;
        this.tiles = new Pane[boards.get(0).getBoardRowCount()][boards.get(0).getBoardColumnCount()];
        this.carColors = initializeNormalCarColors(this.boards.get(0));
        this.totalSteps = boards.size() - 1;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stepsLabel = new Label(Integer.toString(currentStep));
        stepCountLabelContainer.getChildren().add(stepsLabel);
        gridContainer.getChildren().add(addGameBoard(boards.get(0)));
        onExit.setOnAction(actionEvent -> exitProgram());
        onPrevious.setOnAction(actionEvent -> previousBoard());
        onNext.setOnAction(actionEvent -> nextBoard());
    }

    /**
     * Creates a new gameboard
     * @return Grid panel of gameboard
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

    /**
     * Creates a hashmap which assigns a color to every car id
     *
     * @param board a board
     * @return A hashmap where the key is the car id and the value the color in hex format, includes the hashtag.
     */
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
     * Performs the update of the UI.
     * @param board The current board
     */
    private void updateGameBoard(Board board) {
        stepsLabel.setText(" Showing step: " + Integer.toString(currentStep) + "/" + totalSteps);

        for (int y = 0; y < tiles[0].length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                tiles[x][y].setStyle("-fx-background-color: white;");
            }
        }
        for (BoardElement boardElement : board.getBoardElements()) {
            for (Tile tile : boardElement.getTiles()) {
                Pane cell = tiles[tile.getX()][tile.getY()];
                if (boardElement instanceof Wall) {
                    cell.setStyle("-fx-background-color: saddlebrown;");
                } else if (boardElement instanceof Empty) {
                    cell.setStyle("-fx-background-color: white;");
                } else if (boardElement instanceof Goal) {
                    cell.setStyle("-fx-background-color: black;");
                } else if (boardElement instanceof RedCar) {
                    cell.setStyle("-fx-background-color: red;");
                } else if (boardElement instanceof Car) {
                    cell.setStyle("-fx-background-color: " + carColors.get(boardElement.getId()) + ";");
                }
            }
        }
    }

    /**
     * Launches a board ui update, gets board from next step
     */
    private void nextBoard() {
        if (currentStep < totalSteps) {
            currentStep++;
            updateGameBoard(boards.get(currentStep));
        }
    }

    /**
     * Launches a board ui update, gets board from previous step
     */
    private void previousBoard() {
        if (currentStep > 0) {
            currentStep--;
            updateGameBoard(boards.get(currentStep));
        }
    }

    /**
     * Exit the program by getting the window from the gridcontainer (gameboard)
     */
    private void exitProgram() {
        Stage stage = (Stage) gridContainer.getScene().getWindow();
        stage.close();
    }

}
