package minesweeper;

import java.util.Optional;

public class Mine {
    private Matrix mineMap;
    private final int totalMines;

    public Mine(int totalMines) {
        this.totalMines = totalMines;
    }

    void start() {
        mineMap = new Matrix(Cell.EMPTY);
        placeMine();
    }

    Optional<Cell> get(Coordinate coordinate) {
        return mineMap.get(coordinate);
    }

    private void placeMine() {
        mineMap.set(Cell.MINE, new Coordinate(4, 4));
    }
}
