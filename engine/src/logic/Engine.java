package logic;

import com.fxgraph.graph.Graph;
import data.transpool.TransPoolData;
import data.transpool.time.Recurrence;
import data.transpool.time.TimeInterval;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.request.TripRequest;
import data.transpool.user.Feedbackable;
import data.transpool.user.Feedbacker;
import exception.NoResultsFoundException;
import exception.data.TransPoolDataException;
import javafx.beans.property.BooleanProperty;
import javafx.collections.ObservableList;

import java.io.File;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.ExecutionException;

public interface Engine {

    void loadFile(File file) throws ExecutionException, InterruptedException;

    void createMap(Graph mapGraph);

    void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                       int day, LocalTime time, boolean isArrivalTime, boolean isContinuous)
            throws TransPoolDataException;

    ObservableList<TripRequest> getAllTripRequests();

    ObservableList<TripOffer> getAllTripOffers();

    void createNewTripOffer(String driverName, LocalTime departureTime, int dayStart, Recurrence recurrences,
                            int riderCapacity, int PPK, ObservableList<String> addedStops) throws TransPoolDataException;

    void createNewFeedback(Feedbacker feedbacker, Feedbackable feedbackee, int rating, String comment);

    void findPossibleMatches(TripRequest request, int maximumMatches) throws NoResultsFoundException, TransPoolDataException;

    void clearPossibleMatches();

    void incrementTime(TimeInterval duration);

    void decrementTime(TimeInterval duration);

    ObservableList<PossibleRoute> getPossibleRoutes();

    void addNewMatch(int possibleMatchIndex) throws TransPoolDataException;

    ObservableList<MatchedTripRequest> getAllMatchedTripRequests();

    ObservableList<Integer> getAllMatchedTripRequestIDs();

    TransPoolData getData();

    BooleanProperty fileLoadedProperty();

    BooleanProperty foundMatchesProperty();

    void setData(TransPoolData data);
}
