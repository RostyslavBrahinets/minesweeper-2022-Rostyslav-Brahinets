package minesweeper;

import java.util.Optional;

public class GameController {
    private final Mine mine;
    private final Flag flag;

    public GameController(int columns, int rows, int mines) {
        Range.setSize(new Coordinate(columns, rows));
        mine = new Mine(mines);
        flag = new Flag();
    }

    public void start() {
        mine.start();
        flag.start();
    }

    public Optional<Cell> getCell(Coordinate coordinate) {
        Optional<Cell> cellOptional = flag.get(coordinate);
        if (cellOptional.isPresent()) {
            if (cellOptional.get() == Cell.OPENED) {
                return mine.get(coordinate);
            }
        }
        return flag.get(coordinate);
    }
}
