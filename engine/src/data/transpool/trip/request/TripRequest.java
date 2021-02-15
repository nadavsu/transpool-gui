package data.transpool.trip.request;

import data.transpool.time.TimeDay;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalTime;

public interface TripRequest extends BasicTripRequest {
    boolean isTimeOfArrival();
    BooleanProperty isTimeOfArrivalProperty();

    TimeDay getRequestTime();
    SimpleObjectProperty<TimeDay> requestTimeProperty();

    boolean isContinuous();
    SimpleBooleanProperty isContinuousProperty();

}
