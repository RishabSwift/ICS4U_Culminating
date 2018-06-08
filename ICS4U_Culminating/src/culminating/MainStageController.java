package culminating;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainStageController {

    @FXML
    private Button buttonNewGame;
    @FXML
    private Button buttonLoadGame;
    @FXML
    private Button buttonExitGame;


    /**
     * Initialize the images for the main menu
     */
    public void initialize() {

        Image imageNewGame = new Image(getClass().getResourceAsStream("/assets/images/buttons/button_NewGame.png"));
        Image imageNewGameHover = new Image(getClass().getResourceAsStream("/assets/images/buttons/button_NewGameHover.png"));
        Image imageNewGameClicked = new Image(getClass().getResourceAsStream("/assets/images/buttons/button_NewGameClicked.png"));
        Image imageLoadGame = new Image(getClass().getResourceAsStream("/assets/images/buttons/button_LoadGame.png"));
        Image imageLoadGameHover = new Image(getClass().getResourceAsStream("/assets/images/buttons/button_LoadGameHover.png"));
        Image imageLoadGameClicked = new Image(getClass().getResourceAsStream("/assets/images/buttons/button_LoadGameClicked.png"));
        Image imageExitGame = new Image(getClass().getResourceAsStream("/assets/images/buttons/button_ExitGame.png"));
        Image imageExitGameHover = new Image(getClass().getResourceAsStream("/assets/images/buttons/button_ExitGameHover.png"));
        Image imageExitGameClicked = new Image(getClass().getResourceAsStream("/assets/images/buttons/button_ExitGameClicked.png"));

        setButtonStyle(buttonNewGame, imageNewGame, imageNewGameHover, imageNewGameClicked);
        setButtonStyle(buttonLoadGame, imageLoadGame, imageLoadGameHover, imageLoadGameClicked);
        setButtonStyle(buttonExitGame, imageExitGame, imageExitGameHover, imageExitGameClicked);

    }

    @FXML
    private void exitGameAction(ActionEvent event) {
        ((Stage) buttonExitGame.getScene().getWindow()).close();
    }

    /**
     * Handle button style for hover and click
     *
     * @param button
     * @param image
     * @param imageHover
     * @param imageClicked
     */
    public void setButtonStyle(Button button, Image image, Image imageHover, Image imageClicked) {

        ImageView[] images = new ImageView[3];
        images[0] = new ImageView(image);
        images[1] = new ImageView(imageHover);
        images[2] = new ImageView(imageClicked);

        for (ImageView _image : images) {
            _image.setFitHeight(52);
            _image.setFitWidth(257);
        }

        button.setOnMouseEntered(event -> {
            button.setGraphic(images[1]);
        });
        button.setOnMouseExited(event -> {
            button.setGraphic(images[0]);
        });
        button.setOnMousePressed(event -> {
            button.setGraphic(images[2]);
        });
        button.setOnMouseReleased(event -> {
            button.setGraphic(images[1]);
        });
    }

    /**
     * What happens when the new game is started
     * @param actionEvent
     */
    public void startGameAction(ActionEvent actionEvent) {
        ((Stage) buttonExitGame.getScene().getWindow()).hide();
        new GameStage();
    }
}
