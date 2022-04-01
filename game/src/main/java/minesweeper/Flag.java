package minesweeper;

import java.util.Optional;

public class Flag {
    private Matrix flagMap;

    void start() {
        flagMap = new Matrix(Cell.CLOSED);
    }

    Optional<Cell> get(Coordinate coordinate) {
        return flagMap.get(coordinate);
    }

    public void setOpenedToCell(Coordinate coordinate) {
        flagMap.set(Cell.OPENED, coordinate);
    }

    public void toggleFlagInCell(Coordinate coordinate) {
        Optional<Cell> cell = flagMap.get(coordinate);
        if (cell.isPresent()) {
            switch (cell.get()) {
                case CLOSED -> setFlagToCell(coordinate);
                case FLAG -> setClosedToCell(coordinate);
            }
        }

    }

    public void setFlagToCell(Coordinate coordinate) {
        flagMap.set(Cell.FLAG, coordinate);
    }

    private void setClosedToCell(Coordinate coordinate) {
        flagMap.set(Cell.CLOSED, coordinate);
    }
}
