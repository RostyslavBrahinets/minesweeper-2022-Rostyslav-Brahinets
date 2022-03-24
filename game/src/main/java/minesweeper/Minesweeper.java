package minesweeper;

import javax.swing.*;
import java.awt.*;

public class Minesweeper extends JFrame {
    private final int COLS = 15;
    private final int ROWS = 1;
    private final int GRID_SIZE = 50;
    private JPanel panel;

    private Minesweeper() {
        initPanel();
        initFrame();
    }

    public static void main(String[] args) {
        new Minesweeper();
    }

    private void initPanel() {
        panel = new JPanel();
        panel.setPreferredSize(
            new Dimension(
                COLS * GRID_SIZE,
                ROWS * GRID_SIZE
            )
        );
        add(panel);
    }

    private void initFrame() {
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
