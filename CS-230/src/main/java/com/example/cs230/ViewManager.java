package com.example.cs230;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Responsible for the main menu window.
 * @author Everybody
 */
public class ViewManager {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private final BorderPane mainPane;
    private final Scene mainScene;
    private final Stage mainStage;
    private static final String BACKGROUND_PATH = "treesBackground.png";
    private static VBox buttonPane;
    private static VBox logoPane;
    private static StackPane subscenePane;
    private MainMenuSubScene creditsSubScene;
    private MainMenuSubScene helpSubScene;
    private MainMenuSubScene scoreSubScene;
    private MainMenuSubScene shipChooserSubScene;
    private MainMenuSubScene sceneToHide;
    private List<NinjaPicker> ninjaPickerList;
    private Ninja chosenNinja;

    /**
     * Creates a main menu window.
     */
    public ViewManager() {
        mainPane = new BorderPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScenes();
        createBackground();
        createLogo();
        createMsgOfTheDay();
        createButtons();

    }

    /**
     * If sub scene is hidden then move it.
     * @param subScene The sub scene to be moved.
     */
    private void showSubScene(MainMenuSubScene subScene) {
        if (sceneToHide != null) {
            sceneToHide.moveSubScene();
        }

        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    /**
     * Creates the sub scenes for the main menu.
     */
    private void createSubScenes() {
        subscenePane = new StackPane();
        creditsSubScene = new MainMenuSubScene(subscenePane);
        mainPane.getChildren().add(creditsSubScene);

        helpSubScene = new MainMenuSubScene(subscenePane);
        mainPane.getChildren().add(helpSubScene);

        scoreSubScene = new MainMenuSubScene(subscenePane);
        mainPane.getChildren().add(scoreSubScene);

        shipChooserSubScene = new MainMenuSubScene(subscenePane);
        mainPane.getChildren().add(shipChooserSubScene);

        mainPane.setCenter(subscenePane);
        createPlayerCharacterChooserSubScene();
    }

    /**
     * Creates the player character chooser.
     */
    private void createPlayerCharacterChooserSubScene() {
        shipChooserSubScene = new MainMenuSubScene(subscenePane);
        mainPane.getChildren().add(shipChooserSubScene);

        //InfoLabel chooseShipLabel = new InfoLabel("Choose your ship!");
        //chooseShipLabel.setLayoutX(POSITION_X);
        //chooseShipLabel.setLayoutY(POSITION_Y);
        //shipChooserSubScene.getPane().getChildren().add(chooseShipLabel);
        shipChooserSubScene.getPane().getChildren().add(createPlayerCharacterToChoose());
        shipChooserSubScene.getPane().getChildren().add(createButtonToStart());
    }

    /**
     * Creates the start button.
     * @return The start button.
     */
    private MainMenuButton createButtonToStart() {
        MainMenuButton startButton = new MainMenuButton("START");
        startButton.setLayoutX(125);
        startButton.setLayoutY(300);

        startButton.setOnAction(actionEvent -> {
            if (chosenNinja != null) {
                GameViewManager gameManager = new GameViewManager();
                gameManager.createNewGame(mainStage, chosenNinja);
            }
        });

        return startButton;
    }

    /**
     * Creates the player character to choose.
     * @return The HBox of the player character.
     */
    private HBox createPlayerCharacterToChoose() {
        HBox box = new HBox();
        box.setSpacing(20);
        ninjaPickerList = new ArrayList<>();
        for (Ninja ninja: Ninja.values()) {
            NinjaPicker shipToPick = new NinjaPicker(ninja);
            ninjaPickerList.add(shipToPick);
            box.getChildren().add(shipToPick);
            shipToPick.setOnMouseClicked(mouseEvent -> {
                for (NinjaPicker ship1 : ninjaPickerList) {
                    ship1.setIsBoxChosen(false);
                }
                shipToPick.setIsBoxChosen(true);
                chosenNinja = shipToPick.getNinja();
            });
        }
        box.setPadding(new Insets(25));
        box.setLayoutX(Pos.CENTER.ordinal());
        box.setLayoutY(125);
        return box;
    }


    /**
     * Gets main stage.
     * @return The main stage.
     */
    public Stage getMainStage() {
        return mainStage;
    }

    /**
     * Creates all main menu buttons.
     */
    private void createButtons() {
        buttonPane = new VBox();
        buttonPane.setSpacing(20);
        buttonPane.setAlignment(Pos.CENTER_LEFT);
        buttonPane.setPadding(new Insets(0,20,20,60));
        mainPane.setLeft(buttonPane);
        createStartButton();
        createScoreButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    /**
     * Creates the play button.
     */
    private void createStartButton() {
        MainMenuButton startButton = new MainMenuButton("PLAY");
        buttonPane.getChildren().add(startButton);
        startButton.setOnAction(actionEvent -> showSubScene(shipChooserSubScene));
    }

    /**
     * Creates the score button.
     */
    private void createScoreButton() {
        MainMenuButton scoreButton = new MainMenuButton("Scores");
        buttonPane.getChildren().add(scoreButton);
        scoreButton.setOnAction(actionEvent -> showSubScene(scoreSubScene));
    }

    /**
     * Creates the help button.
     */
    private void createHelpButton() {
        MainMenuButton helpButton = new MainMenuButton("Help");
        buttonPane.getChildren().add(helpButton);
        helpButton.setOnAction(actionEvent -> showSubScene(helpSubScene));
    }

    /**
     * Creates the credits button.
     */
    private void createCreditsButton() {
        MainMenuButton creditsButton = new MainMenuButton("Credits");
        buttonPane.getChildren().add(creditsButton);
        creditsButton.setOnAction(actionEvent -> showSubScene(creditsSubScene));
    }

    /**
     * Creates the exit button, that exits the application.
     */
    private void createExitButton() {
        MainMenuButton exitButton = new MainMenuButton("Exit");
        buttonPane.getChildren().add(exitButton);
        exitButton.setOnAction(actionEvent -> mainStage.close());
    }

    /**
     * Sets the background to images.
     */
    private void createBackground() {
        Image backgroundImage = new Image(BACKGROUND_PATH, WIDTH, HEIGHT, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, new BackgroundSize(100, 100,
            true, true, true, true));
        mainPane.setBackground(new Background(background));
    }

    /**
     * Creates the game logo.
     */
    private void createLogo() {

        ImageView logo = new ImageView("logo.png");

        logo.setOnMouseEntered(mouseEvent -> logo.setEffect(new DropShadow()));
        logo.setOnMouseExited(mouseEvent -> logo.setEffect(null));

        logoPane = new VBox();
        logoPane.getChildren().add(logo);
        logoPane.setAlignment(Pos.TOP_CENTER);
        logoPane.setPadding(new Insets(30,0,0,0));
        mainPane.setTop(logoPane);
    }
//TODO: format msg text.
    private void createMsgOfTheDay() {
        String msg;
        MsgOfTheDay m = new MsgOfTheDay();
        msg = m.getRequest();
        Text msgOfTheDay = new Text(msg);

        logoPane.getChildren().add(msgOfTheDay);
    }
}