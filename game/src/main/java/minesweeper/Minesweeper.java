package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Minesweeper extends JFrame {
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int GRID_SIZE = 50;
    private JPanel panel;

    private Minesweeper() {
        Range.setSize(new Coordinate(COLS, ROWS));
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
                for (Coordinate coordinate : Range.getCoordinates()) {
                    g.drawImage(
                        (Image) Grid.MINE.image,
                        coordinate.getX() * GRID_SIZE,
                        coordinate.getY() * GRID_SIZE,
                        this
                    );
                }
            }
        };

        panel.setPreferredSize(
            new Dimension(
                Range.getSize().getX() * GRID_SIZE,
                Range.getSize().getY() * GRID_SIZE
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
        setIconImage(getImage("icon"));
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
