package data.transpool;

import logic.components.TripOfferEngine;
import logic.components.TripRequestEngine;
import com.fxgraph.graph.Graph;
import data.jaxb.TransPool;
import data.transpool.map.BasicMap;
import data.transpool.map.component.MapGraphModel;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import data.transpool.time.TimeDay;
import data.transpool.time.TimeEngine;
import data.transpool.time.TimeInterval;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.offer.matching.PossibleRoutesList;
import data.transpool.trip.offer.map.TripOfferMap;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.request.TripRequest;
import exception.data.TransPoolDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * The main class which holds the model of the system.
 * MapGraphModel - holds the live map model and the basic map data.
 * TripOfferMap - holds the trip offer data as a map.
 * Holds the current time of the system.
 */
public class TransPoolData implements TripRequestEngine, BasicMap, TripOfferEngine, TimeEngine {


    private MapGraphModel map;
    private TripOfferMap tripOffers;
    private ObservableList<TripRequest> allTripRequests;
    private ObservableList<MatchedTripRequest> allMatchedTripRequests;

    public static TimeDay currentTime;

    public TransPoolData(TransPool JAXBData) throws TransPoolDataException {
        Stop.resetIDGenerator();
        currentTime = new TimeDay();
        this.allTripRequests = FXCollections.observableArrayList();
        this.allMatchedTripRequests = FXCollections.observableArrayList();
        this.map = new MapGraphModel(JAXBData.getMapDescriptor());
        this.tripOffers = new TripOfferMap(map, JAXBData.getPlannedTrips().getTransPoolTrip());

    }

    @Override
    public TripOffer getTripOffer(int ID) {
        return tripOffers.getTripOffer(ID);
    }

    @Override
    public void addTripOffer(TripOffer tripOffer) {
        tripOffers.addTripOffer(tripOffer);
    }

    @Override
    public ObservableList<TripOffer> getAllTripOffers() {
        return tripOffers.getAllTripOffers();
    }

    @Override
    public ObservableList<TripOffer> getCurrentOffers() {
        return tripOffers.getCurrentOffers();
    }

    @Override
    public TripRequest getTripRequest(int ID) {
        return allTripRequests
                .stream()
                .filter(t -> t.getRequestID() == ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public MatchedTripRequest getMatchedTripRequest(int feedbackerID) {
        return allMatchedTripRequests
                .stream()
                .filter(m -> m.getRequestID() == feedbackerID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void deleteTripRequest(TripRequest requestToDelete) {
        allTripRequests.remove(requestToDelete);
    }

    @Override
    public void addTripRequest(TripRequest tripRequest) {
        allTripRequests.add(tripRequest);
    }

    @Override
    public void addMatchedRequest(MatchedTripRequest matchedTripRequest) {
        allMatchedTripRequests.add(matchedTripRequest);
        deleteTripRequest(getTripRequest(matchedTripRequest.getRequestID()));
    }

    @Override
    public ObservableList<TripRequest> getAllTripRequests() {
        return allTripRequests;
    }

    @Override
    public ObservableList<MatchedTripRequest> getAllMatchedTripRequests() {
        return allMatchedTripRequests;
    }

    @Override
    public int getMapWidth() {
        return map.getMapWidth();
    }

    @Override
    public int getMapLength() {
        return map.getMapLength();
    }

    @Override
    public boolean containsStop(String stopName) {
        return map.containsStop(stopName);
    }

    @Override
    public Map<String, Stop> getAllStops() {
        return map.getAllStops();
    }

    @Override
    public List<Stop> getAllStopsAsList() {
        return map.getAllStopsAsList();
    }

    @Override
    public Stop getStop(String stopName) {
        return map.getStop(stopName);
    }

    @Override
    public int getNumberOfStops() {
        return map.getNumberOfStops();
    }

    @Override
    public List<Path> getAllPaths() {
        return map.getAllPaths();
    }

    @Override
    public boolean containsPath(String source, String destination) {
        return map.containsPath(source, destination);
    }

    @Override
    public Path getPath(Stop source, Stop destination) {
        return map.getPath(source, destination);
    }

    @Override
    public Path getPath(String source, String destination) {
        return map.getPath(source, destination);
    }

    @Override
    public void incrementTime(TimeInterval interval) {
        currentTime.plus(interval.getMinutes());
        map.update();
        tripOffers.update();

    }

    @Override
    public void decrementTime(TimeInterval interval) {
        currentTime.minus(interval.getMinutes());
        map.update();
        tripOffers.update();
    }

    @Override
    public TimeDay getTimeDay() {
        return currentTime;
    }

    public void createMap(Graph graph) {
        map.createMapModel(graph);
    }


    /**
     * Gets the possible routes from the TripOfferMap, and filters all routes which are not relevant by
     * departure or arrival time. Also filters all rides that are not continuous if the rider asked for continuous rides.
     * @param tripRequest
     * @param maximumMatches
     * @return
     */
    public PossibleRoutesList getAllPossibleRoutes(TripRequest tripRequest, int maximumMatches) {

        Predicate<PossibleRoute> timeMatchPredicate = possibleRoute -> {
            if (tripRequest.isTimeOfArrival()) {
                return possibleRoute.getTimeOfArrival().equals(tripRequest.getRequestTime());
            } else {
                return possibleRoute.getTimeOfDeparture().equals(tripRequest.getRequestTime());
            }
        };

        Predicate<PossibleRoute> continuousRidePredicate = possibleRoute ->
                !tripRequest.isContinuous() || possibleRoute.isContinuous();

        PossibleRoutesList possibleRoutes = tripOffers.getAllPossibleRoutes(tripRequest.getSourceStop(), tripRequest.getDestinationStop(), tripRequest.getRequestTime());
        return possibleRoutes.stream()
                .filter(timeMatchPredicate)
                .filter(continuousRidePredicate)
                .limit(maximumMatches)
                .collect(Collectors.toCollection(PossibleRoutesList::new));

    }

    public PossibleRoutesList getAllPossibleRoutes(Stop source, Stop destination, TimeDay departureTime) {
        return tripOffers.getAllPossibleRoutes(source, destination, departureTime);
    }

    public BasicMap getMap() {
        return map;
    }
}
