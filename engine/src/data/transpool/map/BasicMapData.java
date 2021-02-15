package data.transpool.map;

import data.jaxb.MapDescriptor;
import data.transpool.map.component.Path;
import data.transpool.map.component.Stop;
import exception.data.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

/**
 * The map.
 * Contains a list of stops, a list of paths, and a matrix containing the stops' positions on the map.
 */
public class BasicMapData implements BasicMap {

    private final static int MIN_MAP_SIZE = 6;
    private final static int MAX_MAP_SIZE = 100;

    protected int width;
    protected int length;
    protected MapMatrix mapMatrix;

    protected static java.util.Map<String, Stop> allStops;
    protected static List<Path> allPaths;

    /**
     * Constructor for creating a map out of the JAXB generated classes.
     * @param JAXBMap - JAXB Generated map.
     * @throws TransPoolDataException - Thrown if there's a problem with the data inside the TP data file.
     */
    public BasicMapData(MapDescriptor JAXBMap) throws TransPoolDataException {
        allStops = new HashMap<>();
        allPaths = new ArrayList<>();
        setWidth(JAXBMap.getMapBoundries().getWidth());
        setLength(JAXBMap.getMapBoundries().getLength());

        initAllStops(JAXBMap);
        initAllPaths(JAXBMap);
        mapMatrix = new MapMatrix(JAXBMap);
    }

    /**
     * Initializer for creating a map of stops from JAXB generated stops list.
     * The function checks if the stop already exists, if so an StopNameDuplicationException is thrown,
     * otherwise the stop is added to the hashmap.
     * @param JAXBMap - The map containing the stops.
     * @throws StopNameDuplicationException - thrown if there is a duplication stop.
     */
    private void initAllStops(MapDescriptor JAXBMap) throws StopNameDuplicationException {
        List<data.jaxb.Stop> JAXBStopsList = JAXBMap.getStops().getStop();
        for(data.jaxb.Stop stop : JAXBStopsList) {
            if (allStops.containsKey(stop.getName())) {
                throw new StopNameDuplicationException(stop.getName());
            }
            allStops.put(stop.getName(), new Stop(stop));
        }
    }

    /**
     * Initializer for creating a list of paths from JAXB generated path classes.
     * The function checks if the path exists, or if there is a duplicated path. If so, an exception is thrown,
     * otherwise the path is added to allPaths.
     * @param JAXBMap - The JAXB generated path.
     * @throws TransPoolDataException - thrown if there's a problem with the data inside the JAXB classes/file.
     */
    private void initAllPaths(MapDescriptor JAXBMap) throws PathDuplicationException, PathDoesNotExistException {
        List<data.jaxb.Path> JAXBPathList = JAXBMap.getPaths().getPath();
        for (data.jaxb.Path JAXBPath : JAXBPathList) {
            Path transpoolPath = new Path(allStops, JAXBPath);
            if (allPaths.contains(transpoolPath)) {
                throw new PathDuplicationException(transpoolPath.getSourceName(), transpoolPath.getDestinationName());
            }
            if (!JAXBPath.isOneWay()) {
                Path swappedPath = new Path(transpoolPath);
                swappedPath.swapDirection();
                allPaths.add(swappedPath);
            }
            allPaths.add(transpoolPath);
        }
    }

    /**
     * Finds the path with the given source and destination stop names.
     * @param source - The name of the source stop.
     * @param destination - The name of the destination stop.
     * @return A reference to the path from the list of all paths if found, null otherwise.
     */
    public static Path getPathBySourceAndDestination(String source, String destination) {
        Predicate<Path> sourceDestinationMatchPredicate = p ->
                p.getDestinationName().equals(destination) && p.getSourceName().equals(source);

        return allPaths
                .stream()
                .filter(sourceDestinationMatchPredicate)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Path getPath(Stop source, Stop destination) {
        Predicate<Path> sourceDestinationMatchPredicate = p ->
                p.getDestinationStop().equals(destination) && p.getSourceStop().equals(source);

        return allPaths
                .stream()
                .filter(sourceDestinationMatchPredicate)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Path getPath(String source, String destination) {
        Predicate<Path> sourceDestinationMatchPredicate = p ->
                p.getDestinationStop().getName().equals(destination) && p.getSourceStop().getName().equals(source);

        return allPaths
                .stream()
                .filter(sourceDestinationMatchPredicate)
                .findFirst()
                .orElse(null);
    }

    private void setWidth(int width) throws MapDimensionsException {
        if (width < MIN_MAP_SIZE || width > MAX_MAP_SIZE) {
            throw new MapDimensionsException();
        }
        this.width = width;
    }

    private void setLength(int length) throws MapDimensionsException {
        if (length > MAX_MAP_SIZE || length < MIN_MAP_SIZE) {
            throw new MapDimensionsException();
        }
        this.length = length;
    }

    @Override
    public int getMapLength() {
        return length;
    }

    @Override
    public int getMapWidth() {
        return width;
    }

    @Override
    public boolean containsStop(String stopName) {
        return allStops.containsKey(stopName);
    }

    @Override
    public Stop getStop(String stopName) { return allStops.get(stopName);
    }

    @Override
    public List<Stop> getAllStopsAsList() {
        List<Stop> stopsList = new ArrayList<>();
        allStops
                .forEach((string, stop) -> stopsList.add(stop));
        return stopsList;
    }

    @Override
    public int getNumberOfStops() {
        return allStops.size();
    }

    @Override
    public java.util.Map<String, Stop> getAllStops() {
        return allStops;
    }

    @Override
    public List<Path> getAllPaths() {
        return allPaths;
    }

    @Override
    public boolean containsPath(String source, String destination) {
        for (Path path : allPaths) {
            if (path.getSourceName().equals(source) && path.getDestinationName().equals(destination)) {
                return true;
            }
        }
        return false;
    }

    public MapMatrix getMapMatrix() {
        return mapMatrix;
    }

    /**
     * 2 Dimensional array of strings containing the name of the stop in each coordinate.
     */
    public class MapMatrix {
        private String[][] mapMatrix = new String[BasicMapData.MAX_MAP_SIZE][BasicMapData.MAX_MAP_SIZE];

        public MapMatrix(MapDescriptor JAXBMap) throws StopOutOfBoundsException, StopCoordinatesDuplicationException {
            List<data.jaxb.Stop> JAXBStopsList = JAXBMap.getStops().getStop();
            for (data.jaxb.Stop stop : JAXBStopsList) {
                int x = stop.getX();
                int y = stop.getY();
                if (x > width || y > length) {
                    throw new StopOutOfBoundsException(stop.getName());
                }
                if (mapMatrix[y][x] != null) {
                    throw new StopCoordinatesDuplicationException(x, y);
                }
                mapMatrix[y][x] = stop.getName();
            }
        }

        public String[][] getMapMatrix() {
            return mapMatrix;
        }
    }
}
