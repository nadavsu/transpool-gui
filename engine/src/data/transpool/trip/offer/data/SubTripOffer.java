package data.transpool.trip.offer.data;

import data.transpool.TransPoolData;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.Scheduling;
import data.transpool.time.TimeDay;
import data.transpool.time.Recurrence;
import data.transpool.trip.request.BasicTripRequest;
import exception.data.RideFullException;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Contains the data of a part of a trip offer (made from a single path)
 * dayToDetailsMap - contains the details of the matched trips on the relevant days.
 */
public class SubTripOffer extends BasicTripOfferData {
    private TripOffer mainOffer;
    private int subTripOfferID;

    private ObjectProperty<Stop> sourceStop;
    private ObjectProperty<Stop> destinationStop;

    private Map<Integer, SubTripOfferDetails> dayToDetailsMap;

    public SubTripOffer(int ID, Path path, TripOffer tripOffer) {
        super(tripOffer);
        this.mainOffer = tripOffer;
        this.subTripOfferID = ID;
        this.sourceStop = new SimpleObjectProperty<>(path.getSourceStop());
        this.destinationStop = new SimpleObjectProperty<>(path.getDestinationStop());

        this.tripPrice = new SimpleIntegerProperty(path.getLength() * PPK.get());
        this.averageFuelConsumption = new SimpleDoubleProperty(path.getFuelConsumption());
        this.tripDurationInMinutes = new SimpleIntegerProperty(path.getPathTime());
        this.dayToDetailsMap = new HashMap<>();
        this.schedule = new SimpleObjectProperty<>(
                new Scheduling(
                        tripOffer.getDepartureTimeAtStop(sourceStop.get()),
                        tripOffer.getDepartureTimeAtStop(destinationStop.get()),
                        tripOffer.getScheduling().getRecurrences()
                )
        );
    }


    public int getSubTripOfferID() {
        return subTripOfferID;
    }

    public Stop getSourceStop() {
        return sourceStop.get();
    }

    public Stop getDestinationStop() {
        return destinationStop.get();
    }


    /**
     * Adds the relevant details on day 'day'.
     * @param day - The day to add the details.
     * @param matchedTrip - The matchedTrip to create the details from.
     * @throws RideFullException - Thrown if the ride is full and you can't add a rider on that day.
     */
    public void addRiderOnDay(int day, BasicTripRequest matchedTrip) throws RideFullException {
        if (dayToDetailsMap.get(day) != null) {
            dayToDetailsMap.get(day).addRider(matchedTrip);
        } else {
            dayToDetailsMap.put(day, new SubTripOfferDetails(this, matchedTrip));
        }
    }

    public TimeDay getTimeOfArrivalAtDestination() {
        return schedule.get().getDepartureTime();
    }

    public TimeDay getTimeOfDepartureFromSource() {
        return schedule.get().getDepartureTime();
    }

    public int getDayStart() {
        return schedule.get().getDayStart();
    }

    public Recurrence getRecurrences() {
        return schedule.get().getRecurrences();
    }

    public TripOffer getMainOffer() {
        return mainOffer;
    }

    public boolean isCurrentlyHappening() {
        return schedule.get().isCurrentlyHappening();
    }

    public boolean isCurrentlyDeparting() {
        return schedule.get().isCurrentlyDeparting();
    }

    public boolean isCurrentlyArriving() {
        return schedule.get().isCurrentlyArriving();
    }

    public Scheduling getFirstRecurrenceAfter(TimeDay timeDay) {
        return Scheduling.getFirstRecurrenceAfter(getScheduling(), timeDay);
    }


    /**
     * @return - a string of the details happening currently. Uses TransPoolData.currentTime.
     * Used for displaying who is staying at which station currently.
     */
    public String currentDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append("Driver: ").append(getTransPoolDriver().toString()).append("\n");
        if (dayToDetailsMap.get(TransPoolData.currentTime.getDay()) != null) {
            builder.append(dayToDetailsMap.get(TransPoolData.currentTime.getDay()));
            builder.append("\n\n");
        } else {
            builder.append("Riding alone.\n\n");
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "Depart from " + getSourceStop() +
                " with " + getTransPoolDriver() +
                " and arrive at " + getDestinationStop();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubTripOffer)) return false;
        SubTripOffer that = (SubTripOffer) o;
        return subTripOfferID == that.subTripOfferID &&
                sourceStop.equals(that.sourceStop) &&
                destinationStop.equals(that.destinationStop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subTripOfferID, sourceStop, destinationStop);
    }
}
