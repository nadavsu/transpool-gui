package data.transpool.trip.offer.data;

import data.transpool.time.Scheduling;
import data.transpool.user.TransPoolDriver;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

public interface BasicTripOffer {
    int getOfferID();
    IntegerProperty offerIDProperty();

    TransPoolDriver getTransPoolDriver();
    void setTransPoolDriver(TransPoolDriver transpoolDriver);
    ObjectProperty<TransPoolDriver> transpoolDriverProperty();

    Scheduling getScheduling();
    void setScheduling(Scheduling schedule);
    ObjectProperty<Scheduling> schedulingProperty();

    int getPPK();
    void setPPK(int PPK);
    IntegerProperty PPKProperty();

    int getTripDurationInMinutes();
    IntegerProperty tripDurationInMinutesProperty();

    int getPrice();
    IntegerProperty priceProperty();

    double getAverageFuelConsumption();
    DoubleProperty averageFuelConsumptionProperty();

    int getMaxPassengerCapacity();
    void setMaxPassengerCapacity(int maxPassengerCapacity);
    IntegerProperty maxPassengerCapacityProperty();






}
