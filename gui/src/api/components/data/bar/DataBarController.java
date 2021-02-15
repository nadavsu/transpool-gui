package api.components.data.bar;

import api.components.TransPoolController;
import api.components.card.match.MatchedTripCardController;
import api.components.card.offer.TripOfferCardController;
import api.components.card.request.TripRequestCardController;
import data.transpool.TransPoolData;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.request.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * The controller for the right hand side bar of the UI. Holds the lists containing cards.
 */
public class DataBarController {

    private TransPoolController transPoolController;

    @FXML private Label labelTaskProgress;
    @FXML private ListView<TripRequest> listViewTripRequests;
    @FXML private ListView<TripOffer> listViewTripOffers;
    @FXML private ListView<MatchedTripRequest> listViewMatchedTrips;
    @FXML private ListView<TripOffer> listViewCurrentOffers;

    private StringProperty currentTaskProgress;


    public DataBarController() {
        currentTaskProgress = new SimpleStringProperty();
    }

    public void setTransPoolController(TransPoolController transPoolController) {
        this.transPoolController = transPoolController;
    }

    @FXML
    public void initialize() {
        labelTaskProgress.textProperty().bind(currentTaskProgress);
    }

    public StringProperty currentTaskProgressProperty() {
        return currentTaskProgress;
    }

    public void bindTaskToUI(Task theTask) {
        if (currentTaskProgress.isBound()) {
            currentTaskProgress.unbind();
        }
        currentTaskProgress.bind(theTask.messageProperty());
    }

    public void bindUIToData(TransPoolData data) {
        listViewTripOffers.setItems(data.getAllTripOffers());
        listViewTripOffers.setCellFactory(offerListView -> new TripOfferCardController());
        listViewTripRequests.setItems(data.getAllTripRequests());
        listViewTripRequests.setCellFactory(requestListView -> new TripRequestCardController());
        listViewMatchedTrips.setItems(data.getAllMatchedTripRequests());
        listViewMatchedTrips.setCellFactory(listViewMatchedTrips -> new MatchedTripCardController());
        listViewCurrentOffers.setItems(data.getCurrentOffers());
        listViewCurrentOffers.setCellFactory(list -> new TripOfferCardController());
    }

    public void updateUI() {
        listViewTripOffers.setItems(transPoolController.getEngine().getAllTripOffers());
        listViewTripOffers.setCellFactory(offerListView -> new TripOfferCardController());
    }

}

