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

        Parent root = FXMLLoader.load(getClass().getResource("../assets/fxml/MainStage.fxml"));

        this.setTitle(MainApp.GAME_NAME);
        this.setResizable(false);

//        this.getScene().getStylesheets().add("/assets/style/stylesheet.css");

        final Scene mainScene = new Scene(root, MainApp.GAME_WIDTH, MainApp.GAME_HEIGHT);
        this.setScene(mainScene);
        this.show();
    }
}
