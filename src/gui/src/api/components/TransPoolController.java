package api.components;

import logic.Engine;
import api.components.data.bar.DataBarController;
import api.components.form.Form;
import api.components.form.feedback.FeedbackFormController;
import api.components.map.MapController;
import api.components.menu.bar.MenuBarController;
import api.components.form.match.MatchTripFormController;
import api.components.form.offer.TripOfferFormController;
import api.components.form.request.TripRequestFormController;
import api.exception.RequiredFieldEmptyException;
import data.transpool.TransPoolData;
import data.transpool.time.TimeInterval;
import data.transpool.time.Recurrence;
import data.transpool.trip.request.TripRequest;
import data.transpool.user.Feedbackable;
import data.transpool.user.Feedbacker;
import exception.NoResultsFoundException;
import exception.TransPoolRunTimeException;
import exception.data.TransPoolDataException;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.util.concurrent.ExecutionException;


/**
    The main controller of the application.
 */
public class TransPoolController {

    private Stage primaryStage;
    private Engine engine;

    @FXML private MatchTripFormController matchTripComponentController;
    @FXML private MenuBarController menuBarComponentController;
    @FXML private TripOfferFormController tripOfferComponentController;
    @FXML private TripRequestFormController tripRequestComponentController;
    @FXML private DataBarController dataBarComponentController;
    @FXML private FeedbackFormController feedbackComponentController;
    @FXML private MapController mapComponentController;

    @FXML private MenuBar menuBarComponent;
    @FXML private AnchorPane matchTripComponent;
    @FXML private AnchorPane tripOfferComponent;
    @FXML private AnchorPane tripRequestComponent;
    @FXML private VBox dataBarComponent;
    @FXML private VBox mapComponent;

    private BooleanProperty fileLoaded;
    private StringProperty currentTaskProgress;

    public TransPoolController() {
        fileLoaded = new SimpleBooleanProperty(false);
        currentTaskProgress = new SimpleStringProperty("");
    }

    @FXML
    public void initialize() {
        //Setting the TransPool controllers for the components.
        if (matchTripComponentController != null
                && menuBarComponentController != null
                && tripOfferComponentController != null
                && tripRequestComponentController != null
                && dataBarComponentController != null
                && feedbackComponentController != null
                && mapComponentController != null) {
            matchTripComponentController.setTransPoolController(this);
            tripOfferComponentController.setTransPoolController(this);
            tripRequestComponentController.setTransPoolController(this);
            menuBarComponentController.setTransPoolController(this);
            dataBarComponentController.setTransPoolController(this);
            feedbackComponentController.setTransPoolController(this);
            mapComponentController.setTransPoolController(this);
        }

        //Binding the fileLoaded property for each relevant component.
        fileLoaded.bindBidirectional(menuBarComponentController.fileLoadedProperty());
        fileLoaded.bindBidirectional(matchTripComponentController.fileLoadedProperty());
        fileLoaded.bindBidirectional(tripOfferComponentController.fileLoadedProperty());
        fileLoaded.bindBidirectional(tripRequestComponentController.fileLoadedProperty());
        currentTaskProgress.bindBidirectional(dataBarComponentController.currentTaskProgressProperty());
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
        fileLoaded.bind(this.engine.fileLoadedProperty());
    }

    public void clearForm(Form form) {
        form.clear();
    }

    public void submitForm(Form form) {
        try {
            if (form.isValid()) {
                form.submit();
                form.clear();
            } else {
                throw new RequiredFieldEmptyException();
            }
        } catch(RequiredFieldEmptyException e) {
            showAlert(e);
        }
    }

    public void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                  int day, LocalTime time, boolean isArrivalTime, boolean isContinuous){
        try {
            engine.createNewTransPoolTripRequest(riderName, source, destination, day, time, isArrivalTime, isContinuous);
        } catch (TransPoolDataException e) {
            showAlert(e);
        }
    }

    public void createNewTripOffer(String driverName, LocalTime departureTime, int dayStart, Recurrence recurrences,
                                   int riderCapacity, int PPK, ObservableList<String> addedStops) {
        try {
            engine.createNewTripOffer(driverName, departureTime, dayStart, recurrences, riderCapacity, PPK, addedStops);
        } catch (TransPoolDataException e) {
            showAlert(e);
        }

    }

    public void createNewMatch(int possibleMatchIndex) {
        try {
            engine.addNewMatch(possibleMatchIndex);
        } catch (TransPoolDataException e) {
            showAlert(e);
        }
    }

    public void findPossibleMatches(TripRequest requestToMatch, int numOfResults) {
        try {
            engine.findPossibleMatches(requestToMatch, numOfResults);
        } catch (NoResultsFoundException | TransPoolDataException e) {
            showAlert(e);
        }
    }

    public void createNewFeedback(Feedbacker feedbacker, Feedbackable feedbackee, int rating, String comment) {
        try {
            engine.createNewFeedback(feedbacker, feedbackee, rating, comment);
        } catch (TransPoolRunTimeException e) {
            showAlert(e);
        }
    }

    //---------------------------------------------------------------------------------------------//

    public void loadFile() {
        try {
            menuBarComponentController.loadFile();
            if (fileLoaded.get()) {
                engine.createMap(mapComponentController.getMap());
            }
        } catch (ExecutionException | InterruptedException e) {
            showAlert("Execution interrupted!");
        }
    }


    public void setColorScheme(String colorSchemeFileLocation) {
        menuBarComponentController.setColorScheme(colorSchemeFileLocation);
    }

    public void quit() {
        menuBarComponentController.quit();
    }

    //---------------------------------------------------------------------------------------------//

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Engine getEngine() {
        return engine;
    }


    //---------------------------------------------------------------------------------------------//

    public boolean isFileLoaded() {
        return fileLoaded.get();
    }

    public void setFileLoaded(boolean value) {
        fileLoaded.set(value);
    }

    public BooleanProperty fileLoadedProperty() {
        return fileLoaded;
    }

    //---------------------------------------------------------------------------------------------//

    public void createMap() {
        engine.createMap(mapComponentController.getMap());
    }
    public void incrementTime(TimeInterval duration) {
        engine.incrementTime(duration);
    }

    public void decrementTime(TimeInterval duration) {
        engine.decrementTime(duration);
    }


    //-----------------------------------------------------------------------------------------------------------------//


    public void bindTaskToUI(Task currentRunningTask) {
        dataBarComponentController.bindTaskToUI(currentRunningTask);
    }

    public void bindUIToData(TransPoolData data) {
        dataBarComponentController.bindUIToData(data);
        matchTripComponentController.bindUIToData(data);
        tripOfferComponentController.bindDataToUI(data);
        feedbackComponentController.bindUIToData(data);
        mapComponentController.bindaUIToData(data);
    }

    public void updateCard() {
        dataBarComponentController.updateUI();
    }

    public void showAlert(Exception e) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR, e.getMessage());
        errorAlert.setHeaderText(null);
        errorAlert.showAndWait();
    }

    public void showAlert(String message) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR, message);
        errorAlert.setHeaderText(null);
        errorAlert.showAndWait();
    }
}
