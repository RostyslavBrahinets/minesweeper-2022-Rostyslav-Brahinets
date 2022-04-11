package minesweeper;

import java.util.Optional;

public class Flag {
    private Matrix flagMap;
    private int countOfClosedCells;

    void start() {
        flagMap = new Matrix(Cell.CLOSED);
        Optional<Coordinate> size = Range.getSize();
        size.ifPresent(coordinate -> countOfClosedCells = coordinate.x() * coordinate.y());
    }

    Optional<Cell> get(Coordinate coordinate) {
        return flagMap.get(coordinate);
    }

    void setOpenedToCell(Coordinate coordinate) {
        flagMap.set(Cell.OPENED, coordinate);
        countOfClosedCells--;
    }

    void toggleFlagInCell(Coordinate coordinate) {
        Optional<Cell> cell = flagMap.get(coordinate);
        if (cell.isPresent()) {
            switch (cell.get()) {
                case CLOSED -> setFlagToCell(coordinate);
                case FLAG -> setQuestionToCell(coordinate);
                case QUESTION -> setClosedToCell(coordinate);
            }
        }

    }

    void setFailToCell(Coordinate coordinate) {
        flagMap.set(Cell.FAIL, coordinate);
    }

    int getCountOfClosedCells() {
        return countOfClosedCells;
    }

    void setOpenedToClosedMineCell(Coordinate coordinate) {
        Optional<Cell> cell = flagMap.get(coordinate);
        if (cell.isPresent()) {
            if (cell.get() == Cell.CLOSED) {
                flagMap.set(Cell.OPENED, coordinate);
            }
        }
    }

    void setNomineToFlagSafeCell(Coordinate coordinate) {
        Optional<Cell> cell = flagMap.get(coordinate);
        if (cell.isPresent()) {
            if (cell.get() == Cell.FLAG) {
                flagMap.set(Cell.NOMINE, coordinate);
            }
        }
    }

    int getCountOfFlagsAround(Coordinate coordinate) {
        int count = 0;
        for (Coordinate coordinateAround : Range.getCoordinatesAround(coordinate)) {
            Optional<Cell> cell = flagMap.get(coordinateAround);
            if (cell.isPresent()) {
                if (cell.get() == Cell.FLAG) {
                    count++;
                }
            }
        }
        return count;
    }

    private void setFlagToCell(Coordinate coordinate) {
        flagMap.set(Cell.FLAG, coordinate);
    }

    public void setQuestionToCell(Coordinate coordinate) {
        flagMap.set(Cell.QUESTION, coordinate);
    }

    private void setClosedToCell(Coordinate coordinate) {
        flagMap.set(Cell.CLOSED, coordinate);
    }
}
