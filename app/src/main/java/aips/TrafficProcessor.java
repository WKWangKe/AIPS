package aips;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TrafficProcessor {
    private static final int MIN_N = 3;
    private static final int MAX_N = 3;
    private final Deque<SlotTraffic> lastNSlots;

    private long lastNSlotsCount;
    private final PriorityQueue<SlotTraffic> maxSlots;

    private final Map<LocalDate, Long> dailyCounts;
    private long totalCount;

    private long minNSlotsCount;
    private LocalDateTime minNSlotsEnd;

    public TrafficProcessor() {
        lastNSlots = new LinkedList<>();
        maxSlots = new PriorityQueue<>(Comparator.comparingLong(SlotTraffic::count));
        dailyCounts = new LinkedHashMap<>();
        minNSlotsCount = Long.MAX_VALUE;
    }

    private boolean checkInputValid(SlotTraffic input) {
        //Assuming input is always valid for now
        return true;
    }
    public void addSlotTraffic(SlotTraffic input) throws Exception {
        if(!checkInputValid(input)) {
            throw new Exception("Invalid input");
        }
        lastNSlots.addLast(input);
        lastNSlotsCount += input.count();
        if (lastNSlots.size() > MIN_N) {
            lastNSlotsCount -= lastNSlots.pollFirst().count();
        }
        // Check whether the last N time slot are consecutive
        if (lastNSlots.peekFirst().time().plusMinutes(SlotTraffic.INTERVAL_MIN * (MIN_N - 1)).isEqual(input.time())) {
            if (lastNSlotsCount < minNSlotsCount) {
                minNSlotsCount = lastNSlotsCount;
                minNSlotsEnd = input.time();
            }
        }
        maxSlots.add(input);
        if (maxSlots.size() > MAX_N) {
            maxSlots.poll();
        }
        LocalDate date = input.time().toLocalDate();
        dailyCounts.put(date, dailyCounts.getOrDefault(date, 0L) + input.count());
        totalCount += input.count();
    }

    public long getTotalCount() {
        return totalCount;
    }

    public Map<LocalDate, Long> getDailyCounts() {
        return dailyCounts;
    }
    public List<LocalDateTime> getSlotsWithMostTraffic() {
        return maxSlots.stream().map(SlotTraffic::time).collect(Collectors.toList());
    }

    public List<LocalDateTime> getThreeContiguousSlotsWithLeastTraffic() {
        List<LocalDateTime> result = new ArrayList<>();
        if (minNSlotsEnd == null) {
            return result;
        }
        for (int i = 0; i < MIN_N; ++i) {
            result.add(minNSlotsEnd.minusMinutes(SlotTraffic.INTERVAL_MIN * (MIN_N - i - 1)));
        }
        return result;
    }
}
