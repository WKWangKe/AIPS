package aips;

import java.time.LocalDateTime;

/**
 * This is the record to store the single slot traffic information including time and count
 */
public record SlotTraffic(LocalDateTime time, long count) {
    public static final long INTERVAL_MIN = 30;
}
