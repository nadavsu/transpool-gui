package api.components.form.feedback;

import api.components.form.FormController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import data.transpool.TransPoolData;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.user.Feedbackable;
import data.transpool.user.Feedbacker;
import exception.NoResultsFoundException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.stream.Collectors;

/**
 * A form controller for the Feedbacks tab on the left hand side of the UI.
 */
public class FeedbackFormController extends FormController {

    @FXML private JFXComboBox<MatchedTripRequest> comboBoxRideID;
    @FXML private JFXButton buttonShowDrivers;
    @FXML private JFXTextArea textAreaComment;
    @FXML private JFXListView<Feedbackable> listViewDrivers;
    @FXML private JFXComboBox<Integer> comboboxRating;
    @FXML private JFXButton buttonAddFeedback;

    private BooleanProperty foundResults;
    private Feedbacker feedbacker;

    public FeedbackFormController() {
        foundResults = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize() {
        super.initialize();
        comboboxRating.getItems().addAll(1, 2, 3, 4, 5);
        buttonAddFeedback.disableProperty().bind(foundResults.not());
    }

    @FXML
    public void searchDriversButtonAction(ActionEvent event) {
        showAllFeedbackables();
    }

    @FXML
    public void createNewFeedbackButtonAction(ActionEvent event) {
        transpoolController.submitForm(this);
    }

    @Override
    public void clear() {
        textAreaComment.clear();
        comboboxRating.setValue(5);
        feedbacker = null;
        foundResults.set(false);

    }

    @Override
    public void setValidations() {

    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void submit() {
        Feedbackable feedbackee = listViewDrivers.getSelectionModel().getSelectedItem();
        String comment = textAreaComment.getText();
        int rating = comboboxRating.getValue();

        transpoolController.createNewFeedback(feedbacker, feedbackee, rating, comment);
    }

    public void showAllFeedbackables() {
        feedbacker = comboBoxRideID.getValue();
        listViewDrivers.setItems(feedbacker.getAllFeedbackables());
        foundResults.set(true);
    }

    public void bindUIToData(TransPoolData data) {
        comboBoxRideID.setItems(transpoolController.getEngine().getAllMatchedTripRequests());
    }
}
