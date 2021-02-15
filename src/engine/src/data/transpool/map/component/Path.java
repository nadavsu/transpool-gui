package data.transpool.map.component;

import exception.data.PathDoesNotExistException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A path connecting two stops (source and destination).
 */
public class Path {
    private Stop source;
    private Stop destination;
    private boolean isOneWay;
    private int length;
    private double fuelConsumption;
    private int maxSpeed;

    /**
     * Constructor for creating a new path from the generated JAXB classes.
     * @param JAXBPath - the generated JAXB path.
     */
    public Path(java.util.Map<String, Stop> allStops, data.jaxb.Path JAXBPath) throws PathDoesNotExistException {
        String source = JAXBPath.getFrom();
        String destination = JAXBPath.getTo();
        if (!allStops.containsKey(source)) {
            throw new PathDoesNotExistException(source, destination);
        }
        if (!allStops.containsKey(destination)) {
            throw new PathDoesNotExistException(source, destination);
        }
        this.source = allStops.get(source);
        this.destination = allStops.get(destination);
        this.isOneWay = JAXBPath.isOneWay();
        this.length = JAXBPath.getLength();
        this.fuelConsumption = JAXBPath.getFuelConsumption();
        this.maxSpeed = JAXBPath.getSpeedLimit();
    }

    public Path(Path other) {
        this.source = other.source;
        this.destination = other.destination;
        this.isOneWay = other.isOneWay;
        this.length = other.length;
        this.fuelConsumption = other.fuelConsumption;
        this.maxSpeed = other.maxSpeed;
    }

    /**
     * Swaps the direction of a path only if it's a 2-way path. Used by the TransPoolPaths data structure.
     * @throws RuntimeException - Thrown if somehow the user managed to try to swap a one way path.
     */
    public void swapDirection() throws RuntimeException {
        if (isOneWay) {
            System.out.println("Aw. Tried to swap the direction of a one way path :(");
            throw new RuntimeException();
        }
        Stop temp;
        temp = source;
        source = destination;
        destination = temp;
    }

    public static List<Stop> asStopList(List<Path> pathList) {
        int i;
        List<Stop> stopsList = new ArrayList<>();

        for (i = 0; i < pathList.size(); i++) {
            stopsList.add(new Stop(pathList.get(i).source));
        }
        stopsList.add(new Stop(pathList.get(i - 1).destination));
        return stopsList;
    }

    public String getSourceName() {
        return source.getName();
    }

    public String getDestinationName() {
        return destination.getName();
    }

    public Stop getSourceStop() {
        return source;
    }

    public Stop getDestinationStop() {
        return destination;
    }

    public boolean isOneWay() {
        return isOneWay;
    }

    public int getLength() {
        return length;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public int getPathTime() {
        return (60 * length) / maxSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Path)) return false;
        Path that = (Path) o;
        return ((this.source.equals(that.source)
                && this.destination.equals(that.destination)) ||
                (this.source.equals(that.destination) && this.destination.equals(that.source)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }
}
