package api.components.map;

import api.components.TransPoolController;
import com.fxgraph.graph.Graph;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import data.transpool.TransPoolData;
import data.transpool.time.TimeInterval;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import java.time.LocalTime;

public class MapController {

    private TransPoolController transpoolController;


    @FXML
    private JFXButton buttonPrevious;
    @FXML
    private JFXButton buttonNext;
    @FXML
    private JFXComboBox<TimeInterval> comboboxInterval;
    @FXML
    private Label labelCurrentDay;
    @FXML
    private Label labelCurrentTime;
    @FXML
    private StackPane mapStackPane;

    private Graph mapGraph;
    private ObjectProperty<TimeInterval> currentInteval;
    private ObjectProperty<LocalTime> currentTime;
    private IntegerProperty currentDay;

    public MapController() {
        currentInteval = new SimpleObjectProperty<>();
        currentTime = new SimpleObjectProperty<>(LocalTime.MIDNIGHT);
        currentDay = new SimpleIntegerProperty(0);
        mapGraph = new Graph();
        Platform.runLater(() -> {
            mapGraph.getUseViewportGestures().set(false);
            mapGraph.getUseNodeGestures().set(false);
        });
    }

    public void setTransPoolController(TransPoolController controller) {
        this.transpoolController = controller;
    }

    @FXML
    public void initialize() {
        comboboxInterval.getItems().addAll(
                TimeInterval.FIVE_MINUTES,
                TimeInterval.THIRTY_MINUTES,
                TimeInterval.ONE_HOUR,
                TimeInterval.TWO_HOURS,
                TimeInterval.ONE_DAY
        );
        comboboxInterval.setValue(TimeInterval.FIVE_MINUTES);
        currentInteval.bind(comboboxInterval.valueProperty());
        labelCurrentTime.textProperty().bind(currentTime.asString());
        labelCurrentDay.textProperty().bind(currentDay.asString());
        mapStackPane.getChildren().add(mapGraph.getCanvas());
        currentDay.set(1);
    }

    @FXML
    void nextButtonAction(ActionEvent event) {
        incrementTime();
    }

    @FXML
    void previousButtonAction(ActionEvent event) {
        decrementTime();
    }

    private void incrementTime() {
        transpoolController.incrementTime(currentInteval.get());
    }

    private void decrementTime() {
        transpoolController.decrementTime(currentInteval.get());
    }

    public Graph getMap() {
        return mapGraph;
    }

    public void bindaUIToData(TransPoolData data) {
        currentTime.bind(data.getTimeDay().timeProperty());
        currentDay.bind(data.getTimeDay().dayProperty());
    }
}
