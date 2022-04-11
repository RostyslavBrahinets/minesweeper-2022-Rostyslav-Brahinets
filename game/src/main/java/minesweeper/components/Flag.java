package minesweeper.components;

import minesweeper.enums.Cell;
import minesweeper.utility.Coordinate;
import minesweeper.utility.Matrix;
import minesweeper.utility.Range;

import java.util.Optional;

import static minesweeper.enums.Cell.*;

public class Flag {
    private Matrix flagMap;
    private int countOfClosedCells;

    public void start() {
        flagMap = new Matrix(CLOSED);
        Optional<Coordinate> size = Range.getSize();
        size.ifPresent(coordinate -> countOfClosedCells = coordinate.x() * coordinate.y());
    }

    public Optional<Cell> get(Coordinate coordinate) {
        return flagMap.get(coordinate);
    }

    public void setOpenedToCell(Coordinate coordinate) {
        flagMap.set(Cell.OPENED, coordinate);
        countOfClosedCells--;
    }

    public void toggleFlagInCell(Coordinate coordinate) {
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

    public void setFailToCell(Coordinate coordinate) {
        flagMap.set(Cell.FAIL, coordinate);
    }

    public void setOpenedToClosedMineCell(Coordinate coordinate) {
        Optional<Cell> cell = flagMap.get(coordinate);
        if (cell.isPresent()) {
            if (cell.get() == CLOSED) {
                flagMap.set(Cell.OPENED, coordinate);
            }
        }
    }

    public void setNomineToFlagSafeCell(Coordinate coordinate) {
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

    public int getCountOfFlagsAround(Coordinate coordinate) {
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
