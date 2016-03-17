package rush.hour;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.nio.file.Paths;

public class RushHourGame extends Application {

    private Board board;

    public RushHourGame() {
        // Path ondersteund geen relatief pad...
//        Path game = Paths.get("/home/yorick/IdeaProjects/RushHour/boards/board1a.rushhour");
        Path game = Paths.get("/Users/Alex/Documents/Datastructuren/DatastructurenNew/rush_hour_ds/boards/board1a.rushhour");
        System.out.println(game.getParent());
        board = new Board(game);
        printCurrentBoard(board);


        RushAI kirby = new RushAI(board);
//        BoardHistory solution = kirby.solveBoard(board);
        board = kirby.getCurrentMoves(board);

        printCurrentBoard(board);
    }

    public static void main(String[] args) {
        RushHourGame rushHourGame = new RushHourGame();
//        launch(args);
    }

     @Override
     public void start(Stage primaryStage) throws Exception {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("../../res/frame.fxml"));
         UIController uiController = new UIController(board);
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
        System.out.println(board.toString());
    }
}
