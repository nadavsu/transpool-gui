package data.transpool.trip.request;

import data.transpool.map.component.Stop;
import data.transpool.user.TransPoolRider;
import javafx.beans.property.*;

import java.util.Objects;

/**
 * A class holding the basic Trip Request data.
 */
public abstract class BasicTripRequestData implements BasicTripRequest {

    private static int IDGenerator = 20000;
    protected IntegerProperty requestID;
    protected ObjectProperty<TransPoolRider> transpoolRider;
    protected ObjectProperty<Stop> sourceStop;
    protected ObjectProperty<Stop> destinationStop;

    public BasicTripRequestData(String riderName, Stop sourceStop, Stop destinationStop) {
        this.requestID = new SimpleIntegerProperty(IDGenerator++);
        this.transpoolRider = new SimpleObjectProperty<>(new TransPoolRider(riderName));
        this.sourceStop = new SimpleObjectProperty<>(new Stop(sourceStop));
        this.destinationStop = new SimpleObjectProperty<>(new Stop(destinationStop));
    }

    public BasicTripRequestData(TripRequest other) {
        this.requestID = new SimpleIntegerProperty(other.getRequestID());
        this.transpoolRider = new SimpleObjectProperty<>(new TransPoolRider(other.getTransPoolRider()));
        this.sourceStop = new SimpleObjectProperty<>(other.getSourceStop());
        this.destinationStop = new SimpleObjectProperty<>(other.getDestinationStop());
    }

    @Override
    public int getRequestID() {
        return this.requestID.get();
    }

    @Override
    public TransPoolRider getTransPoolRider() {
        return transpoolRider.get();
    }

    @Override
    public void setTransPoolRider(TransPoolRider transpoolRider) {
        this.transpoolRider.set(transpoolRider);
    }


    @Override
    public Stop getSourceStop() {
        return this.sourceStop.get();
    }

    @Override
    public void setSourceStop(Stop sourceStop) {
        this.sourceStop.set(sourceStop);
    }

    @Override
    public Stop getDestinationStop() {
        return this.destinationStop.get();
    }

    @Override
    public void setDestinationStop(Stop destinationStop) {
        this.destinationStop.set(destinationStop);
    }

    @Override
    public ObjectProperty<TransPoolRider> transpoolRiderProperty() {
        return transpoolRider;
    }

    @Override
    public IntegerProperty requestIDProperty() {
        return requestID;
    }

    @Override
    public ObjectProperty<Stop> sourceStopProperty() {
        return sourceStop;
    }

    @Override
    public ObjectProperty<Stop> destinationStopProperty() {
        return destinationStop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicTripRequestData)) return false;
        BasicTripRequestData that = (BasicTripRequestData) o;
        return requestID.equals(that.requestID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestID);
    }

    @Override
    public String toString() {
        return "Rider " + requestID.get() + " - " + getTransPoolRider();
    }
}
