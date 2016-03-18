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

/**
 * This is the main class. Launches the solver and inits the UI.
 */
public class RushHourGame extends Application {

    // CHANGE THIS TO DESIRED BOARD NAME
    private static final String BOARDNAME = "board1a.rushhour";

    private List<Board> solutionBoards;

    public RushHourGame() {
        // get path
        String basepath = new File("").getAbsolutePath();
        basepath = basepath.concat("/boards/" + BOARDNAME);
        Path game = Paths.get(basepath);
        Board initialBoard = new Board(game);

        // bfs search
        RushBFS AI = new RushBFS(initialBoard);
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
         primaryStage.setTitle("The Fantastic RushHour Solver");

         Scene scene = new Scene(root, 800, 600);

         primaryStage.setScene(scene);
         primaryStage.show();
     }

}
