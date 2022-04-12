package minesweeper.panels;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class InfoPanel extends JPanel {
    private final Font font;
    private JLabel counterLabel;
    private JLabel timeLabel;
    private Timer timer;
    private int time;

    public InfoPanel(int mines) {
        font = new Font("Arial", Font.PLAIN, 24);
        setLayout(new BorderLayout());
        SwingUtilities.invokeLater(this::initTimer);
        initCounter(mines);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public Optional<JLabel> getCounterLabel() {
        return Optional.ofNullable(counterLabel);
    }

    public Optional<JLabel> getTimeLabel() {
        return Optional.ofNullable(timeLabel);
    }

    public Optional<Timer> getTimer() {
        return Optional.ofNullable(timer);
    }

    public void setTime(int time) {
        this.time = time;
    }

    private void initTimer() {
        timeLabel = new JLabel("Час: " + time);
        timeLabel.setFont(font);
        add(timeLabel, BorderLayout.WEST);
        timer = new Timer(
            1000, e -> timeLabel.setText("Час: " + ++time)
        );
        timer.start();
    }

    private void initCounter(int mines) {
        String count = "Залишилося ще " + mines + " мін";
        counterLabel = new JLabel(count);
        counterLabel.setFont(font);
        add(counterLabel, BorderLayout.EAST);
    }
}
