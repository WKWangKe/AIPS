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

    public TrafficFileReader(File file) throws FileNotFoundException {
        scanner = new Scanner(file);
    }

    public boolean hasNext() {
        return scanner.hasNext();
    }

    public SlotTraffic next() {
        String input = scanner.nextLine();
        String[] tokens = input.split(" ");
        LocalDateTime time = LocalDateTime.parse(tokens[0], DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        long count = Long.parseLong(tokens[1]);
        return new SlotTraffic(time, count);
    }

    public void close() {
        scanner.close();
    }
}
