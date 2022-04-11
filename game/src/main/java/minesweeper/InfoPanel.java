package minesweeper;

import javax.swing.*;
import java.awt.*;

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
    }

    public JLabel getCounterLabel() {
        return counterLabel;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private void initTimer() {
        timeLabel = new JLabel(getTimeMessage(0));
        timeLabel.setFont(font);
        add(timeLabel, BorderLayout.WEST);
        timer = new Timer(1000, e -> timeLabel.setText(getTimeMessage(++time)));
        timer.start();
    }

    private String getTimeMessage(int time) {
        return "Time: " + time;
    }

    private void initCounter(int mines) {
        String count = mines + " mines left";
        counterLabel = new JLabel(count);
        counterLabel.setFont(font);
        add(counterLabel, BorderLayout.EAST);
    }
}
