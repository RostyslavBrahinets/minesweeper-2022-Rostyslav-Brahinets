package minesweeper;

import java.util.Optional;

public class Mine {
    private int totalMines;
    private Matrix mineMap;

    public Mine(int totalMines) {
        this.totalMines = totalMines;
        fixMinesCount();
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

    int getTotalMines() {
        return totalMines;
    }

    private void fixMinesCount() {
        Optional<Coordinate> sizeOptional = Range.getSize();
        if (sizeOptional.isPresent()) {
            Coordinate size = sizeOptional.get();
            int maxMines = size.getX() * size.getY() / 2;
            if (totalMines > maxMines) {
                totalMines = maxMines;
            }
        }
    }

    private void placeMine() {
        while (true) {
            Optional<Coordinate> coordinateOptional = Range.getRandomCoordinate();
            if (coordinateOptional.isPresent()) {
                Coordinate coordinate = coordinateOptional.get();
                Optional<Cell> mine = mineMap.get(coordinate);
                if (mine.isPresent()) {
                    if (Cell.MINE == mine.get()) {
                        continue;
                    }
                }
                mineMap.set(Cell.MINE, coordinate);
                incrementNumbersAroundMine(coordinate);
                break;
            }
        }
    }

    private void incrementNumbersAroundMine(Coordinate coordinate) {
        for (Coordinate aroundCoordinate : Range.getCoordinatesAround(coordinate)) {
            Optional<Cell> mineOptional = mineMap.get(aroundCoordinate);
            if (mineOptional.isPresent()) {
                Cell mine = mineOptional.get();
                if (Cell.MINE != mine) {
                    Optional<Cell> numberOptional = mine.getNextNumberCell();
                    numberOptional.ifPresent(number -> mineMap.set(number, aroundCoordinate));
                }
            }
        }
    }
}
