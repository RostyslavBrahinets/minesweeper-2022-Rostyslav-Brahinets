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
        for (int y = 0; y < size.y(); y++) {
            for (int x = 0; x < size.x(); x++) {
                coordinates.add(new Coordinate(x, y));
            }
        }
    }

    public static List<Coordinate> getCoordinates() {
        return coordinates;
    }

    static boolean inRange(Coordinate coordinate) {
        return coordinate.x() >= 0
            && coordinate.x() < size.x()
            && coordinate.y() >= 0
            && coordinate.y() < size.y();
    }

    static Optional<Coordinate> getRandomCoordinate() {
        Coordinate coordinate = new Coordinate(
            random.nextInt(size.x()),
            random.nextInt(size.y())
        );

        return Optional.of(coordinate);
    }

    static List<Coordinate> getCoordinatesAround(Coordinate coordinate) {
        Coordinate coordinateAround;
        List<Coordinate> coordinates = new ArrayList<>();

        for (int x = coordinate.x() - 1; x <= coordinate.x() + 1; x++) {
            for (int y = coordinate.y() - 1; y <= coordinate.y() + 1; y++) {
                coordinateAround = new Coordinate(x, y);
                if (inRange(coordinateAround)) {
                    if (!coordinateAround.equals(coordinate)) {
                        coordinates.add(coordinateAround);
                    }
                }
            }
        }

        return coordinates;
    }
}
