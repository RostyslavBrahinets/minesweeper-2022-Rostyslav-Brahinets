package minesweeper.utility;

import minesweeper.enums.Cell;

import java.util.Optional;

public class Matrix {
    private Cell[][] matrix;

    public Matrix(Cell defaultCell) {
        Optional<Coordinate> size = Range.getSize();
        if (size.isPresent()) {
            matrix = new Cell[size.get().x()][size.get().y()];

            for (Coordinate coordinate : Range.getCoordinates()) {
                matrix[coordinate.x()][coordinate.y()] = defaultCell;
            }
        }
    }

    public Optional<Cell> get(Coordinate coordinate) {
        Cell cell = null;
        if (Range.inRange(coordinate)) {
            cell = matrix[coordinate.x()][coordinate.y()];
        }
        return Optional.ofNullable(cell);
    }

    public void set(Cell cell, Coordinate coordinate) {
        if (Range.inRange(coordinate)) {
            matrix[coordinate.x()][coordinate.y()] = cell;
        }
    }
}
