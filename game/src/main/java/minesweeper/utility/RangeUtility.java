package minesweeper.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class RangeUtility {
    private static final Random random = new Random();
    private static CoordinateUtility size;
    private static List<CoordinateUtility> coordinates;

    public static Optional<CoordinateUtility> getSize() {
        return Optional.ofNullable(size);
    }

    public static void setSize(CoordinateUtility newSize) {
        size = newSize;
        coordinates = new ArrayList<>();

        for (int y = 0; y < size.y(); y++) {
            for (int x = 0; x < size.x(); x++) {
                coordinates.add(new CoordinateUtility(x, y));
            }
        }
    }

    public static List<CoordinateUtility> getCoordinates() {
        return coordinates;
    }

    public static boolean inRange(CoordinateUtility coordinate) {
        return coordinate.x() >= 0
            && coordinate.x() < size.x()
            && coordinate.y() >= 0
            && coordinate.y() < size.y();
    }

    public static Optional<CoordinateUtility> getRandomCoordinate() {
        CoordinateUtility coordinate = new CoordinateUtility(
            random.nextInt(size.x()),
            random.nextInt(size.y())
        );

        return Optional.of(coordinate);
    }

    public static List<CoordinateUtility> getCoordinatesAround(CoordinateUtility coordinate) {
        CoordinateUtility coordinateAround;
        List<CoordinateUtility> coordinates = new ArrayList<>();

        for (int x = coordinate.x() - 1; x <= coordinate.x() + 1; x++) {
            for (int y = coordinate.y() - 1; y <= coordinate.y() + 1; y++) {
                coordinateAround = new CoordinateUtility(x, y);
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
