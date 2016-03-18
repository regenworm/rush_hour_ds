package rush.hour;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rush.hour.Search.RushYorickBFS;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RushHourGame extends Application {

    private Board board;

    public RushHourGame() {
        // get path
        String basepath = new File("").getAbsolutePath();
        basepath  = basepath.concat("/../boards/board1a.rushhour");
        Path game = Paths.get(basepath);
        board = new Board(game);

        // init AI
        RushYorickBFS AI = new RushYorickBFS(board);
        AI.BFSearch();


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

}
