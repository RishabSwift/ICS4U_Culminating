package culminating;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * MainStage.java
 * This class will load the main stage.
 * June 18, 2018
 */
public class MainStage extends Stage {

    public MainStage() throws IOException {

        // Use fxml file to render the view of application
        Parent root = FXMLLoader.load(getClass().getResource("../assets/fxml/MainStage.fxml"));

        this.setTitle(MainApp.GAME_NAME);
        this.setResizable(false);
        // Load scene and set the game width/height
        final Scene mainScene = new Scene(root, MainApp.GAME_WIDTH, MainApp.GAME_HEIGHT);
        // Set scene to current and show
        this.setScene(mainScene);
        this.show();
    }
}
