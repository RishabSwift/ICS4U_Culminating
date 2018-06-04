package culminating;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class runs the main application and sets the parameters used everywhere else throughout the game,
 * such as the width, height, and the game name
 */
public class MainApp extends Application {

    public static String GAME_NAME = "The Best Singerss";
    public static int GAME_WIDTH = 1050;
    public static int GAME_HEIGHT = 800;

    public static void main(String[] args) {
    	launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        new MainStage();
    }


}
