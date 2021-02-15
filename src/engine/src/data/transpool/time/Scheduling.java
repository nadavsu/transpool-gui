package data.transpool.time;

import data.transpool.TransPoolData;
import exception.data.InvalidDayStartException;
import exception.data.TransPoolDataException;
import javafx.beans.property.*;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Scheduling - Used by everything that is scheduled.
 * Members - arrival time, departure time and recurrences.
 */
public class Scheduling {
    private ObjectProperty<TimeDay> departureTime;
    private ObjectProperty<TimeDay> arrivalTime;
    private ObjectProperty<Recurrence> recurrences;

    public Scheduling(int dayStart, LocalTime departureTime, int tripDuration, Recurrence recurrences) throws TransPoolDataException {
        this.departureTime = new SimpleObjectProperty<>(new TimeDay(departureTime, dayStart));
        this.recurrences = new SimpleObjectProperty<>(recurrences);
        this.arrivalTime = new SimpleObjectProperty<>();
        setArrivalTime(tripDuration);
    }

    public Scheduling(TimeDay timeStart, TimeDay timeEnd, Recurrence recurrences) {
        this.departureTime = new SimpleObjectProperty<>(timeStart);
        this.arrivalTime = new SimpleObjectProperty<>(timeEnd);
        this.recurrences = new SimpleObjectProperty<>(recurrences);
    }

    public Scheduling(data.jaxb.Scheduling JAXBScheduling, int tripDuration) throws TransPoolDataException {
        this.recurrences = new SimpleObjectProperty<>();
        LocalTime time = LocalTime.of(JAXBScheduling.getHourStart(), 0);
        this.departureTime = new SimpleObjectProperty<>(new TimeDay(time, getDayStart(JAXBScheduling.getDayStart())));
        this.arrivalTime = new SimpleObjectProperty<>();
        setArrivalTime(tripDuration);
        setRecurrences(JAXBScheduling.getRecurrences());
    }

    public Scheduling(Scheduling other) {
        recurrences = new SimpleObjectProperty<>(other.getRecurrences());
        departureTime = new SimpleObjectProperty<>(new TimeDay(other.departureTime.get()));
        arrivalTime = new SimpleObjectProperty<>(new TimeDay(other.arrivalTime.get()));
    }

    private int getDayStart(Integer dayStart) {
        if (dayStart == null) {
            return 1;
        } else {
            return dayStart;
        }
    }

    private void setArrivalTime(int tripDuration) {
        TimeDay arrivalTime = new TimeDay(this.departureTime.get());
        arrivalTime.plus(tripDuration);
        this.arrivalTime.set(arrivalTime);
    }

    /**
     * Converts a String recurrence from the JAXB files to the enum.
     * @param recurrences
     */
    private void setRecurrences(String recurrences) {
        switch (recurrences) {
            case "Daily":
                this.recurrences.set(Recurrence.DAILY);
                break;
            case "Bi-daily":
                this.recurrences.set(Recurrence.BI_DAILY);
                break;
            case "Weekly":
                this.recurrences.set(Recurrence.WEEKLY);
                break;
            case "Monthly":
                this.recurrences.set(Recurrence.MONTHLY);
                break;
            default:
                this.recurrences.set(Recurrence.ONE_TIME);
                break;
        }
    }

    /**
     * Checks if now is the departure time. Uses TransPoolData.currentTime.
     * @return - true if the schedule is currently at departure time, false otherwise.
     */
    public boolean isCurrentlyDeparting() {
        return TransPoolData.currentTime.getTime().equals(departureTime.get().getTime())
                && isHappeningOnDay(TransPoolData.currentTime.getDay());
    }

    /**
     * Checks if the schedule is currently happening, if the current time is
     * between the departureTime and the arrivalTime
     * @return - true if the current time is between the departureTime and the arrivalTime, false otherwise.
     */
    public boolean isCurrentlyHappening() {
        return !TransPoolData.currentTime.getTime().isBefore(departureTime.get().getTime())
                && !TransPoolData.currentTime.getTime().isAfter(arrivalTime.get().getTime())
                && isHappeningOnDay(TransPoolData.currentTime.getDay());
    }

    /**
     * Checks if now is the time of arrival of the schedule.
     * @return true if now is the time of arrival of the schedule, false otherwise.
     */
    public boolean isCurrentlyArriving() {
        return TransPoolData.currentTime.getTime().equals(arrivalTime.get().getTime())
                && isHappeningOnDay(TransPoolData.currentTime.getDay());
    }

    /**
     * Checks the schedule is happening on some day
     * @param day - the day to check if the schedule is occurring.
     * @return - true if occurring n day, false otherwise.
     */
    public boolean isHappeningOnDay(int day) {
        return getRecurrences().isOnDay(day, getDayStart());
    }


    /**
     * Checks to see if a schedule is occurring before timeDay.
     * @param timeDay - The point in time to check if the schedule is happening before.
     * @return - true if the schedule is happening before timeDay, false otherwise.
     */
    public boolean isBefore(TimeDay timeDay) {
        return this.getDepartureTime().isBefore(timeDay);
    }

    /**
     * Gets the first recurrence happening after timeDay.
     * @param scheduling - The scheduling you want to get the firsr occurrence after timeDay.
     * @param timeDay - The time and day you want the schedule after
     * @return - A new instance of schedule which is the first one after timeDay.
     */
    public static Scheduling getFirstRecurrenceAfter(Scheduling scheduling, TimeDay timeDay) {
        if (!timeDay.isAfter(scheduling.getDepartureTime())) {
            return scheduling;
        } else if (!scheduling.getRecurrences().equals(Recurrence.ONE_TIME)){
            Scheduling nextOccurrence = new Scheduling(scheduling);
            while (nextOccurrence.isBefore(timeDay)) {
                nextOccurrence.setNextRecurrence();
            }
            return nextOccurrence;
        } else {
            return null;
        }
    }

    private void setNextRecurrence() {
        departureTime.get().setNextRecurrence(this.getRecurrences());
        arrivalTime.get().setNextRecurrence(this.getRecurrences());
    }

    public int getDayStart() {
        return departureTime.get().getDay();
    }

    public Recurrence getRecurrences() {
        return recurrences.get();
    }

    public ObjectProperty<Recurrence> recurrencesProperty() {
        return recurrences;
    }

    public TimeDay getDepartureTime() {
        return departureTime.get();
    }

    public ObjectProperty<TimeDay> departureTimeProperty() {
        return departureTime;
    }

    public void setDepartureTime(TimeDay departureTime) {
        this.departureTime.set(departureTime);
    }

    public TimeDay getArrivalTime() {
        return arrivalTime.get();
    }

    public ObjectProperty<TimeDay> arrivalTimeProperty() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        return recurrences + " " + departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Scheduling)) return false;
        Scheduling that = (Scheduling) o;
        return Objects.equals(recurrences, that.recurrences) &&
                Objects.equals(departureTime, that.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recurrences, departureTime);
    }
}
