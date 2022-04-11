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
        font = new Font("Arial", Font.PLAIN, 36);
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
        Optional<String> messageOptional = getTimeMessage(0);
        messageOptional.ifPresent(message -> timeLabel = new JLabel(message));
        timeLabel.setFont(font);
        add(timeLabel, BorderLayout.WEST);
        messageOptional = getTimeMessage(++time);
        messageOptional.ifPresent(message -> timer = new Timer(
            1000, e -> timeLabel.setText(message)
        ));
        timer.start();
    }

    private Optional<String> getTimeMessage(int time) {
        return Optional.of("Time: " + time);
    }

    private void initCounter(int mines) {
        String count = mines + " mines left";
        counterLabel = new JLabel(count);
        counterLabel.setFont(font);
        add(counterLabel, BorderLayout.EAST);
    }
}
