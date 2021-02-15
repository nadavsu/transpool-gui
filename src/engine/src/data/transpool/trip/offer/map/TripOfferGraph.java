package data.transpool.trip.offer.map;

import data.transpool.map.component.Stop;
import data.transpool.time.TimeDay;
import data.transpool.trip.offer.data.SubTripOffer;
import data.transpool.trip.offer.matching.PossibleRoute;
import data.transpool.trip.offer.matching.PossibleRoutesList;
import data.transpool.trip.offer.data.TripOffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Trip offer weighted graph
 * Vertex - Stop
 * Weight - SubTripOffer
 */

public class TripOfferGraph {
    private List<List<SubTripOffer>> adjointList;

    public TripOfferGraph() {
        adjointList = new ArrayList<>();
    }

    public TripOfferGraph(int numOfStops, List<TripOffer> allTripOffers) {
        adjointList = new ArrayList<>();
        for (int i = 0; i < numOfStops; i ++) {
            adjointList.add(new ArrayList<>());
        }
        allTripOffers.forEach(this::add);
    }

    public void add(TripOffer tripOffer) {
        tripOffer
                .getRoute()
                .forEach(this::newConnection);
    }

    private void newConnection(SubTripOffer subTripOffer) {
        int sourceID = subTripOffer.getSourceStop().getID();
        adjointList.get(sourceID).add(subTripOffer);
    }

    /**
     * The main function for finding all possible routes from Stop source to Stop destination. Only takes into account
     * the departure time. ***Arrival time is not yet done!***
     * @param source - The source to depart from
     * @param destination - The destination to arrive at
     * @param departureTime - The desired departure time.
     * @return -  A list of possible routes from source to destination, departing AFTER departure time.
     */
    public PossibleRoutesList getAllPossibleRoutes(Stop source, Stop destination, TimeDay departureTime) {
        boolean[] beingVisited = new boolean[adjointList.size()];

        PossibleRoute currentRoute = new PossibleRoute();
        PossibleRoutesList possibleRoutes = new PossibleRoutesList();

        depthFirstTraversal(source, destination, departureTime, beingVisited, currentRoute, possibleRoutes);

        return possibleRoutes;
    }


    /**
     * The auxiliary function for finding the possible routes.
     * Uses DFS recursive algorithm. Traverses SubTripOffers, relevant STOs are converted to timedSTOs.
     * "if (currentRoute.add(nextOffer, departureTime))" (line 89) is where the magic happens.
     * @param currentStop - The... current stop.
     * @param destination - The desired destination
     * @param departureTime - The current departure time.
     * @param beingVisited - An array of booleans to check if the stops are already visited.
     * @param currentRoute - The current route built - a stack.
     * @param possibleRoutes - The value which will be returned at the end.
     */
    private void depthFirstTraversal(Stop currentStop, Stop destination, TimeDay departureTime,
                                     boolean[] beingVisited, PossibleRoute currentRoute,
                                     PossibleRoutesList possibleRoutes) {
        beingVisited[currentStop.getID()] = true;

        if (currentStop.equals(destination)) {
            possibleRoutes.add(new PossibleRoute(currentRoute));
            beingVisited[currentStop.getID()] = false;
            return;
        }

        for (SubTripOffer nextOffer : adjointList.get(currentStop.getID())) {
            if (nextOffer != null && !beingVisited[nextOffer.getDestinationStop().getID()]) {
                if (currentRoute.add(nextOffer, departureTime)) {
                    depthFirstTraversal(nextOffer.getDestinationStop(), destination, currentRoute.getTimeOfArrival(), beingVisited,
                            currentRoute, possibleRoutes);
                    currentRoute.remove(nextOffer);
                }
            }
        }

        beingVisited[currentStop.getID()] = false;
    }
}
