package data.transpool.trip.offer.data;

import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.TimeDay;
import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.request.MatchedTripRequestPart;
import javafx.beans.property.DoubleProperty;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;


public interface TripOffer extends BasicTripOffer {
    double getAverageRating();
    DoubleProperty averageRatingProperty();

    ObservableList<MatchedTripRequestPart> getAllMatchedRequestsData();

    List<Path> getUsedPaths();
    Map<Stop, TimeDay> getTimeTable();
    List<SubTripOffer> getRoute();
    ObservableList<String> getRouteAsStopsList();

    void updateAfterMatch(MatchedTripRequest matchedRequest, TimedSubTripOffer subTripOffer);

    TimeDay getDepartureTimeAtStop(Stop stop);

    SubTripOffer getSubTripOffer(int subTripOfferID);

    boolean isCurrentlyHappening();

    SubTripOffer getCurrentSubTripOffer();


}
