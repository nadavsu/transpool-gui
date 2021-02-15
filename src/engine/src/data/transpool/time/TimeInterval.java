package data.transpool.time;

public enum TimeInterval {
    FIVE_MINUTES("5 minutes", 5),
    THIRTY_MINUTES("30 minutes", 30),
    ONE_HOUR("1 hour", 60),
    TWO_HOURS("2 hours", 120),
    ONE_DAY("1 day", 1440);

    private String name;
    private int minutes;

    TimeInterval(String name, int minutes) {
        this.name = name;
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
