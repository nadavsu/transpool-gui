package api.components.card.request;
import api.Constants;
import api.components.card.CardController;
import data.transpool.trip.request.TripRequest;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * A card controller for TripRequests.
 */
public class TripRequestCardController extends CardController<TripRequest> {

    @FXML private Label labelRiderName;
    @FXML private Label labelRequestID;
    @FXML private Label labelRequestSource;
    @FXML private Label labelRequestDestination;
    @FXML private Label labelArrivalDeparture;
    @FXML private Label labelTime;

    @Override
    protected void loadCard() {
        loader = new FXMLLoader(Constants.TRIP_REQUEST_CARD_RESOURCE);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initializeValues(TripRequest request) {
        labelRequestID.textProperty().bind(request.requestIDProperty().asString());
        labelRiderName.textProperty().bind(request.getTransPoolRider().usernameProperty());
        labelRequestSource.textProperty().bind(Bindings.concat("Gets on at ", request.sourceStopProperty()));
        labelRequestDestination.textProperty().bind(Bindings.concat("Gets off at ", request.destinationStopProperty()));
        labelTime.textProperty().bind(request.requestTimeProperty().asString());
        labelArrivalDeparture.textProperty().bind(
                Bindings
                        .when(request.isTimeOfArrivalProperty())
                        .then("Requested time of arrival ")
                        .otherwise("Requested time of departure ")
        );
    }
}

