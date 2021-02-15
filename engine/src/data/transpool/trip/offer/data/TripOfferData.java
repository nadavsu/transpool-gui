package data.transpool.trip.offer.data;

import data.transpool.TransPoolData;
import data.transpool.map.BasicMap;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.Scheduling;
import data.transpool.time.TimeDay;
import data.transpool.time.Recurrence;
import data.transpool.trip.request.BasicTripRequest;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.request.MatchedTripRequestPart;
import exception.TransPoolRunTimeException;
import exception.data.PathDoesNotExistException;
import exception.data.TransPoolDataException;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalTime;
import java.util.*;

/**
 * Contains the static data for a TransPool trip offered by TransPool drivers.
 */
public class TripOfferData extends BasicTripOfferData implements TripOffer {

    private List<SubTripOffer> route;

    private ObservableList<MatchedTripRequestPart> allMatchedRequestsData;
    private Map<Stop, TimeDay> timeTable;
    private List<Path> usedPaths;

    public TripOfferData(BasicMap map, String driverName, LocalTime departureTime, int dayStart, Recurrence recurrences, int passengerCapacity, int PPK, ObservableList<String> route) throws TransPoolDataException {
        super(driverName, PPK, passengerCapacity);
        this.allMatchedRequestsData = FXCollections.observableArrayList();
        this.route = new ArrayList<>();
        this.timeTable = new HashMap<>();
        this.usedPaths = new ArrayList<>();

        initializeUsedPaths(route, map);

        //Order matters.
        this.tripPrice.set(calculatePriceOfRoute(this.PPK.get()));
        this.tripDurationInMinutes.set(calculateTripDuration());
        this.averageFuelConsumption.set(calculateAverageFuelConsumption());
        this.schedule.set(new Scheduling(dayStart, departureTime, tripDurationInMinutes.get(), recurrences));

        //Order matters.
        initializeTimeTable();
        initializeSubTripOffers();
    }

    public TripOfferData(data.jaxb.TransPoolTrip JAXBTransPoolTrip, BasicMap map) throws TransPoolDataException {
        super(JAXBTransPoolTrip);
        this.allMatchedRequestsData = FXCollections.observableArrayList();
        this.route = new ArrayList<>();
        this.usedPaths = new ArrayList<>();
        this.timeTable = new HashMap<>();

        String[] routeArray = JAXBTransPoolTrip.getRoute().getPath().split(",");
        List<String> JAXBRoute = Arrays.asList(routeArray);
        initializeUsedPaths(JAXBRoute, map);

        //Order matters.
        this.tripPrice.set(calculatePriceOfRoute(this.PPK.get()));
        this.tripDurationInMinutes.set(calculateTripDuration());
        this.averageFuelConsumption.set(calculateAverageFuelConsumption());
        this.schedule.set(new Scheduling(JAXBTransPoolTrip.getScheduling(), tripDurationInMinutes.get()));

        //Order matters.
        initializeTimeTable();
        initializeSubTripOffers();
    }

    private void initializeTimeTable() {
        TimeDay timeAtStop = new TimeDay(getScheduling().getDepartureTime());
        Path firstPath = usedPaths.get(0);

        timeTable.put(firstPath.getSourceStop(), new TimeDay(timeAtStop));
        timeAtStop.plus(firstPath.getPathTime());

        for (Path path : usedPaths) {
            timeTable.put(path.getDestinationStop(), new TimeDay(timeAtStop));
            timeAtStop.plus(path.getPathTime());
        }
    }

    private void initializeSubTripOffers() {
        for (int i = 0; i < usedPaths.size(); i++) {
            route.add(new SubTripOffer(i, usedPaths.get(i), this));
        }
    }


    private void initializeUsedPaths(List<String> route, BasicMap map) throws PathDoesNotExistException {
        for (int i = 0; i < route.size() - 1; i++) {
            Path foundPath = map.getPath(route.get(i).trim(), route.get(i + 1).trim());
            if (foundPath == null) {
                throw new PathDoesNotExistException(route.get(i).trim(), route.get(i + 1).trim());
            }
            usedPaths.add(new Path(foundPath));
        }
    }

    private int calculatePriceOfRoute(int PPK) {
        return this
                .getUsedPaths()
                .stream()
                .mapToInt(p -> p.getLength() * PPK)
                .sum();
    }

    private int calculateTripDuration() {
        return this
                .getUsedPaths()
                .stream()
                .mapToInt(Path::getPathTime)
                .sum();
    }

    private double calculateAverageFuelConsumption() {
        return this
                .getUsedPaths()
                .stream()
                .mapToDouble(Path::getFuelConsumption)
                .average()
                .orElse(0);
    }

    @Override
    public SubTripOffer getSubTripOffer(int ID) {
        return route
                .stream()
                .filter(subTripOffer -> subTripOffer.getSubTripOfferID() == ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean isCurrentlyHappening() {
        for (SubTripOffer subTripOffer : route) {
            if (subTripOffer.isCurrentlyHappening()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SubTripOffer getCurrentSubTripOffer() {
        for (SubTripOffer subTripOffer : route) {
            if (subTripOffer.isCurrentlyDeparting()) {
                return subTripOffer;
            }
        }
        if (route.get(route.size() - 1).isCurrentlyArriving()) {
            return route.get(route.size() - 1);
        }
        return null;
    }

    @Override
    public TimeDay getDepartureTimeAtStop(Stop stop) {
        return timeTable.get(stop);
    }

    @Override
    public void updateAfterMatch(MatchedTripRequest matchedRequest, TimedSubTripOffer subTripOffer) {
        allMatchedRequestsData.add(new MatchedTripRequestPart(matchedRequest.getTransPoolRider(), subTripOffer));
    }



    @Override
    public ObservableList<String> getRouteAsStopsList() {
        ObservableList<String> stopNamesList = FXCollections.observableArrayList();
        stopNamesList.add(route.get(0).getSourceStop().getName());
        route.forEach(subTripOffer -> stopNamesList.add(subTripOffer.getDestinationStop().getName()));
        return stopNamesList;
    }

    @Override
    public double getAverageRating() {
        return transpoolDriver.get().getAverageRating();
    }

    @Override
    public DoubleProperty averageRatingProperty() {
        return transpoolDriver.get().averageRatingProperty();
    }

    @Override
    public ObservableList<MatchedTripRequestPart> getAllMatchedRequestsData() {
        return allMatchedRequestsData;
    }

    @Override
    public List<SubTripOffer> getRoute() {
        return route;
    }

    @Override
    public Map<Stop, TimeDay> getTimeTable() {
        return timeTable;
    }

    @Override
    public List<Path> getUsedPaths() {
        return usedPaths;
    }

    @Override
    public String toString() {
        return transpoolDriver.get() + " - " + offerID.get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripOfferData)) return false;
        TripOfferData that = (TripOfferData) o;
        return offerID.get() == that.offerID.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(offerID.get());
    }
}