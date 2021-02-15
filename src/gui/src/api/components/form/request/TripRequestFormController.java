package api.components.form.request;

import api.components.TransPoolController;
import api.components.form.Form;
import api.components.form.FormController;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import exception.data.TransPoolDataException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.time.LocalTime;

/**
 * A form controller for Trip Requests.
 */
public class TripRequestFormController extends FormController {

    @FXML private JFXTimePicker timeFieldTime;
    @FXML private JFXRadioButton radioButtonArrivalTime;
    @FXML private JFXRadioButton radioButtonDepartureTime;
    @FXML private JFXTextField textFieldSource;
    @FXML private JFXTextField textFieldRiderName;
    @FXML private JFXTextField textFieldDestination;
    @FXML private JFXButton buttonAddRequest;
    @FXML private JFXCheckBox checkBoxContinuousRide;
    @FXML private JFXTextField textFieldDay;


    @FXML
    public void initialize() {
        super.initialize();
        timeFieldTime.set24HourView(true);
        timeFieldTime.setValue(LocalTime.MIDNIGHT);
        buttonAddRequest.disableProperty().bind(fileLoaded.not());

        textFieldDay.textProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textFieldDay.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }));
        radioButtonArrivalTime.setDisable(true);
    }

    @FXML
    public void newTripRequestButtonAction(ActionEvent event) {
        transpoolController.submitForm(this);
    }

    /**
     * Rounds time to the nearest 5 minutes.
     */
    @FXML
    public void timeSelectedAction(ActionEvent event) {
        LocalTime accurateTime = timeFieldTime.getValue();
        LocalTime roundedTime = accurateTime.withSecond(0).withNano(0).plusMinutes((65-accurateTime.getMinute()) % 5);
        timeFieldTime.setValue(roundedTime);
    }

    @Override
    public void submit() {
        String source = textFieldSource.getText();
        String destination = textFieldDestination.getText();
        String riderName = textFieldRiderName.getText();
        LocalTime time = timeFieldTime.getValue();
        int day = Integer.parseInt(textFieldDay.getText());
        boolean isArrivalTime = radioButtonArrivalTime.isSelected();
        boolean isContinuous = checkBoxContinuousRide.isSelected();
        transpoolController.createNewTransPoolTripRequest(riderName, source, destination, day, time, isArrivalTime, isContinuous);
    }

    @Override
    public void clear() {
        timeFieldTime.setValue(LocalTime.now());
        radioButtonDepartureTime.setSelected(true);
        textFieldDestination.setText("");
        textFieldSource.setText("");
        textFieldRiderName.setText("");
    }

    @Override
    public void setValidations() {
        timeFieldTime.getValidators().add(requiredFieldValidator);
        textFieldSource.getValidators().add(requiredFieldValidator);
        textFieldDestination.getValidators().add(requiredFieldValidator);
        textFieldRiderName.getValidators().add(requiredFieldValidator);
        textFieldDay.getValidators().add(requiredFieldValidator);

        timeFieldTime.focusedProperty().addListener(
                (observable, oldValue, newValue) -> timeFieldTime.validate()
        );
        textFieldSource.focusedProperty().addListener(
                (observable, oldValue, newValue) -> textFieldSource.validate()
        );
        textFieldDestination.focusedProperty().addListener(
                (observable, oldValue, newValue) -> textFieldDestination.validate()
        );
        textFieldRiderName.focusedProperty().addListener(
                (observable, oldValue, newValue) -> textFieldRiderName.validate()
        );
        textFieldDay.focusedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    textFieldRiderName.validate();
                }
        );
    }

    @Override
    public boolean isValid() {
        return timeFieldTime.validate()
                && textFieldRiderName.validate()
                && textFieldDestination.validate()
                && textFieldSource.validate()
                && textFieldDay.validate();
    }

    public BooleanProperty fileLoadedProperty() {
        return fileLoaded;
    }
}
