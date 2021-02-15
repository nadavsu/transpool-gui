package api.components.menu.bar;

import api.components.TransPoolController;
import com.jfoenix.controls.JFXButton;
import exception.data.TransPoolDataException;
import exception.data.TransPoolFileNotFoundException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.stage.FileChooser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.Optional;
import java.util.concurrent.ExecutionException;


/**
 * The main controller for the menubar on the top left hand side of the UI.
 */
public class MenuBarController {

    private final static String DARK_COLOR_SCHEME = "/api/components/main/scheme/color_scheme_dark.css";
    private final static String LIGHT_COLOR_SCHEME = "/api/components/main/scheme/color_scheme_light.css";

    private TransPoolController transpoolController;

    @FXML private MenuItem menuItemOpen;
    @FXML private MenuItem menuItemClose;
    @FXML private MenuItem menuItemAbout;
    @FXML private RadioMenuItem menuItemDarkMode;
    @FXML private RadioMenuItem menuItemLightMode;

    private BooleanProperty fileLoaded;

    public MenuBarController() {
        fileLoaded = new SimpleBooleanProperty();
    }

    public void setTransPoolController(TransPoolController transpoolController) {
        this.transpoolController = transpoolController;
    }

    @FXML
    public void initialize() {

    }

    @FXML
    public void openMenuItemAction(ActionEvent event) {
        transpoolController.loadFile();
    }

    @FXML
    public void helpMenuItemActionEvent(ActionEvent event) {

    }

    @FXML
    public void quitMenuItemActionEvent(ActionEvent event) {
        transpoolController.quit();
    }

    @FXML
    public void darkThemeMenuItemActionEvent(ActionEvent event) {
        transpoolController.setColorScheme(DARK_COLOR_SCHEME);
    }

    @FXML
    public void lightThemeMenuItemActionEvent(ActionEvent event) {
        transpoolController.setColorScheme(LIGHT_COLOR_SCHEME);
    }

    public boolean isFileLoaded() {
        return fileLoaded.get();
    }

    public BooleanProperty fileLoadedProperty() {
        return fileLoaded;
    }

    public void setFileLoaded(boolean fileLoaded) {
        this.fileLoaded.set(fileLoaded);
    }

    public void loadFile() throws  InterruptedException, ExecutionException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load TransPool data file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML File", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(transpoolController.getPrimaryStage());
        if (selectedFile == null) {
            return;
        }
        transpoolController.getEngine().loadFile(selectedFile);
    }

    /**
     * Unfortunately this doesn't work and I don't know why :(
     * @param colorSchemeFileLocation - The location of the colorScheme file.
     */
    public void setColorScheme(String colorSchemeFileLocation) {
        transpoolController.getPrimaryStage().getScene().getStylesheets().clear();
        transpoolController
                .getPrimaryStage()
                .getScene()
                .getStylesheets()
                .add(getClass()
                        .getResource(colorSchemeFileLocation)
                        .toExternalForm());
    }

    public void quit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to quit?");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }
    }
}

