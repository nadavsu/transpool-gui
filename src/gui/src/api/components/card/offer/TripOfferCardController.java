package api.components.card.offer;

import api.Constants;
import api.components.card.CardController;
import api.components.card.feedback.FeedbackCardController;
import com.jfoenix.controls.JFXListView;
import data.transpool.trip.request.MatchedTripRequestPart;
import data.transpool.user.Feedback;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.request.BasicTripRequest;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * A card controller for TripOffers.
 */
public class TripOfferCardController extends CardController<TripOffer> {

    @FXML private Label labelDriverName;
    @FXML private Label labelDriverRating;
    @FXML private Label labelOfferID;
    @FXML private Label labelSchedule;
    @FXML private JFXListView<String> listViewStops;
    @FXML private Label labelTripDuration;
    @FXML private Label labelFuelConsumption;
    @FXML private Label labelPPK;
    @FXML private JFXListView<MatchedTripRequestPart> listViewRiderDetails;
    @FXML private Label labelPassengerCapacity;
    @FXML private JFXListView<Feedback> listViewFeedbacks;
    @FXML private AnchorPane anchorPaneCardBody;

    public void loadCard() {
        loader = new FXMLLoader(Constants.TRIP_OFFER_CARD_RESOURCE);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initializeValues(TripOffer tripOffer) {
        labelDriverName.textProperty().bind(tripOffer.getTransPoolDriver().usernameProperty());
        labelOfferID.textProperty().bind(tripOffer.offerIDProperty().asString());

        listViewStops.setItems(tripOffer.getRouteAsStopsList());
        listViewRiderDetails.setItems(tripOffer.getAllMatchedRequestsData());

        listViewFeedbacks.setItems(tripOffer.getTransPoolDriver().getAllFeedbacks());

        labelPassengerCapacity.textProperty().bind(Bindings.concat(
                "There are ", tripOffer.maxPassengerCapacityProperty(), " spaces on this ride."));
        labelTripDuration.textProperty().bind(Bindings.concat(
                "Trip is about ", tripOffer.tripDurationInMinutesProperty(), " minutes long."));
        labelFuelConsumption.textProperty().bind(Bindings.concat(
                "Average fuel consumption: ", tripOffer.averageFuelConsumptionProperty()));
        labelPPK.textProperty().bind(Bindings.concat(
                "PPK: ", tripOffer.PPKProperty()));
        labelSchedule.textProperty().bind(Bindings.concat(
                "Departs ", tripOffer.getScheduling().getRecurrences(), " ", tripOffer.getScheduling().getDepartureTime()));
        labelDriverRating.textProperty().bind(Bindings
                .when(tripOffer.getTransPoolDriver().averageRatingProperty().isEqualTo(0))
                .then("No rating yet.")
                .otherwise(Bindings.concat("Rating: ", tripOffer.getTransPoolDriver().averageRatingProperty())));

    }
}
