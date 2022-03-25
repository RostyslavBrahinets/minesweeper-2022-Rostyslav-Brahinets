package minesweeper;

import java.util.Optional;

public class Matrix {
    private Cell[][] matrix;

    public Matrix(Cell defaultCell) {
        Optional<Coordinate> sizeOptional = Range.getSize();
        if (sizeOptional.isPresent()) {
            matrix = new Cell[sizeOptional.get().getX()][sizeOptional.get().getY()];

            for (Coordinate coordinate : Range.getCoordinates()) {
                matrix[coordinate.getX()][coordinate.getY()] = defaultCell;
            }
        }
    }

    Optional<Cell> get(Coordinate coordinate) {
        Cell cell = null;
        if (Range.inRange(coordinate)) {
            cell = matrix[coordinate.getX()][coordinate.getY()];
        }
        return Optional.ofNullable(cell);
    }

    void set(Cell cell, Coordinate coordinate) {
        if (Range.inRange(coordinate)) {
            matrix[coordinate.getX()][coordinate.getY()] = cell;
        }
    }
}
