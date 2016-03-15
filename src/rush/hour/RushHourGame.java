package rush.hour;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rush.hour.BoardElements.Car;

import java.nio.file.Path;
import java.nio.file.Paths;

public class RushHourGame extends Application {

    private Board board;
    private int[] boardSize;

    public RushHourGame() {
        Path game = Paths.get("/home/yorick/IdeaProjects/RushHour/res/board1a.rushhour");
        board = new Board(game);
        boardSize = board.getBoardSize();
        printCurrentBoard(board);
        if (board.getBoardElements().get(1) instanceof Car) {
            try {
                board.move((Car) board.getBoardElements().get(9), -3);
                printCurrentBoard(board);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public static void main(String[] args) {
        RushHourGame rushHourGame = new RushHourGame();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../res/frame.fxml"));
        UIController uiController = new UIController(board, boardSize);
        loader.setController(uiController);
        Parent root = loader.load();
        primaryStage.setTitle("RushHour");

        Scene scene = new Scene(root, 300, 275);
        String css = this.getClass().getResource("../../res/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void printCurrentBoard(Board board) {
        board.printSerializedBoard();
    }
}
