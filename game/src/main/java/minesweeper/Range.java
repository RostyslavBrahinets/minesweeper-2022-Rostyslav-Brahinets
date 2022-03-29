package minesweeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Range {
    private static final Random random = new Random();
    private static Coordinate size;
    private static List<Coordinate> coordinates;

    public static Optional<Coordinate> getSize() {
        return Optional.ofNullable(size);
    }

    static void setSize(Coordinate newSize) {
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

    static boolean inRange(Coordinate coordinate) {
        return coordinate.getX() >= 0
            && coordinate.getX() < size.getX()
            && coordinate.getY() >= 0
            && coordinate.getY() < size.getY();
    }

    static Optional<Coordinate> getRandomCoordinate() {
        Coordinate coordinate = new Coordinate(
            random.nextInt(size.getX()),
            random.nextInt(size.getY())
        );

        return Optional.of(coordinate);
    }
}
