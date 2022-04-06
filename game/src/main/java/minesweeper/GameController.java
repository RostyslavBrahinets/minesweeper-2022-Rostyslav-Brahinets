package minesweeper;

import java.util.Optional;

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
        Optional<Cell> cell = flag.get(coordinate);
        if (cell.isPresent()) {
            if (cell.get() == Cell.OPENED) {
                return mine.get(coordinate);
            }
        }
        return flag.get(coordinate);
    }

    public void pressLeftButton(Coordinate coordinate) {
        if (gameOver()) {
            return;
        }

        openCell(coordinate);
        checkWinner();
    }

    public void pressRightButton(Coordinate coordinate) {
        if (gameOver()) {
            return;
        }

        flag.toggleFlagInCell(coordinate);
    }

    public GameState getState() {
        return state;
    }

    private boolean gameOver() {
        if (state == GameState.PLAYED) {
            return false;
        }
        start();
        return true;
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

            if (flagCell == Cell.OPENED) {
                setOpenedToClosedCellsAroundNumber(coordinate);
                return;
            } else if (flagCell == Cell.FLAG) {
                return;
            } else if (flagCell == Cell.CLOSED) {
                if (mineCell == Cell.EMPTY) {
                    openCellsAround(coordinate);
                    return;
                } else if (mineCell == Cell.MINE) {
                    openMines(coordinate);
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

    private void openMines(Coordinate coordinateWithMine) {
        state = GameState.FAILED;
        flag.setFailToCell(coordinateWithMine);

        for (Coordinate coordinate : Range.getCoordinates()) {
            Optional<Cell> mine = this.mine.get(coordinate);
            if (mine.isPresent()) {
                if (mine.get() == Cell.MINE) {
                    flag.setOpenedToClosedMineCell(coordinate);
                } else {
                    flag.setNomineToFlagSafeCell(coordinate);
                }
            }
        }
    }

    private void setOpenedToClosedCellsAroundNumber(Coordinate coordinate) {
        Optional<Cell> cell = mine.get(coordinate);
        if (cell.isPresent()) {
            if (cell.get() != Cell.MINE) {
                if (flag.getCountOfFlagsAround(coordinate) == cell.get().getNumber()) {
                    for (Coordinate coordinateAround : Range.getCoordinatesAround(coordinate)) {
                        Optional<Cell> cellAround = flag.get(coordinateAround);
                        if (cellAround.isPresent()) {
                            if (cellAround.get() == Cell.CLOSED) {
                                openCell(coordinateAround);
                            }
                        }
                    }
                }
            }
        }
    }
}
