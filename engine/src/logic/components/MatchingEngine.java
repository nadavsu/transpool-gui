package logic.components;

import data.transpool.TransPoolData;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.request.MatchedTripRequest;
import data.transpool.trip.request.TripRequest;
import exception.NoResultsFoundException;
import exception.data.RideFullException;
import exception.data.TransPoolDataException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

/**
 * The engine used to find a match for a trip request. Contains the list of all possible matches, the request to match.
 */
public class MatchingEngine {
    private TripRequest tripRequestToMatch;
    private ObservableList<PossibleRoute> possibleRoutes;
    private BooleanProperty foundMatches;

    public MatchingEngine() {
        possibleRoutes = FXCollections.observableArrayList();
        foundMatches = new SimpleBooleanProperty(false);
    }


    /**
     * Finds the possible routes for a request.
     * @param data - The data to search the possible routes on
     * @param tripRequestToMatch - The trip request to match
     * @param maximumMatches - maximum matches;
     * @throws NoResultsFoundException - If no results are found, the user is told.
     */
    public void findPossibleMatches(TransPoolData data, TripRequest tripRequestToMatch, int maximumMatches) throws NoResultsFoundException {
        this.tripRequestToMatch = tripRequestToMatch;
        possibleRoutes.addAll(data.getAllPossibleRoutes(tripRequestToMatch, maximumMatches));

        if (!possibleRoutes.isEmpty()) {
            foundMatches.set(true);
        } else {
            throw new NoResultsFoundException();
        }
    }


    /**
     * Adds a new match to the system and updates all relevant data.
     * @param data - data to update
     * @param chosenPossibleRouteIndex - The chosen possible route in the list possibleRoutes.
     * @throws TransPoolDataException
     */
    public void addNewMatch(TransPoolData data, int chosenPossibleRouteIndex) throws TransPoolDataException {
        PossibleRoute chosenPossibleRoute = possibleRoutes.get(chosenPossibleRouteIndex);
        MatchedTripRequest matchedTripRequest = new MatchedTripRequest(tripRequestToMatch, chosenPossibleRoute);
        data.addMatchedRequest(matchedTripRequest);
    }


    /**
     * @return the list of all possible matches.
     */
    public ObservableList<PossibleRoute> getPossibleRoutes() {
        return possibleRoutes;
    }

    /**
     * Clears the engine's data.
     */
    public void clearPossibleMatches() {
        possibleRoutes.clear();
        foundMatches.set(false);
    }

    public BooleanProperty foundMatchesProperty() {
        return foundMatches;
    }

    public ObservableList<String> getPossibleRoutesAsString() {
        return possibleRoutes
                .stream()
                .map(PossibleRoute::toString)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
