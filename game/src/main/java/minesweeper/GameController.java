package minesweeper;

import java.util.Optional;

public class GameController {
    private final Mine mine;
    private final Flag flag;
    private GameState state;
    private int countOfMines;

    public GameController(int columns, int rows, int mines) {
        Range.setSize(new Coordinate(columns, rows));
        mine = new Mine(mines);
        flag = new Flag();
    }

    public void start() {
        mine.start();
        flag.start();
        state = GameState.PLAYED;
        countOfMines = mine.getTotalMines();
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

    public void doubleClickLeftButton(Coordinate coordinate) {
        Optional<Cell> cell = flag.get(coordinate);
        if (cell.isPresent()) {
            if (cell.get() == Cell.OPENED) {
                setOpenedToClosedCellsAroundNumber(coordinate);
            }
        }
        checkWinner();
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

        Optional<Cell> cell = getCell(coordinate);
        if (cell.isPresent()) {
            if (cell.get() == Cell.FLAG) {
                countOfMines--;
            } else if (cell.get() == Cell.QUESTION) {
                countOfMines++;
            }
        }
    }

    public GameState getState() {
        return state;
    }

    public int getCountOfMines() {
        return countOfMines;
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
            if (flag.getCountOfClosedCells() == mine.getTotalMines()) {
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

            if (flagCell == Cell.OPENED || flagCell == Cell.FLAG) {
                return;
            } else if (flagCell == Cell.CLOSED) {
                if (mineCell == Cell.EMPTY) {
                    openCellsAround(coordinate);
                    return;
                } else if (mineCell == Cell.MINE) {
                    countOfMines--;
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
