package data.transpool.trip.request;

import data.transpool.trip.offer.data.TimedSubTripOffer;
import data.transpool.user.TransPoolRider;

/**
 * Used for displaying who's ridin with who in which SubTripOffer.
 */
public class MatchedTripRequestPart extends TimedSubTripOffer {

   private TransPoolRider rider;

    public MatchedTripRequestPart(TransPoolRider rider, TimedSubTripOffer timedSubTripOffer) {
        super(timedSubTripOffer);
        this.rider = rider;
    }

    @Override
    public String toString() {
        return rider + " gets on at " + getSubTripOffer().getSourceStop() +
                " at " + getDepartureTime() +
                " and gets off at " + getSubTripOffer().getDestinationStop() +
                " at " + getArrivalTime();
    }
}
