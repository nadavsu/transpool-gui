package api.components.map.course.transpool.graph.component.details;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;

import java.util.List;

/*
Dummy container to hold information needed to be shown upon clicking a station
 */
public class StationDetailsDTO {

    private String name;
    private int x;
    private int y;
    private List<String> details;
    private IntegerProperty numOfCars;

    public StationDetailsDTO(List<String> details) {
        this.details = details;
        this.numOfCars = new SimpleIntegerProperty(0);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getDetails() {
        return details;
    }

    public void addDetails(String details) {
        this.details.add(details);
        this.numOfCars.set(numOfCars.get() + 1);
    }

    public void clear() {
        details.clear();
        numOfCars.set(0);
    }

    public IntegerProperty numOfCarsProperty() {
        return numOfCars;
    }
}
