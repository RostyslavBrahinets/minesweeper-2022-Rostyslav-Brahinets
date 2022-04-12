package minesweeper.frames;

import minesweeper.controllers.GameController;
import minesweeper.panels.InfoPanel;
import minesweeper.panels.MainPanel;
import minesweeper.utility.ImageUtility;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class MainFrame extends JFrame {
    private final int mines;
    private final int cellSize;
    private final GameController gameController;
    private InfoPanel infoPanel;

    public MainFrame(int mines, int cellSize, GameController gameController) {
        this.mines = mines;
        this.cellSize = cellSize;
        this.gameController = gameController;
    }

    public void initGameFrame() {
        initInfoPanel();
        initMainPanel();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Розмінуй Україну");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        Optional<Image> icon = ImageUtility.getImage("icon", "");
        icon.ifPresent(this::setIconImage);
    }

    private void initInfoPanel() {
        infoPanel = new InfoPanel(mines);
        add(infoPanel, BorderLayout.NORTH);
    }

    private void initMainPanel() {
        MainPanel mainPanel = new MainPanel(cellSize, infoPanel, gameController);
        add(mainPanel);
    }
}
