package minesweeper;

public class GameController {
    public GameController(int columns, int rows) {
        Range.setSize(new Coordinate(columns, rows));
    }

    public Grid getGrid(Coordinate coordinate) {
        return Grid.values()[
            (coordinate.getX() + coordinate.getY()) % Grid.values().length
            ];
    }
}
