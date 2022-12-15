package aips;

import java.time.LocalDateTime;

/**
 * This is the record to store the single slot traffic information
 */
public record SlotTraffic(LocalDateTime time, long count) {
    public static final long INTERVAL_MIN = 30;
}
