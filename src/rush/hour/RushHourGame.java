package rush.hour;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class RushHourGame extends Application {

    private List<Board> solutionBoards;

    public RushHourGame() {
        // get path
        String basepath = new File("").getAbsolutePath();
        basepath = basepath.concat("/boards/board1a.rushhour");
        Path game = Paths.get(basepath);
        Board initialBoard = new Board(game);

        RushYorickBFS AI = new RushYorickBFS(initialBoard);
        solutionBoards = AI.BFSearch();
    }

    public static void main(String[] args) {
        launch(args);
    }

     @Override
     public void start(Stage primaryStage) throws Exception {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("../../res/frame.fxml"));
         UIController uiController = new UIController(solutionBoards);
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
