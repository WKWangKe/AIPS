package aips;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TrafficFileReader {
    Scanner scanner;

    public TrafficFileReader(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        scanner = new Scanner(file);
    }

    public boolean hasNext() {
        return scanner.hasNext();
    }
    public SlotTraffic readNext() {
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
