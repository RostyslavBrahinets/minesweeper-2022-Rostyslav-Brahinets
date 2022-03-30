package minesweeper;

import java.util.Optional;

public class Flag {
    private Matrix flagMap;

    void start() {
        flagMap = new Matrix(Cell.CLOSED);
        for (Coordinate aroundCoordinate : Range.getCoordinatesAround(new Coordinate(4, 4))) {
            flagMap.set(Cell.OPENED, aroundCoordinate);
        }
    }

    Optional<Cell> get(Coordinate coordinate) {
        return flagMap.get(coordinate);
    }
}
