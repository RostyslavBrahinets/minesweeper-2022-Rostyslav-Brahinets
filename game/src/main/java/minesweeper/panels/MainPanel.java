package minesweeper.panels;

import minesweeper.controllers.GameController;
import minesweeper.enums.Cell;
import minesweeper.enums.GameState;
import minesweeper.utility.CoordinateUtility;
import minesweeper.utility.MessageUtility;
import minesweeper.utility.RangeUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Optional;

import static minesweeper.enums.GameState.FAILED;
import static minesweeper.enums.GameState.WINNER;

public class MainPanel extends JPanel {
    private final GameController gameController;
    private final InfoPanel infoPanel;
    private final int cellSize;

    public MainPanel(int cellSize, InfoPanel infoPanel, GameController gameController) {
        this.cellSize = cellSize;
        this.gameController = gameController;
        this.infoPanel = infoPanel;
        initPanel();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintCells(g);
    }

    private void initPanel() {
        addListener();

        Optional<CoordinateUtility> sizeOptional = RangeUtility.getSize();
        sizeOptional.ifPresent(size -> setPreferredSize(
            new Dimension(
                size.x() * cellSize,
                size.y() * cellSize
            )
        ));
    }

    private void addListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / cellSize;
                int y = e.getY() / cellSize;
                CoordinateUtility coordinate = new CoordinateUtility(x, y);

                checkClick(e, coordinate);
                Optional<JLabel> counterLabel = infoPanel.getCounterLabel();
                counterLabel.ifPresent(label -> label.setText(
                    "Залишилося ще " + gameController.getCountOfMines() + " мін"
                ));
                repaint();
                checkStateOfGame();
            }
        });
    }

    private void paintCells(Graphics g) {
        for (CoordinateUtility coordinate : RangeUtility.getCoordinates()) {
            Optional<Cell> cellOptional = gameController.getCell(coordinate);
            cellOptional.ifPresent(
                cell -> g.drawImage(
                    (Image) cell.image,
                    coordinate.x() * cellSize,
                    coordinate.y() * cellSize,
                    this
                )
            );
        }
    }

    private void checkClick(MouseEvent e, CoordinateUtility coordinate) {
        if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
            gameController.doubleClickLeftButton(coordinate);
        } else if (e.getButton() == MouseEvent.BUTTON1) {
            gameController.pressLeftButton(coordinate);
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            gameController.pressRightButton(coordinate);
        } else if (e.getButton() == MouseEvent.BUTTON2) {
            gameController.start();
        }
    }

    private void checkStateOfGame() {
        Optional<Timer> timer = infoPanel.getTimer();
        Optional<GameState> state = gameController.getState();
        if (timer.isPresent() && state.isPresent()) {
            if (state.get() == FAILED) {
                Optional<String> messageForLoser = MessageUtility.getMessageForLoser();
                messageForLoser.ifPresent(s -> showMessage(s, false));
                timer.get().stop();
            } else if (state.get() == WINNER) {
                Optional<String> messageForWinner = MessageUtility.getMessageForWinner();
                messageForWinner.ifPresent(s -> showMessage(s, true));
                timer.get().stop();
            }
        }
    }

    private void showMessage(String message, boolean win) {
        JFrame frame = new JFrame();

        JDialog dialog = new JDialog(frame);
        dialog.setLayout(new FlowLayout());

        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel(message);
        Font font = new Font("Arial", Font.PLAIN, 24);
        label.setFont(font);

        if (win) {
            label.setForeground(Color.GREEN);
        } else {
            label.setForeground(Color.RED);
        }

        Optional<JButton> buttonOptional = getButtonNewGame(dialog);

        panel.add(label, BorderLayout.NORTH);
        buttonOptional.ifPresent(jButton -> panel.add(jButton, BorderLayout.SOUTH));

        dialog.add(panel);
        dialog.pack();
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private Optional<JButton> getButtonNewGame(JDialog dialog) {
        JButton button = new JButton("Нова гра");
        button.addActionListener(clicked -> {
            dialog.setVisible(false);
            gameController.start();

            Optional<JLabel> timeLabel = infoPanel.getTimeLabel();
            Optional<JLabel> counterLabel = infoPanel.getCounterLabel();
            Optional<Timer> timer = infoPanel.getTimer();

            timeLabel.ifPresent(label -> label.setText("Час: 0"));
            counterLabel.ifPresent(label -> label.setText(
                "Залишилося ще " + gameController.getCountOfMines() + " мін"
            ));
            timer.ifPresent(Timer::restart);

            infoPanel.setTime(0);
            repaint();
        });

        return Optional.of(button);
    }
}
