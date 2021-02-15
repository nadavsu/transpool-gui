package logic.components;

import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.request.TripRequest;
import javafx.collections.ObservableList;

/**
 * THe engine interface that controls the trip requests.
 */
public interface TripRequestEngine {
    TripRequest getTripRequest(int ID);

    void deleteTripRequest(TripRequest requestToDelete);

    void addTripRequest(TripRequest tripRequest);

    void addMatchedRequest(MatchedTripRequest matchedTripRequest);

    ObservableList<TripRequest> getAllTripRequests();

    ObservableList<MatchedTripRequest> getAllMatchedTripRequests();

    MatchedTripRequest getMatchedTripRequest(int feedbackerID);
}
