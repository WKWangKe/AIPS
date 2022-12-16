package aips;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertArrayEquals;

public class TrafficFileReaderTest {

    private static final String TEST_FILE_NAME = "sample_input.txt";

    @Test
    public void parse() throws FileNotFoundException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(TEST_FILE_NAME)).getFile());
        TrafficFileReader reader = new TrafficFileReader(file);
        List<SlotTraffic> result = new ArrayList<>();
        while (reader.hasNext()) {
            result.add(reader.next());
        }
        SlotTraffic[] expected = {new SlotTraffic(LocalDateTime.of(2021, 12, 1, 5, 0, 0), 5),
                new SlotTraffic(LocalDateTime.of(2021, 12, 9, 0, 0, 0), 4)};
        assertArrayEquals(expected, result.toArray());
    }
}