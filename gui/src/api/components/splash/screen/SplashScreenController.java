package api.components.splash.screen;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SplashScreenController {

    @FXML private ImageView splashScreen;
    @FXML private ImageView loadingIcon;

    @FXML
    public void initialize() {
        RotateTransition rt = new RotateTransition(Duration.millis(600), loadingIcon);
        rt.setByAngle(360);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }

}
