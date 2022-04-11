package minesweeper.components;

import minesweeper.enums.Cell;
import minesweeper.utility.CoordinateUtility;
import minesweeper.utility.MatrixUtility;
import minesweeper.utility.RangeUtility;

import java.util.Optional;

public class Mine {
    private MatrixUtility mineMap;
    private int totalMines;

    public Mine(int totalMines) {
        this.totalMines = totalMines;
        fixMinesCount();
    }

    public void start() {
        mineMap = new MatrixUtility(Cell.EMPTY);
        for (int i = 0; i < totalMines; i++) {
            placeMine();
        }
    }

    public Optional<Cell> get(CoordinateUtility coordinate) {
        return mineMap.get(coordinate);
    }

    public int getTotalMines() {
        return totalMines;
    }

    public void resetMine(CoordinateUtility coordinate) {
        placeMine();
        mineMap.set(Cell.EMPTY, coordinate);
    }

    private void fixMinesCount() {
        Optional<CoordinateUtility> sizeOptional = RangeUtility.getSize();
        if (sizeOptional.isPresent()) {
            CoordinateUtility size = sizeOptional.get();
            int maxMines = size.x() * size.y() / 2;
            if (totalMines > maxMines) {
                totalMines = maxMines;
            }
        }
    }

    private void placeMine() {
        while (true) {
            Optional<CoordinateUtility> coordinateOptional = RangeUtility.getRandomCoordinate();
            if (coordinateOptional.isPresent()) {
                CoordinateUtility coordinate = coordinateOptional.get();

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

    private void incrementNumbersAroundMine(CoordinateUtility coordinate) {
        for (CoordinateUtility aroundCoordinate : RangeUtility.getCoordinatesAround(coordinate)) {
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
