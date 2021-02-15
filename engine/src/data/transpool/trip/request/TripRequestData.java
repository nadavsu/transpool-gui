package data.transpool.trip.request;

import data.transpool.map.component.Stop;
import data.transpool.time.TimeDay;
import exception.data.InvalidDayStartException;
import exception.data.TransPoolDataException;
import javafx.beans.property.*;

import java.time.LocalTime;
import java.util.Objects;

public class TripRequestData extends BasicTripRequestData implements TripRequest {

    private SimpleObjectProperty<TimeDay> requestTime;
    private BooleanProperty isTimeOfArrival;
    private SimpleBooleanProperty isContinuous;

    public TripRequestData(String riderName, Stop sourceStop, Stop destinationStop, int day,
                           LocalTime requestTime, boolean isTimeOfArrival, boolean isContinuous) throws TransPoolDataException {
        super(riderName, sourceStop, destinationStop);
        this.requestTime = new SimpleObjectProperty<>(new TimeDay(requestTime, day));
        this.isTimeOfArrival = new SimpleBooleanProperty(isTimeOfArrival);
        this.isContinuous = new SimpleBooleanProperty(isContinuous);
        setDay(day);
    }


    @Override
    public boolean isTimeOfArrival() {
        return isTimeOfArrival.get();
    }

    @Override
    public TimeDay getRequestTime() {
        return requestTime.get();
    }

    private void setDay(int day) throws InvalidDayStartException {
        requestTime.get().setDay(day);
    }

    @Override
    public boolean isContinuous() {
        return isContinuous.get();
    }

    @Override
    public SimpleBooleanProperty isContinuousProperty() {
        return isContinuous;
    }

    @Override
    public BooleanProperty isTimeOfArrivalProperty() {
        return isTimeOfArrival;
    }

    @Override
    public SimpleObjectProperty<TimeDay> requestTimeProperty() {
        return requestTime;
    }


    @Override
    public String toString() {
        return transpoolRider.get().getUsername() + " - " + transpoolRider.get().getID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TripRequestData)) return false;
        TripRequestData that = (TripRequestData) o;
        return requestID.get() == that.requestID.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestID.get());
    }
}
