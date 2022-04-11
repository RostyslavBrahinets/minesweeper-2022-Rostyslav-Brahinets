package minesweeper;

import java.util.Optional;

import static minesweeper.Cell.*;

public class Flag {
    private Matrix flagMap;
    private int countOfClosedCells;

    void start() {
        flagMap = new Matrix(CLOSED);
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
            if (cell.get() == CLOSED) {
                setFlagToCell(coordinate);
            } else if (cell.get() == FLAG) {
                setQuestionToCell(coordinate);
            } else if (cell.get() == QUESTION) {
                setClosedToCell(coordinate);
            }
        }
    }

    void setFailToCell(Coordinate coordinate) {
        flagMap.set(Cell.FAIL, coordinate);
    }

    void setOpenedToClosedMineCell(Coordinate coordinate) {
        Optional<Cell> cell = flagMap.get(coordinate);
        if (cell.isPresent()) {
            if (cell.get() == CLOSED) {
                flagMap.set(Cell.OPENED, coordinate);
            }
        }
    }

    void setNomineToFlagSafeCell(Coordinate coordinate) {
        Optional<Cell> cell = flagMap.get(coordinate);
        if (cell.isPresent()) {
            if (cell.get() == FLAG) {
                flagMap.set(Cell.NOMINE, coordinate);
            }
        }
    }

    int getCountOfClosedCells() {
        return countOfClosedCells;
    }

    int getCountOfFlagsAround(Coordinate coordinate) {
        int count = 0;
        for (Coordinate coordinateAround : Range.getCoordinatesAround(coordinate)) {
            Optional<Cell> cell = flagMap.get(coordinateAround);
            if (cell.isPresent()) {
                if (cell.get() == FLAG) {
                    count++;
                }
            }
        }
        return count;
    }

    private void setFlagToCell(Coordinate coordinate) {
        flagMap.set(FLAG, coordinate);
    }

    public void setQuestionToCell(Coordinate coordinate) {
        flagMap.set(QUESTION, coordinate);
    }

    private void setClosedToCell(Coordinate coordinate) {
        flagMap.set(CLOSED, coordinate);
    }
}
