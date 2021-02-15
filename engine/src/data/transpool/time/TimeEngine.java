package data.transpool.time;

import java.time.Duration;

public interface TimeEngine {
    void incrementTime(TimeInterval interval);
    void decrementTime(TimeInterval interval);
    TimeDay getTimeDay();
}
