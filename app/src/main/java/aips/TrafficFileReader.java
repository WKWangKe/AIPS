package aips;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * This class is responsible to read individual traffic information from a given file
 */
public final class TrafficFileReader {
    Scanner scanner;

    /**
     * Construct a TrafficFileReader for a given filepath
     * @param file input file
     * @throws FileNotFoundException If the input file is not found
     */
    public TrafficFileReader(File file) throws FileNotFoundException {
        scanner = new Scanner(file);
    }

    /** return true if the input file still have content to read
     * @return true if the input file still have content to read
     */
    public boolean hasNext() {
        return scanner.hasNext();
    }

    /**
     * Advances this reader to read the next line and parse it into Slot Traffic
     * The format would be "yyyy-HH-mmThh:mm:ss count". The input format is always valid
     * @return the parsed SlotTraffic object
     */
    public SlotTraffic next() {
        String input = scanner.nextLine();
        String[] tokens = input.split(" ");
        LocalDateTime time = LocalDateTime.parse(tokens[0], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        long count = Long.parseLong(tokens[1]);
        return new SlotTraffic(time, count);
    }

    /**
     * Close the reader to release the file
     */
    public void close() {
        scanner.close();
    }
}
