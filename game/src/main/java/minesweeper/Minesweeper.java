package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.Optional;

public class Minesweeper extends JFrame {
    private final int COLUMNS = 9;
    private final int ROWS = 9;
    private final int CELL_SIZE = 50;
    private final int MINES = 10;
    private final GameController gameController;
    private JLabel label;
    private JPanel panel;

    public Minesweeper() {
        gameController = new GameController(COLUMNS, ROWS, MINES);
    }

    public void start() {
        gameController.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }

    public void initLabel() {
        label = new JLabel("Welcome!");
        add(label, BorderLayout.SOUTH);
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

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / CELL_SIZE;
                int y = e.getY() / CELL_SIZE;
                Coordinate coordinate = new Coordinate(x, y);

                if (e.getButton() == MouseEvent.BUTTON1) {
                    gameController.pressLeftButton(coordinate);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    gameController.pressRightButton(coordinate);
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    gameController.start();
                }

                label.setText(getMessage());
                panel.repaint();
            }
        });

        Optional<Coordinate> sizeOptional = Range.getSize();
        sizeOptional.ifPresent(size -> panel.setPreferredSize(
            new Dimension(
                size.getX() * CELL_SIZE,
                size.getY() * CELL_SIZE
            )
        ));

        add(panel);
    }

    private String getMessage() {
        return switch (gameController.getState()) {
            case PLAYED -> "Find all the mines!";
            case FAILED -> "Sorry! You died!";
            case WINNER -> "Wow! You did it!";
        };
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
