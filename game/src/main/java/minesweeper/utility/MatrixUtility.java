package minesweeper.utility;

import minesweeper.enums.Cell;

import java.util.Optional;

public class MatrixUtility {
    private Cell[][] matrix;

    public MatrixUtility(Cell defaultCell) {
        Optional<CoordinateUtility> size = RangeUtility.getSize();
        if (size.isPresent()) {
            matrix = new Cell[size.get().x()][size.get().y()];

            for (CoordinateUtility coordinate : RangeUtility.getCoordinates()) {
                matrix[coordinate.x()][coordinate.y()] = defaultCell;
            }
        }
    }

    public Optional<Cell> get(CoordinateUtility coordinate) {
        Cell cell = null;
        if (RangeUtility.inRange(coordinate)) {
            cell = matrix[coordinate.x()][coordinate.y()];
        }
        return Optional.ofNullable(cell);
    }

    public void set(Cell cell, CoordinateUtility coordinate) {
        if (RangeUtility.inRange(coordinate)) {
            matrix[coordinate.x()][coordinate.y()] = cell;
        }
    }
}
