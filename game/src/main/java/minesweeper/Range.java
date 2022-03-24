package minesweeper;

import java.util.ArrayList;
import java.util.List;

public class Range {
    private static Coordinate size;
    private static List<Coordinate> coordinates;

    public static Coordinate getSize() {
        return size;
    }

    public static void setSize(Coordinate newSize) {
        size = newSize;

        coordinates = new ArrayList<>();
        for (int y = 0; y < size.getY(); y++) {
            for (int x = 0; x < size.getX(); x++) {
                coordinates.add(new Coordinate(x, y));
            }
        }
    }

    public static List<Coordinate> getCoordinates() {
        return coordinates;
    }
}
