package minesweeper;

import minesweeper.game.GameController;
import minesweeper.game.GameFrame;
import minesweeper.utility.ImageUtility;

public class Minesweeper {
    private final int COLUMNS;
    private final int ROWS;
    private final int MINES;
    private final int CELL_SIZE;
    private final GameController gameController;
    private final GameFrame gameFrame;

    public Minesweeper() {
        COLUMNS = 9;
        ROWS = 9;
        MINES = 10;
        CELL_SIZE = 50;
        gameController = new GameController(COLUMNS, ROWS, MINES);
        gameFrame = new GameFrame(MINES, CELL_SIZE, gameController);
    }

    public void start() {
        gameController.start();
        ImageUtility.setImages();
        gameFrame.initGameFrame();
    }
}
