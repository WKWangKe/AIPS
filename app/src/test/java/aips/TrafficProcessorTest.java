package aips;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class TrafficProcessorTest {
    private TrafficProcessor processor;

    @Before
    public void setUp() throws Exception {
        processor = new TrafficProcessor();
        processor.addSlotTraffic(new SlotTraffic(LocalDateTime.of(2021, 12, 1, 5, 0, 0), 10));
        processor.addSlotTraffic(new SlotTraffic(LocalDateTime.of(2021, 12, 1, 5, 30, 0), 3));
        processor.addSlotTraffic(new SlotTraffic(LocalDateTime.of(2021, 12, 1, 6, 0, 0), 4));
        processor.addSlotTraffic(new SlotTraffic(LocalDateTime.of(2021, 12, 1, 6, 30, 0), 7));
        processor.addSlotTraffic(new SlotTraffic(LocalDateTime.of(2021, 12, 1, 7, 30, 0), 1));
        processor.addSlotTraffic(new SlotTraffic(LocalDateTime.of(2021, 12, 1, 8, 0, 0), 2));
        processor.addSlotTraffic(new SlotTraffic(LocalDateTime.of(2021, 12, 2, 8, 0, 0), 100));
    }

    @Test
    public void getTotalCount() {
        assertEquals(127, processor.getTotalCount());
    }

    @Test
    public void getDailyCounts() {
        Map<LocalDate, Long> result = processor.getDailyCounts();
        assertEquals(2, result.size());
        assertEquals(27, result.get(LocalDate.of(2021, 12, 1)).longValue());
        assertEquals(100, result.get(LocalDate.of(2021, 12, 2)).longValue());
    }

    @Test
    public void getSlotsWithMostTraffic() throws Exception {
        TrafficProcessor tp = new TrafficProcessor();
        assertTrue(tp.getSlotsWithMostTraffic().isEmpty());
        tp.addSlotTraffic(new SlotTraffic(LocalDateTime.of(2021, 12, 1, 5, 0, 0), 10));
        assertEquals(1, tp.getSlotsWithMostTraffic().size());
        tp.addSlotTraffic(new SlotTraffic(LocalDateTime.of(2021, 12, 1, 5, 30, 0), 10));
        assertEquals(2, tp.getSlotsWithMostTraffic().size());
        Set<LocalDateTime> actual = new HashSet<>(processor.getSlotsWithMostTraffic());
        assertTrue(actual.contains(LocalDateTime.of(2021, 12, 1, 6, 30, 0)));
        assertTrue(actual.contains(LocalDateTime.of(2021, 12, 1, 5, 0, 0)));
        assertTrue(actual.contains(LocalDateTime.of(2021, 12, 2, 8, 0, 0)));
    }

    @Test
    public void getThreeContiguousSlotsWithLeastTraffic() throws Exception {
        TrafficProcessor tp = new TrafficProcessor();
        assertTrue(tp.getThreeContiguousSlotsWithLeastTraffic().isEmpty());
        tp.addSlotTraffic(new SlotTraffic(LocalDateTime.of(2021, 12, 1, 5, 0, 0), 10));
        assertTrue(tp.getThreeContiguousSlotsWithLeastTraffic().isEmpty());
        tp.addSlotTraffic(new SlotTraffic(LocalDateTime.of(2021, 12, 1, 5, 30, 0), 10));
        assertTrue(tp.getThreeContiguousSlotsWithLeastTraffic().isEmpty());

        LocalDateTime[] expected = {LocalDateTime.of(2021, 12, 1, 5, 30, 0),
                LocalDateTime.of(2021, 12, 1, 6, 0, 0),
                LocalDateTime.of(2021, 12, 1, 6, 30, 0)};
        assertArrayEquals(expected, processor.getThreeContiguousSlotsWithLeastTraffic().toArray(new LocalDateTime[0]));
    }
}