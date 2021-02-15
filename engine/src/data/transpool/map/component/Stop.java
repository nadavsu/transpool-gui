package data.transpool.map.component;

import api.components.map.course.transpool.graph.component.details.StationDetailsDTO;
import data.transpool.trip.offer.data.TripOffer;
import data.transpool.user.TransPoolDriver;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Stop {
    private static int IDGenerator = 0;
    private int ID;
    private int x;
    private int y;
    private StringProperty name;
    private StationDetailsDTO details;

    public Stop(data.jaxb.Stop JAXBStop) {
        this.ID = IDGenerator++;
        this.x = JAXBStop.getX();
        this.y = JAXBStop.getY();
        this.name = new SimpleStringProperty(JAXBStop.getName().trim());
        this.details = new StationDetailsDTO(new ArrayList<>());
    }

    public Stop(Stop other) {
        this.ID = other.ID;
        this.x = other.x;
        this.y = other.y;
        this.name = new SimpleStringProperty(other.getName());
        this.details = other.details;
    }

    public void addDetails(String details) {
        this.details.addDetails(details);
    }


    public int getID() {
        return ID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public static void resetIDGenerator() {
        IDGenerator = 0;
    }

    public StationDetailsDTO getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stop)) return false;
        Stop stop = (Stop) o;
        return ID == stop.ID &&
                x == stop.x &&
                y == stop.y &&
                getName().equals(stop.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, x, y, getName());
    }

    public void clearDetails() {
        details.clear();
    }
}
