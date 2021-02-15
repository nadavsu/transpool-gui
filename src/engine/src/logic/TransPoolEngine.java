package logic;

import api.components.*;
import logic.components.MatchingEngine;
import logic.task.LoadFileTask;
import com.fxgraph.graph.Graph;
import data.transpool.TransPoolData;
import data.transpool.time.Recurrence;
import data.transpool.time.TimeInterval;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.offer.data.TripOfferData;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.request.*;
import data.transpool.user.Feedback;
import data.transpool.user.Feedbackable;
import data.transpool.user.Feedbacker;
import exception.NoResultsFoundException;
import exception.data.InvalidDayStartException;
import exception.data.StopNotFoundException;
import exception.data.TransPoolDataException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.io.File;
import java.time.LocalTime;


/**
 * The main engine of the application.
 * Holds the data, and other engines.
 */
public class TransPoolEngine implements Engine {

    private TransPoolData data;
    private BooleanProperty isLoaded;

    private MatchingEngine matchingEngine;
    private TransPoolController transpoolController;

    public TransPoolEngine(TransPoolController transpoolController) {
        this.transpoolController = transpoolController;
        this.matchingEngine = new MatchingEngine();

        this.isLoaded = new SimpleBooleanProperty(false);
    }

    @Override
    public void loadFile(File file) {
        Task<TransPoolData> loadFileTask = new LoadFileTask(this, file);
        transpoolController.bindTaskToUI(loadFileTask);

        loadFileTask.valueProperty().addListener((observable, oldValue, newValue) -> {
            transpoolController.bindUIToData(data);
            isLoaded.set(true);
            transpoolController.createMap();
        });

        new Thread(loadFileTask).start();
    }

    @Override
    public void createNewTransPoolTripRequest(String riderName, String source, String destination,
                                              int day, LocalTime time, boolean isArrivalTime, boolean isContinuous) throws
            TransPoolDataException {
        if (!data.getMap().containsStop(source)) {
            throw new StopNotFoundException(source);
        }
        if (!data.getMap().containsStop(destination)) {
            throw new StopNotFoundException(destination);
        }
        TripRequest request = new TripRequestData(riderName, data.getStop(source), data.getStop(destination), day, time, isArrivalTime, isContinuous);
        data.addTripRequest(request);

    }

    @Override
    public void createNewTripOffer(String driverName, LocalTime departureTime, int dayStart, Recurrence recurrences,
                                   int riderCapacity, int PPK, ObservableList<String> route) throws TransPoolDataException {
        if (dayStart < 1) {
            throw new InvalidDayStartException();
        }
        data.addTripOffer(new TripOfferData(data.getMap(), driverName, departureTime, dayStart, recurrences, riderCapacity, PPK, route));
    }

    @Override
    public void findPossibleMatches(TripRequest request, int maximumMatches) throws NoResultsFoundException {
        matchingEngine.findPossibleMatches(data, request, maximumMatches);
    }

    @Override
    public void createNewFeedback(Feedbacker feedbacker, Feedbackable feedbackee, int rating, String comment) {
        feedbacker.leaveFeedback(feedbackee,
                new Feedback(feedbacker.getFeedbackerID(), feedbacker.getFeedbackerName(), rating, comment)
        );
        transpoolController.updateCard();
    }

    @Override
    public void setData(TransPoolData data) {
        this.data = data;
    }

    @Override
    public void addNewMatch(int possibleMatchIndex) throws TransPoolDataException {
        matchingEngine.addNewMatch(data, possibleMatchIndex);
    }

    @Override
    public void createMap(Graph mapGraph) {
        data.createMap(mapGraph);
    }

    @Override
    public void incrementTime(TimeInterval duration) {
        data.incrementTime(duration);
    }

    @Override
    public void decrementTime(TimeInterval duration) {
        data.decrementTime(duration);
    }

    @Override
    public ObservableList<TripRequest> getAllTripRequests() {
        return data.getAllTripRequests();
    }

    @Override
    public ObservableList<TripOffer> getAllTripOffers() {
        return data.getAllTripOffers();
    }

    @Override
    public ObservableList<PossibleRoute> getPossibleRoutes() {
        return matchingEngine.getPossibleRoutes();
    }

    @Override
    public void clearPossibleMatches() {
        matchingEngine.clearPossibleMatches();
    }

    @Override
    public ObservableList<MatchedTripRequest> getAllMatchedTripRequests() {
        return data.getAllMatchedTripRequests();
    }

    @Override
    public ObservableList<Integer> getAllMatchedTripRequestIDs() {
        ObservableList<Integer> matchedTripsIDs = FXCollections.observableArrayList();
        data
                .getAllMatchedTripRequests()
                .forEach(match -> matchedTripsIDs.add(match.getRequestID()));
        return matchedTripsIDs;
    }

    @Override
    public TransPoolData getData() {
        return data;
    }


    @Override
    public BooleanProperty fileLoadedProperty() {
        return isLoaded;
    }

    @Override
    public BooleanProperty foundMatchesProperty() {
        return matchingEngine.foundMatchesProperty();
    }
}
