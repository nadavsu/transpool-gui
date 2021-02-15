package api.components.card.match;

import api.Constants;
import api.components.card.CardController;
import com.jfoenix.controls.JFXListView;
import data.transpool.trip.offer.data.TimedSubTripOffer;
import data.transpool.trip.request.MatchedTripRequest;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * A Card controller for MatchedTripRequests.
 */
public class MatchedTripCardController extends CardController<MatchedTripRequest> {

    @FXML private Label labelRiderName;
    @FXML private Label labelRequestID;
    @FXML private Label labelTripPrice;
    @FXML private Label labelPersonalFuelConsumption;
    @FXML private Label labelTime;
    @FXML private JFXListView<TimedSubTripOffer> listViewRideDetails;
    @FXML private AnchorPane anchorPaneCardBody;

    @Override
    protected void initializeValues(MatchedTripRequest request) {
        listViewRideDetails.setItems(request.getRoute());
        labelRequestID.textProperty().bind(request.requestIDProperty().asString());
        labelRiderName.textProperty().bind(request.getTransPoolRider().usernameProperty());

        labelTripPrice.textProperty().bind(Bindings.concat(
                "Trip price: ", request.getTripPrice()));
        labelPersonalFuelConsumption.textProperty().bind(Bindings.concat(
                "By travelling by TransPool, you have saved ", request.getPersonalFuelConsumption()
                , " litres of fuel!"));
        labelTime.textProperty().bind(Bindings
                .when(request.isArrivalProperty())
                .then(Bindings.concat("Depart at ", request.getTimeOfDeparture()))
                .otherwise(Bindings.concat("Arrive by ", request.getExpectedTimeOfArrival()))
        );
    }

    @Override
    protected void loadCard() {
        loader = new FXMLLoader(Constants.MATCHED_TRIP_CARD_RESOURCE);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}