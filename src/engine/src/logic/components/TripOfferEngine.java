package logic.components;

import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.request.MatchedTripRequest;
import javafx.collections.ObservableList;

/**
 * The engine interface responsible for controlling the trip offers.
 */
public interface TripOfferEngine {

    TripOffer getTripOffer(int ID);

    void addTripOffer(TripOffer tripOffer);

    ObservableList<TripOffer> getAllTripOffers();

    ObservableList<TripOffer> getCurrentOffers();


}
