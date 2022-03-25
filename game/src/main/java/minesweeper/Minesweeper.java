package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Optional;

public class Minesweeper extends JFrame {
    private final int COLUMNS = 9;
    private final int ROWS = 9;
    private final int CELL_SIZE = 50;
    private GameController gameController;
    private JPanel panel;

    private Minesweeper() {
        gameController = new GameController(COLUMNS, ROWS);
        gameController.start();
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
                    Optional<Cell> cellOptional = gameController.getCell(coordinate);
                    cellOptional.ifPresent(
                        cell -> g.drawImage(
                            (Image) cell.image,
                            coordinate.getX() * CELL_SIZE,
                            coordinate.getY() * CELL_SIZE,
                            this
                        )
                    );
                }
            }
        };

        Optional<Coordinate> sizeOptional = Range.getSize();
        sizeOptional.ifPresent(size -> panel.setPreferredSize(
            new Dimension(
                size.getX() * CELL_SIZE,
                size.getY() * CELL_SIZE
            )
        ));

        add(panel);
    }

    private void initFrame() {
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        setLocationRelativeTo(null);
        setResizable(false);
        Optional<Image> icon = getImage("icon");
        icon.ifPresent(this::setIconImage);
        setVisible(true);
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
