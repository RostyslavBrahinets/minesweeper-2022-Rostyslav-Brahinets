package minesweeper;

import java.util.Optional;

import static minesweeper.Cell.*;

public class GameController {
    private final Mine mine;
    private final Flag flag;
    private GameState state;

    public GameController(int columns, int rows, int mines) {
        Range.setSize(new Coordinate(columns, rows));
        mine = new Mine(mines);
        flag = new Flag();
    }

    public void start() {
        mine.start();
        flag.start();
        state = GameState.PLAYED;
    }

    public Optional<Cell> getCell(Coordinate coordinate) {
        Optional<Cell> cellOptional = flag.get(coordinate);
        if (cellOptional.isPresent()) {
            if (cellOptional.get() == OPENED) {
                return mine.get(coordinate);
            }
        }
        return flag.get(coordinate);
    }

    public void pressLeftButton(Coordinate coordinate) {
        openCell(coordinate);
        checkWinner();
    }

    public void pressRightButton(Coordinate coordinate) {
        flag.toggleFlagInCell(coordinate);
    }

    public GameState getState() {
        return state;
    }

    private void checkWinner() {
        if (state == GameState.PLAYED) {
            if (flag.getCountOfClosedMines() == mine.getTotalMines()) {
                state = GameState.WINNER;
            }
        }
    }

    private void openCell(Coordinate coordinate) {
        Optional<Cell> flagOptional = flag.get(coordinate);
        Optional<Cell> mineOptional = mine.get(coordinate);

        if (flagOptional.isPresent() && mineOptional.isPresent()) {
            Cell flagCell = flagOptional.get();
            Cell mineCell = mineOptional.get();

            if (flagCell == OPENED || flagCell == FLAG) {
                return;
            } else if (flagCell == CLOSED) {
                if (mineCell == EMPTY) {
                    openCellsAround(coordinate);
                    return;
                } else if (mineCell == MINE) {
                    return;
                } else {
                    flag.setOpenedToCell(coordinate);
                    return;
                }
            }
        }
    }

    private void openCellsAround(Coordinate coordinate) {
        flag.setOpenedToCell(coordinate);
        for (Coordinate coordinateAround : Range.getCoordinatesAround(coordinate)) {
            openCell(coordinateAround);
        }
    }
}
