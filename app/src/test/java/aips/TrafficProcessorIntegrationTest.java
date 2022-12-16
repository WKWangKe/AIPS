package aips;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

public class TrafficProcessorIntegrationTest {
    private TrafficProcessor processor;
    private static final String TEST_FILE_NAME = "sample_input_full.txt";

    @Before
    public void setUp() throws Exception {
        processor = new TrafficProcessor();
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(TEST_FILE_NAME)).getFile());
        TrafficFileReader reader = new TrafficFileReader(file);
        while (reader.hasNext()) {
            processor.addSlotTraffic(reader.next());
        }
    }

    @Test
    public void getTotalCount() {
        assertEquals(398, processor.getTotalCount());
    }

    @Test
    public void getDailyCounts() {
        Map<LocalDate, Long> result = processor.getDailyCounts();
        assertEquals(4, result.size());
        assertEquals(179, result.get(LocalDate.of(2021, 12, 1)).longValue());
        assertEquals(81, result.get(LocalDate.of(2021, 12, 5)).longValue());
        assertEquals(134, result.get(LocalDate.of(2021, 12, 8)).longValue());
        assertEquals(4, result.get(LocalDate.of(2021, 12, 9)).longValue());
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
        assertTrue(actual.contains(LocalDateTime.of(2021, 12, 1, 7, 30, 0)));
        assertTrue(actual.contains(LocalDateTime.of(2021, 12, 1, 8, 0, 0)));
        assertTrue(actual.contains(LocalDateTime.of(2021, 12, 8, 18, 0, 0)));
    }

    @Test
    public void getThreeContiguousSlotsWithLeastTraffic() throws Exception {
        TrafficProcessor tp = new TrafficProcessor();
        assertTrue(tp.getThreeContiguousSlotsWithLeastTraffic().isEmpty());
        tp.addSlotTraffic(new SlotTraffic(LocalDateTime.of(2021, 12, 1, 5, 0, 0), 10));
        assertTrue(tp.getThreeContiguousSlotsWithLeastTraffic().isEmpty());
        tp.addSlotTraffic(new SlotTraffic(LocalDateTime.of(2021, 12, 1, 5, 30, 0), 10));
        assertTrue(tp.getThreeContiguousSlotsWithLeastTraffic().isEmpty());

        LocalDateTime[] expected = {LocalDateTime.of(2021, 12, 1, 5, 0, 0),
                LocalDateTime.of(2021, 12, 1, 5, 30, 0),
                LocalDateTime.of(2021, 12, 1, 6, 0, 0)};
        assertArrayEquals(expected, processor.getThreeContiguousSlotsWithLeastTraffic().toArray(new LocalDateTime[0]));
    }
}