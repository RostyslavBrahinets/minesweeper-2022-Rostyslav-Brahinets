package minesweeper;

import java.util.Optional;

public class GameController {
    public GameController(int columns, int rows) {
        Range.setSize(new Coordinate(columns, rows));
    }

    public Optional<Cell> getCell(Coordinate coordinate) {
        Cell cell = Cell.values()[
            (coordinate.getX() + coordinate.getY()) % Cell.values().length
            ];
        return Optional.ofNullable(cell);
    }
}
