package minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.Optional;

import static minesweeper.GameState.FAILED;
import static minesweeper.GameState.WINNER;

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
        initCounter();
        initPanel();
        initFrame();
    }

    private void initCounter() {
        String count = MINES + " mines left";
        label = new JLabel(count);
        label.setFont(new Font("Arial", Font.PLAIN, 50));
        label.setSize(new Dimension());

        panel = new JPanel();
        panel.add(label);
        add(panel, BorderLayout.NORTH);
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

                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    gameController.doubleClickLeftButton(coordinate);
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    gameController.pressLeftButton(coordinate);
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    gameController.pressRightButton(coordinate);
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    gameController.start();
                }

                label.setText(gameController.getCountOfMines() + " mines left");

                panel.repaint();

                if (gameController.getState() == FAILED) {
                    showMessage("Sorry! You died!", panel);
                } else if (gameController.getState() == WINNER) {
                    showMessage("Wow! You did it!", panel);
                }
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

    private void showMessage(String message, JPanel panel) {
        JFrame frame = new JFrame();

        JDialog dialog = new JDialog(frame);
        dialog.setLayout(new FlowLayout());
        dialog.setSize(new Dimension(200, 100));
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);

        JButton button = new JButton("New Game");
        button.addActionListener(clicked -> {
            dialog.setVisible(false);
            gameController.start();
            label.setText(gameController.getCountOfMines() + " mines left");
            panel.repaint();
        });

        JLabel label = new JLabel(message);

        dialog.add(label);
        dialog.add(button);
        dialog.setVisible(true);
    }
}
