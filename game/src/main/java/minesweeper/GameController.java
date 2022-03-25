package minesweeper;

import java.util.Optional;

public class GameController {
    private Matrix mineMap;

    public GameController(int columns, int rows) {
        Range.setSize(new Coordinate(columns, rows));
    }

    public void start() {
        mineMap = new Matrix(Cell.EMPTY);
    }

    public Optional<Cell> getCell(Coordinate coordinate) {
        return mineMap.get(coordinate);
    }
}
