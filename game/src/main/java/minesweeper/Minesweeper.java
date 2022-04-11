package minesweeper;

import minesweeper.panels.InfoPanel;
import minesweeper.panels.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Optional;

public class Minesweeper extends JFrame {
    private final int COLUMNS;
    private final int ROWS;
    private final int CELL_SIZE;
    private final int MINES;
    private final GameController gameController;
    private InfoPanel infoPanel;
    private MainPanel mainPanel;

    public Minesweeper() {
        COLUMNS = 9;
        ROWS = 9;
        CELL_SIZE = 50;
        MINES = 10;
        gameController = new GameController(COLUMNS, ROWS, MINES);
    }

    public void start() {
        gameController.start();
        setImages();
        initInfoPanel();
        initMainPanel();
        initFrame();
    }

    private void initInfoPanel() {
        infoPanel = new InfoPanel(MINES);
        add(infoPanel, BorderLayout.NORTH);
    }

    private void initMainPanel() {
        mainPanel = new MainPanel(CELL_SIZE, infoPanel, gameController);
        add(mainPanel);
    }

    private void initFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        Optional<Image> icon = getImage("icon");
        icon.ifPresent(this::setIconImage);
    }

    private void setImages() {
        for (Cell cell : Cell.values()) {
            Optional<Image> imageOptional = getImage(cell.name().toLowerCase());
            imageOptional.ifPresent(image -> cell.image = image);
        }
    }

    private Optional<Image> getImage(String name) {
        String fileName = "/images/" + name + ".png";
        ImageIcon imageIcon = new ImageIcon(
            Objects.requireNonNull(Minesweeper.class.getResource(fileName))
        );
        return Optional.ofNullable(imageIcon.getImage());
    }
}
