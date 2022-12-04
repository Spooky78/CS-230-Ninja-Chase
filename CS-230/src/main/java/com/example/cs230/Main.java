package com.example.cs230;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

/**
 * The main class which contains main method to run application.
 *
 * @author Vic
 */

//TODO: Player:                                     DONE
    //TODO: Movement                                DONE

//TODO: Assassin:
    //TODO: Movement                    Vic         DONE
    //TODO: Collision player            Vic         DONE
    //TODO: Collision NPCs              Vic

//TODO: Smart Thief:
//TODO: Draw                            Rex         DONE
//TODO: Movement

//TODO: Floor Following Thief:
//TODO: Draw                            Dimitrios   DONE
//TODO: Movement

//TODO: Coins:
    //TODO: Draw                        Omar        DONE
    //TODO: Collision Player            Meeting     DONE
    //TODO: Collision NPC

//TODO: Lever:
    //TODO: Draw                        Chrysis     DONE
    //TODO: Collision player/NPC

//TODO: Gate:
    //TODO: Draw                        Lewis       DONE

//TODO: Door:
    //TODO: Draw                        Vic         DONE
    //TODO: Collision

//TODO: Clock:
    //TODO: Draw                        Lewis       DONE
    //TODO: Collision/Add Time          Lewis       DONE

//TODO: Bomb:
    //TODO: Draw                        Arran
    //TODO: Collision

//TODO: Time
    //TODO: Draw time to screen

//TODO: Player Profiles:
    //TODO: Write names to txt file
    //TODO: Reading names from txt file
    //TODO: Delete names from txt file

//TODO: End Screen                      Vic

//TODO: Save File (maybe do together near end of week):
    //TODO: write save txt file
    //TODO: read save txt file


public class Main extends Application {
    //private Stage mainStage;
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        final Stage mainStage = new Stage();
        ViewManager manager = new ViewManager();
        manager.createNewMenu(mainStage);
        //primaryStage = manager.getMainStage();
        //primaryStage.show();
    }

    /**
     * Entry to main method.
     *
     * @param args command-line arguments array.
     */
    public static void main(String[] args) {
        launch(args);
    }

}