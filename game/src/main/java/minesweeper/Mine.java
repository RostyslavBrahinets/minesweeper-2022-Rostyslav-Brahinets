package minesweeper;

import java.util.Optional;

public class Mine {
    private final int totalMines;
    private Matrix mineMap;

    public Mine(int totalMines) {
        this.totalMines = totalMines;
    }

    void start() {
        mineMap = new Matrix(Cell.EMPTY);
        for (int i = 0; i < totalMines; i++) {
            placeMine();
        }
    }

    Optional<Cell> get(Coordinate coordinate) {
        return mineMap.get(coordinate);
    }

    private void placeMine() {
        Optional<Coordinate> coordinateOptional = Range.getRandomCoordinate();
        if (coordinateOptional.isPresent()) {
            Coordinate coordinate = coordinateOptional.get();
            mineMap.set(Cell.MINE, coordinate);
            for (Coordinate aroundCoordinate : Range.getCoordinatesAround(coordinate)) {
                mineMap.set(Cell.NUM1, aroundCoordinate);
            }
        }
    }
}
