package minesweeper;

import java.util.Optional;

public class GameController {
    private final Mine mine;

    public GameController(int columns, int rows, int mines) {
        Range.setSize(new Coordinate(columns, rows));
        mine = new Mine(mines);
    }

    public void start() {
        mine.start();
    }

    public Optional<Cell> getCell(Coordinate coordinate) {
        return mine.get(coordinate);
    }
}
