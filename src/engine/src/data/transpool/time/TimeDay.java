package data.transpool.time;

import exception.data.InvalidDayStartException;
import exception.data.TransPoolDataException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalTime;
import java.util.Objects;

/**
 * A class for managing the time and day. Not only live time and day, but also some timestamp. (what?)
 */

public class TimeDay {

    private static final int DAY_START = 1;
    private static final LocalTime TIME_START = LocalTime.MIDNIGHT;

    private ObjectProperty<LocalTime> time;
    private IntegerProperty day;

    public TimeDay() {
        time = new SimpleObjectProperty<>(TIME_START);
        day = new SimpleIntegerProperty(DAY_START);
    }

    public TimeDay(LocalTime time, int day) throws TransPoolDataException {
        this.time = new SimpleObjectProperty<>(time);
        this.day = new SimpleIntegerProperty();
        setDay(day);
    }

    public TimeDay(TimeDay other) {
        this.day = new SimpleIntegerProperty(other.day.get());
        this.time = new SimpleObjectProperty<>(other.time.get());
    }

    public void setDay(Integer day) throws InvalidDayStartException {
        if (day == null) {
            this.day.set(1);
        } else if (day < 1) {
            throw new InvalidDayStartException();
        } else {
            this.day.set(day);
        }
    }

    public LocalTime getTime() {
        return time.get();
    }

    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time.set(time);
    }

    public int getDay() {
        return day.get();
    }

    public IntegerProperty dayProperty() {
        return day;
    }

    /**
     * Checks to see if an instance of TimeDay is before another instance ofTimeDay.
     * @param other - the other TimeDay (duh)
     * @return - true if this instance is before other, false otherwise.
     */
    public boolean isBefore(TimeDay other) {
        if (this.day.get() < other.day.get()) {
            return true;
        } else if (this.day.get() == other.day.get()) {
            return this.time.get().isBefore(other.time.get());
        } else {
            return false;
        }
    }


    /**
     * Checks to see if an instance of TimeDay is after another instance ofTimeDay.
     * @param other - the other TimeDay.
     * @return - true if this instance is after other, false otherwise.
     */
    public boolean isAfter(TimeDay other) {
        if (this.day.get() > other.day.get()) {
            return true;
        } else if (this.day.get() == other.day.get()) {
            return this.time.get().isAfter(other.time.get());
        } else {
            return false;
        }
    }

    /**
     * Add minutes to this instance.
     * @param minutes - to add.
     */
    public void plus(int minutes) {
        LocalTime timeBefore = time.get();
        time.set(time.get().plusMinutes(minutes));
        if (time.get().compareTo(timeBefore) <= 0) {
            day.set(day.get() + 1);
        }
    }


    /**
     * Reduces minutes from this instance.
     * @param minutes - minutes to reduce.
     */
    public void minus(int minutes) {
        LocalTime timeBefore = time.get();
        LocalTime timeAfter = time.get().minusMinutes(minutes);
        int dayAfter = day.get();
        if (timeAfter.compareTo(timeBefore) >= 0) {
            dayAfter--;
        }
        if (dayAfter >= DAY_START && !timeAfter.isBefore(TIME_START)) {
            time.set(timeAfter);
            day.set(dayAfter);
        }
    }

    public void setNextRecurrence(Recurrence recurrences) {
        day.set(day.get() + recurrences.getValue());
    }

    public boolean isInRange(TimeDay time1, TimeDay time2) {
        return this.isBefore(time2) && this.isAfter(time1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimeDay)) return false;
        TimeDay timeDay = (TimeDay) o;
        return time.get().equals(timeDay.time.get()) &&
                day.get() == timeDay.day.get();
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, day);
    }

    @Override
    public String toString() {
        return "on time " + getTime() + " on day " + getDay();
    }

}
