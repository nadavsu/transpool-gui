package api;

import api.components.TransPoolController;
import api.components.splash.screen.SplashScreenController;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.Engine;
import logic.TransPoolEngine;

import java.net.URL;

public class TransPool extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("TransPool");
        primaryStage.getIcons().add(new Image(Constants.STAGE_ICON_LOCATION));

        FXMLLoader splashScreenLoader = new FXMLLoader();
        FXMLLoader mainLoader = new FXMLLoader();
        URL splashScreenFXML = getClass().getResource(Constants.SPLASH_SCREEN_FXML_LOCATION);
        URL mainFXML = getClass().getResource(Constants.MAIN_SCENE_FXML_LOCATION);

        //Loading the splash screen
        splashScreenLoader.setLocation(splashScreenFXML);
        Parent splashScreenRoot = splashScreenLoader.load();
        Scene splashScreenScene = new Scene(splashScreenRoot, 1080, 720);
        SplashScreenController splashScreenController = splashScreenLoader.getController();
        primaryStage.setScene(splashScreenScene);
        primaryStage.show();

        //Loading the main FXML scene
        mainLoader.setLocation(mainFXML);
        Parent mainRoot = mainLoader.load();
        Scene mainScene = new Scene(mainRoot, 1080, 720);

        //Setting up the controller & engine
        TransPoolController transpoolController = mainLoader.getController();
        Engine engine = new TransPoolEngine(transpoolController);
        transpoolController.setEngine(engine);
        transpoolController.setPrimaryStage(primaryStage);

        //Pause transition for the splash screen. When pause is finished the scene is switched to the main scene.
        PauseTransition splashScreenPause = new PauseTransition(Duration.millis(3300));
        splashScreenPause.setOnFinished(event -> {
            primaryStage.setScene(mainScene);
        });

        splashScreenPause.play();
    }

    public static void main(String[] args) {
        launch(TransPool.class);
    }
}