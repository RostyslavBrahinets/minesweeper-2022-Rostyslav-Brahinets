package minesweeper;

import java.util.Optional;

public class Flag {
    private Matrix flagMap;
    private int countOfClosedCells;

    void start() {
        flagMap = new Matrix(Cell.CLOSED);
        Optional<Coordinate> size = Range.getSize();
        size.ifPresent(coordinate -> countOfClosedCells = coordinate.getX() * coordinate.getY());
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
                case FLAG -> setClosedToCell(coordinate);
            }
        }

    }

    int getCountOfClosedMines() {
        return countOfClosedCells;
    }

    private void setFlagToCell(Coordinate coordinate) {
        flagMap.set(Cell.FLAG, coordinate);
    }

    private void setClosedToCell(Coordinate coordinate) {
        flagMap.set(Cell.CLOSED, coordinate);
    }
}
