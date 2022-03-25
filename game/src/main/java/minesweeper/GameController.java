package minesweeper;

public class GameController {
    public GameController(int columns, int rows) {
        Range.setSize(new Coordinate(columns, rows));
    }

    public Cell getCell(Coordinate coordinate) {
        return Cell.values()[
            (coordinate.getX() + coordinate.getY()) % Cell.values().length
            ];
    }
}
