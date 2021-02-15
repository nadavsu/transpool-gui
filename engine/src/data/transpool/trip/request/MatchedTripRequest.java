package data.transpool.trip.request;

import data.transpool.time.TimeDay;
import data.transpool.trip.offer.data.TimedSubTripOffer;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.user.Feedback;
import data.transpool.user.Feedbackable;
import data.transpool.user.Feedbacker;
import data.transpool.user.TransPoolDriver;
import exception.data.RideFullException;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

public class MatchedTripRequest extends BasicTripRequestData implements Feedbacker {


    private ObservableList<TimedSubTripOffer> route;
    private ObservableSet<Integer> tripOfferIDs;
    private ObservableSet<TransPoolDriver> transpoolDrivers;


    private IntegerProperty tripPrice;
    private DoubleProperty personalFuelConsumption;
    private ObjectProperty<TimeDay> expectedTimeOfArrival;
    private ObjectProperty<TimeDay> timeOfDeparture;
    private BooleanProperty isArrival;

    public MatchedTripRequest(TripRequest tripRequestToMatch, PossibleRoute possibleRoute) throws RideFullException {
        super(tripRequestToMatch);
        this.isArrival = new SimpleBooleanProperty(tripRequestToMatch.isTimeOfArrival());
        this.route = FXCollections.observableArrayList(possibleRoute.getRoute());
        this.tripPrice = new SimpleIntegerProperty(possibleRoute.getTotalPrice());
        this.expectedTimeOfArrival = new SimpleObjectProperty<>(possibleRoute.getTimeOfArrival());
        this.timeOfDeparture = new SimpleObjectProperty<>(possibleRoute.getTimeOfDeparture());
        this.personalFuelConsumption = new SimpleDoubleProperty(possibleRoute.getAverageFuelConsumption());
        this.tripOfferIDs = FXCollections.observableSet();
        this.transpoolDrivers = FXCollections.observableSet();

        possibleRoute.getRoute().forEach(subTripOffer -> {
            tripOfferIDs.add(subTripOffer.getSubTripOffer().getOfferID());
            transpoolDrivers.add(subTripOffer.getSubTripOffer().getTransPoolDriver());
            subTripOffer.getSubTripOffer().getMainOffer().updateAfterMatch(this, subTripOffer);
        });
        updateSubTripOffers();
    }

    private void updateSubTripOffers() throws RideFullException {
        for (TimedSubTripOffer timedOffer : route) {
            timedOffer.updateFather(timedOffer.getDay(), this);
        }
    }

    @Override
    public void leaveFeedback(Feedbackable feedbackable, Feedback feedback) {
        feedbackable.addFeedback(feedback);
    }

    @Override
    public ObservableList<Feedbackable> getAllFeedbackables() {
        ObservableList<Feedbackable> feedbackables = FXCollections.observableArrayList();
        feedbackables.addAll(transpoolDrivers);
        return feedbackables;
    }

    @Override
    public String getFeedbackerName() {
        return getTransPoolRider().getUsername();
    }

    @Override
    public int getFeedbackerID() {
        return getTransPoolRider().getID();
    }

    public boolean isTimeOfArrival() {
        return isArrival.get();
    }

    public BooleanProperty isArrivalProperty() {
        return isArrival;
    }

    public ObservableList<TimedSubTripOffer> getRoute() {
        return route;
    }

    public ObservableSet<Integer> getTripOfferIDs() {
        return tripOfferIDs;
    }

    public ObservableSet<TransPoolDriver> getTransPoolDrivers() {
        return transpoolDrivers;
    }


    public int getTripPrice() {
        return tripPrice.get();
    }

    public IntegerProperty tripPriceProperty() {
        return tripPrice;
    }


    public double getPersonalFuelConsumption() {
        return personalFuelConsumption.get();
    }

    public DoubleProperty personalFuelConsumptionProperty() {
        return personalFuelConsumption;
    }

    public TimeDay getExpectedTimeOfArrival() {
        return expectedTimeOfArrival.get();
    }

    public ObjectProperty<TimeDay> expectedTimeOfArrivalProperty() {
        return expectedTimeOfArrival;
    }

    public TimeDay getTimeOfDeparture() {
        return timeOfDeparture.get();
    }

    public ObjectProperty<TimeDay> timeOfDepartureProperty() {
        return timeOfDeparture;
    }

    @Override
    public String toString() {
        return super.toString();
        /*String matchedTripString = "";
        matchedTripString += "------Matched Trip------\n";
        matchedTripString += "Request ID: " + getRequestID() + "\n";
        matchedTripString += "Name of rider: " + getTransPoolRider() + "\n";
        matchedTripString += "Source stop: " + getSourceStop() + "\n";
        matchedTripString += "Destination stop: " + getDestinationStop() + "\n";
        matchedTripString += "Matched trip ID: " + getTripOfferID() + "\n";
        matchedTripString += "Name of driver: " + getTransPoolDriver() + "\n";
        matchedTripString += "Price of trip: " + getTripPrice() + "\n";
        matchedTripString += "Expected time of arrival: " + getExpectedTimeOfArrival() + "\n";
        matchedTripString += "Personal fuel consumption: " + getPersonalFuelConsumption() + "\n";

        return matchedTripString;*/
    }
}
