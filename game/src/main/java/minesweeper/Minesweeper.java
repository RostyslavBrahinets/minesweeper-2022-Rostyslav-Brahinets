package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Minesweeper extends JFrame {
    private GameController gameController;

    private final int COLUMNS = 9;
    private final int ROWS = 9;
    private final int CELL_SIZE = 50;
    private JPanel panel;

    private Minesweeper() {
        gameController = new GameController(COLUMNS, ROWS);
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
                        (Image) gameController.getCell(coordinate).image,
                        coordinate.getX() * CELL_SIZE,
                        coordinate.getY() * CELL_SIZE,
                        this
                    );
                }
            }
        };

        panel.setPreferredSize(
            new Dimension(
                Range.getSize().getX() * CELL_SIZE,
                Range.getSize().getY() * CELL_SIZE
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
        for (Cell cell : Cell.values()) {
            cell.image = getImage(cell.name().toLowerCase());
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
