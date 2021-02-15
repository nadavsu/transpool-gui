package data.transpool.trip.offer.map;

import logic.components.TripOfferEngine;
import data.jaxb.TransPoolTrip;
import data.transpool.map.BasicMap;
import data.transpool.map.component.Stop;
import data.transpool.time.TimeDay;
import data.transpool.trip.offer.data.SubTripOffer;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.trip.offer.data.TripOfferData;
import data.transpool.trip.offer.matching.PossibleRoutesList;
import exception.data.TransPoolDataException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * The main class which holds the trip offer graph and all the trip offers data. Also holds the timed data
 * such as current trip offers and current sub trip offers which are happening.
 */
public class TripOfferMap implements TripOfferEngine {
    private ObservableList<TripOffer> allTripOffers;
    private TripOfferGraph tripOfferGraph;

    //Live details
    private ObservableList<TripOffer> currentTripOffers;
    private List<SubTripOffer> currentSubTripOffers;


    public TripOfferMap(BasicMap map, List<TransPoolTrip> JAXBTripOffers) throws TransPoolDataException {
        this.allTripOffers = FXCollections.observableArrayList();
        this.currentSubTripOffers = new ArrayList<>();
        this.currentTripOffers = FXCollections.observableArrayList();
        initAllTripOffers(map, JAXBTripOffers);
        update();

        this.tripOfferGraph = new TripOfferGraph(map.getNumberOfStops(), allTripOffers);
    }

    private void initAllTripOffers(BasicMap map, List<TransPoolTrip> JAXBTripOffers) throws TransPoolDataException {
        for (TransPoolTrip JAXBTrip : JAXBTripOffers) {
            allTripOffers.add(new TripOfferData(JAXBTrip, map));
        }
    }

    @Override
    public TripOffer getTripOffer(int ID) {
        return allTripOffers
                .stream()
                .filter(t -> t.getOfferID() == ID)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addTripOffer(TripOffer tripOffer) {
        allTripOffers.add(tripOffer);
        tripOfferGraph.add(tripOffer);
    }

    @Override
    public ObservableList<TripOffer> getAllTripOffers() {
        return allTripOffers;
    }

    @Override
    public ObservableList<TripOffer> getCurrentOffers() {
        return currentTripOffers;
    }

    //----------------------------------------------------------------------------------------------------------------//

    /**
     * This function updates the map every time the system's time is changed.
     * 1. Gets all the current tripoffers happening.
     * 2. Populates the list of current subTripOffers happening
     * 3. Updates the stops with the relevant details through the subTripOffers using subTripOffer.currentDetails() functions.
     *    The details are shown on the live map when a stop is clicked.
     */
    public void update() {
        currentTripOffers.clear();
        currentSubTripOffers.clear();
        for (TripOffer tripOffer : allTripOffers) {
            if (tripOffer.isCurrentlyHappening()) {
                currentTripOffers.add(tripOffer);
                currentSubTripOffers.add(tripOffer.getCurrentSubTripOffer());
            }
        }
        for (SubTripOffer subTripOffer : currentSubTripOffers) {
            if (subTripOffer != null && subTripOffer.isCurrentlyDeparting()) {
                subTripOffer.getSourceStop().addDetails(subTripOffer.currentDetails());
            } else if (subTripOffer != null && subTripOffer.isCurrentlyArriving()) {
                subTripOffer.getDestinationStop().addDetails(subTripOffer.currentDetails());
            }
        }
    }

    public SubTripOffer getSubTripOffer(int tripOfferID, int subTripOfferID) {
        return getTripOffer(tripOfferID).getSubTripOffer(subTripOfferID);
    }

    public PossibleRoutesList getAllPossibleRoutes(Stop source, Stop destination, TimeDay departureTime) {
        return tripOfferGraph.getAllPossibleRoutes(source, destination, departureTime);
    }

    public TripOfferGraph getTripOfferGraph() {
        return tripOfferGraph;
    }

}
