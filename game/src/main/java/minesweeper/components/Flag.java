package minesweeper.components;

import minesweeper.enums.Cell;
import minesweeper.utility.CoordinateUtility;
import minesweeper.utility.MatrixUtility;
import minesweeper.utility.RangeUtility;

import java.util.Optional;

import static minesweeper.enums.Cell.*;

public class Flag {
    private MatrixUtility flagMap;
    private int countOfClosedCells;

    public void start() {
        flagMap = new MatrixUtility(CLOSED);
        Optional<CoordinateUtility> size = RangeUtility.getSize();
        size.ifPresent(coordinate -> countOfClosedCells = coordinate.x() * coordinate.y());
    }

    public Optional<Cell> get(CoordinateUtility coordinate) {
        return flagMap.get(coordinate);
    }

    public void setOpenedToCell(CoordinateUtility coordinate) {
        flagMap.set(Cell.OPENED, coordinate);
        countOfClosedCells--;
    }

    public void toggleFlagInCell(CoordinateUtility coordinate) {
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

    public void setFailToCell(CoordinateUtility coordinate) {
        flagMap.set(Cell.FAIL, coordinate);
    }

    public void setOpenedToClosedMineCell(CoordinateUtility coordinate) {
        Optional<Cell> cell = flagMap.get(coordinate);
        if (cell.isPresent()) {
            if (cell.get() == CLOSED) {
                flagMap.set(Cell.OPENED, coordinate);
            }
        }
    }

    public void setNomineToFlagSafeCell(CoordinateUtility coordinate) {
        Optional<Cell> cell = flagMap.get(coordinate);
        if (cell.isPresent()) {
            if (cell.get() == FLAG) {
                flagMap.set(Cell.NOMINE, coordinate);
            }
        }
    }

    public int getCountOfClosedCells() {
        return countOfClosedCells;
    }

    public int getCountOfFlagsAround(CoordinateUtility coordinate) {
        int count = 0;
        for (CoordinateUtility coordinateAround : RangeUtility.getCoordinatesAround(coordinate)) {
            Optional<Cell> cell = flagMap.get(coordinateAround);
            if (cell.isPresent()) {
                if (cell.get() == FLAG) {
                    count++;
                }
            }
        }
        return count;
    }

    private void setFlagToCell(CoordinateUtility coordinate) {
        flagMap.set(FLAG, coordinate);
    }

    public void setQuestionToCell(CoordinateUtility coordinate) {
        flagMap.set(QUESTION, coordinate);
    }

    private void setClosedToCell(CoordinateUtility coordinate) {
        flagMap.set(CLOSED, coordinate);
    }
}
