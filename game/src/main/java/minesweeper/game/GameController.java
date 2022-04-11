package minesweeper.game;

import minesweeper.components.Flag;
import minesweeper.components.Mine;
import minesweeper.enums.Cell;
import minesweeper.enums.GameState;
import minesweeper.utility.CoordinateUtility;
import minesweeper.utility.RangeUtility;

import java.util.Optional;

public class GameController {
    private final Mine mine;
    private final Flag flag;
    private GameState state;
    private int countOfMines;
    private boolean firstCell;

    public GameController(int columns, int rows, int mines) {
        RangeUtility.setSize(new CoordinateUtility(columns, rows));
        mine = new Mine(mines);
        flag = new Flag();
        firstCell = true;
    }

    public void start() {
        mine.start();
        flag.start();
        state = GameState.PLAYED;
        countOfMines = mine.getTotalMines();
    }

    public Optional<Cell> getCell(CoordinateUtility coordinate) {
        Optional<Cell> cell = flag.get(coordinate);

        if (cell.isPresent()) {
            if (cell.get() == Cell.OPENED) {
                return mine.get(coordinate);
            }
        }

        return flag.get(coordinate);
    }

    public void pressLeftButton(CoordinateUtility coordinate) {
        if (gameOver()) {
            return;
        }

        openCell(coordinate);
        checkWinner();
    }

    public void doubleClickLeftButton(CoordinateUtility coordinate) {
        Optional<Cell> cell = flag.get(coordinate);

        if (cell.isPresent()) {
            if (cell.get() == Cell.OPENED) {
                setOpenedToClosedCellsAroundNumber(coordinate);
            }
        }

        checkWinner();
    }

    public void pressRightButton(CoordinateUtility coordinate) {
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
                countOfMines = 0;
            }
        }
    }

    private void openCell(CoordinateUtility coordinate) {
        Optional<Cell> cellOptional = flag.get(coordinate);
        Optional<Cell> mineOptional = mine.get(coordinate);

        if (cellOptional.isPresent() && mineOptional.isPresent()) {
            Cell cell = cellOptional.get();
            Cell mineCell = mineOptional.get();

            if (cell == Cell.CLOSED || cell == Cell.QUESTION) {
                if (mineCell == Cell.EMPTY) {
                    openCellsAround(coordinate);
                } else if (mineCell == Cell.MINE) {
                    if (firstCell) {
                        openFirstCell(coordinate);
                        firstCell = false;
                    }
                    countOfMines--;
                    openMines(coordinate);
                } else {
                    flag.setOpenedToCell(coordinate);
                }
            }
        }
    }

    private void openFirstCell(CoordinateUtility coordinate) {
        flag.setOpenedToCell(coordinate);
        mine.resetMine(coordinate);
    }

    private void openCellsAround(CoordinateUtility coordinate) {
        flag.setOpenedToCell(coordinate);

        for (CoordinateUtility coordinateAround : RangeUtility.getCoordinatesAround(coordinate)) {
            openCell(coordinateAround);
        }
    }

    private void openMines(CoordinateUtility coordinateWithMine) {
        state = GameState.FAILED;
        flag.setFailToCell(coordinateWithMine);

        for (CoordinateUtility coordinate : RangeUtility.getCoordinates()) {
            Optional<Cell> cell = mine.get(coordinate);

            if (cell.isPresent()) {
                if (cell.get() == Cell.MINE) {
                    flag.setOpenedToClosedMineCell(coordinate);
                } else {
                    flag.setNomineToFlagSafeCell(coordinate);
                }
            }
        }
    }

    private void setOpenedToClosedCellsAroundNumber(CoordinateUtility coordinate) {
        Optional<Cell> cell = mine.get(coordinate);

        if (cell.isPresent()) {
            if (cell.get() != Cell.MINE) {
                if (flag.getCountOfFlagsAround(coordinate) == cell.get().getNumber()) {
                    for (
                        CoordinateUtility coordinateAround :
                        RangeUtility.getCoordinatesAround(coordinate)
                    ) {
                        Optional<Cell> cellAround = flag.get(coordinateAround);

                        if (cellAround.isPresent()) {
                            if (cellAround.get() == Cell.CLOSED
                                || cellAround.get() == Cell.QUESTION) {
                                openCell(coordinateAround);
                            }
                        }
                    }
                }
            }
        }
    }
}
