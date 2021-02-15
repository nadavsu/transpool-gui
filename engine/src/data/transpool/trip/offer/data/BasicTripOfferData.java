package data.transpool.trip.offer.data;

import data.transpool.time.Recurrence;
import data.transpool.time.Scheduling;
import data.transpool.user.TransPoolDriver;
import exception.data.TransPoolDataException;
import javafx.beans.property.*;

import java.time.LocalTime;

/**
 * Contains basic data of a trip offer - be it a sub-trip offer or a normal trip offer.
 */
public abstract class BasicTripOfferData implements BasicTripOffer {
    private static int IDGenerator = 100000;
    protected IntegerProperty offerID;
    protected ObjectProperty<TransPoolDriver> transpoolDriver;
    protected IntegerProperty PPK;
    protected IntegerProperty maxPassengerCapacity;

    //Initialized at children
    protected ObjectProperty<Scheduling> schedule;
    protected IntegerProperty tripDurationInMinutes;
    protected IntegerProperty tripPrice;
    protected DoubleProperty averageFuelConsumption;

    public BasicTripOfferData(String driverName, int PPK, int maxPassengerCapacity) {
        this.offerID = new SimpleIntegerProperty(IDGenerator++);
        this.transpoolDriver = new SimpleObjectProperty<>(new TransPoolDriver(driverName));
        this.PPK = new SimpleIntegerProperty(PPK);

        this.schedule = new SimpleObjectProperty<>();
        this.tripDurationInMinutes = new SimpleIntegerProperty();
        this.tripPrice = new SimpleIntegerProperty();
        this.averageFuelConsumption = new SimpleDoubleProperty();
        this.maxPassengerCapacity = new SimpleIntegerProperty(maxPassengerCapacity);
    }

    public BasicTripOfferData(data.jaxb.TransPoolTrip JAXBTransPoolTrip) {
        this.offerID = new SimpleIntegerProperty(IDGenerator++);
        this.transpoolDriver = new SimpleObjectProperty<>(new TransPoolDriver(JAXBTransPoolTrip.getOwner()));
        this.PPK = new SimpleIntegerProperty(JAXBTransPoolTrip.getPPK());
        this.maxPassengerCapacity = new SimpleIntegerProperty(JAXBTransPoolTrip.getCapacity());

        this.schedule = new SimpleObjectProperty<>();
        this.tripDurationInMinutes = new SimpleIntegerProperty();
        this.tripPrice = new SimpleIntegerProperty();
        this.averageFuelConsumption = new SimpleDoubleProperty();
    }

    public BasicTripOfferData(BasicTripOffer other) {
        this.offerID = new SimpleIntegerProperty(other.getOfferID());
        this.transpoolDriver = new SimpleObjectProperty<>(other.getTransPoolDriver());
        this.PPK = new SimpleIntegerProperty(other.getPPK());
        this.schedule = new SimpleObjectProperty<>(new Scheduling(other.getScheduling()));
        this.maxPassengerCapacity = new SimpleIntegerProperty(other.getMaxPassengerCapacity());

        this.schedule = new SimpleObjectProperty<>();
        this.tripDurationInMinutes = new SimpleIntegerProperty();
        this.tripPrice = new SimpleIntegerProperty();
        this.averageFuelConsumption = new SimpleDoubleProperty();

    }

    @Override
    public IntegerProperty offerIDProperty() {
        return offerID;
    }

    @Override
    public int getOfferID() {
        return offerID.get();
    }

    @Override
    public TransPoolDriver getTransPoolDriver() {
        return transpoolDriver.get();
    }

    @Override
    public void setTransPoolDriver(TransPoolDriver transpoolDriver) {
        this.transpoolDriver.set(transpoolDriver);
    }

    @Override
    public int getPPK() {
        return PPK.get();
    }

    @Override
    public void setPPK(int PPK) {
        this.PPK.set(PPK);
    }

    @Override
    public Scheduling getScheduling() {
        return schedule.get();
    }

    @Override
    public void setScheduling(Scheduling schedule) {
        this.schedule.set(schedule);
    }

    @Override
    public ObjectProperty<Scheduling> schedulingProperty() {
        return schedule;
    }

    @Override
    public ObjectProperty<TransPoolDriver> transpoolDriverProperty() {
        return transpoolDriver;
    }

    @Override
    public IntegerProperty PPKProperty() {
        return PPK;
    }

    @Override
    public int getTripDurationInMinutes() {
        return tripDurationInMinutes.get();
    }

    @Override
    public int getPrice() {
        return tripPrice.get();
    }


    @Override
    public double getAverageFuelConsumption() {
        return averageFuelConsumption.get();
    }

    @Override
    public IntegerProperty tripDurationInMinutesProperty() {
        return tripDurationInMinutes;
    }

    @Override
    public IntegerProperty priceProperty() {
        return tripPrice;
    }

    @Override
    public DoubleProperty averageFuelConsumptionProperty() {
        return averageFuelConsumption;
    }

    @Override
    public int getMaxPassengerCapacity() {
        return maxPassengerCapacity.get();
    }

    @Override
    public void setMaxPassengerCapacity(int maxPassengerCapacity) {
        this.maxPassengerCapacity.set(maxPassengerCapacity);
    }

    @Override
    public IntegerProperty maxPassengerCapacityProperty() {
        return maxPassengerCapacity;
    }
}
