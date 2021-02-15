package data.transpool.trip.request;

import data.transpool.map.component.Stop;
import data.transpool.user.TransPoolRider;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

public interface BasicTripRequest {
    TransPoolRider getTransPoolRider();
    void setTransPoolRider(TransPoolRider transpoolRider);
    ObjectProperty<TransPoolRider> transpoolRiderProperty();

    int getRequestID();
    IntegerProperty requestIDProperty();

    Stop getSourceStop();
    void setSourceStop(Stop sourceStop);
    ObjectProperty<Stop> sourceStopProperty();

    Stop getDestinationStop();
    void setDestinationStop(Stop destinationStop);
    ObjectProperty<Stop> destinationStopProperty();
}
