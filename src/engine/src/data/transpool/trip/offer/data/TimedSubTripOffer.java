package data.transpool.trip.offer.data;

import data.transpool.time.TimeDay;
import data.transpool.trip.request.BasicTripRequest;
import exception.data.RideFullException;

/**
 * A sub trip offer with a timestamp. THis class is used for the matching, and searching for relevant routes.
 */

public class TimedSubTripOffer {
    private SubTripOffer subTripOffer;
    private TimeDay departureTime;
    private TimeDay arrivalTime;

    public TimedSubTripOffer(TimeDay departureTime, TimeDay arrivalTime, SubTripOffer subTripOffer) {
        this.subTripOffer = subTripOffer;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public TimedSubTripOffer(TimedSubTripOffer other) {
        this.subTripOffer = other.subTripOffer;
        this.departureTime = other.departureTime;
        this.arrivalTime = other.arrivalTime;
    }

    public void updateFather(int day, BasicTripRequest matchedRequest) throws RideFullException {
        subTripOffer.addRiderOnDay(day, matchedRequest);
    }

    public TimeDay getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(TimeDay departureTime) {
        this.departureTime = departureTime;
    }

    public TimeDay getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(TimeDay arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getDay() {
        return arrivalTime.getDay();
    }

    public SubTripOffer getSubTripOffer() {
        return subTripOffer;
    }

    @Override
    public String toString() {
        return "Depart from " + subTripOffer.getSourceStop() +
                " with "  +  subTripOffer.getTransPoolDriver() +
                " at " + departureTime +
                " and arrive on " + arrivalTime +
                " at " + subTripOffer.getDestinationStop();
    }
}
