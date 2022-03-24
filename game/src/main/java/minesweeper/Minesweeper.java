package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Minesweeper extends JFrame {
    private final int COLS = 15;
    private final int ROWS = 1;
    private final int GRID_SIZE = 50;
    private JPanel panel;

    private Minesweeper() {
        setImages();
        initPanel();
        initFrame();
    }

    public static void main(String[] args) {
        new Minesweeper();
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Grid grid : Grid.values()) {
                    g.drawImage((Image) grid.image, grid.ordinal() * GRID_SIZE, 0, this);
                }
            }
        };
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

    private void setImages() {
        for (Grid grid : Grid.values()) {
            grid.image = getImage(grid.name().toLowerCase());
        }
    }

    private Image getImage(String name) {
        String fileName = "/images/" + name + ".png";
        ImageIcon imageIcon = new ImageIcon(
            Objects.requireNonNull(Minesweeper.class.getResource(fileName))
        );
        return imageIcon.getImage();
    }
}
