package minesweeper.frames;

import minesweeper.controllers.GameController;
import minesweeper.enums.Level;
import minesweeper.utility.ImageUtility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StartFrame extends JPanel implements ActionListener {
    private static JFrame frame;
    private Level levelOfGame;

    private int COLUMNS;
    private int ROWS;
    private int MINES;
    private int CELL_SIZE;

    public StartFrame() {
        super(new BorderLayout());
        frame = new JFrame();
        levelOfGame = Level.BEGINNER;
        initialLabel();
        initialRadioButtons();
        initialButton();
    }

    public static void showStartFrame() {
        JComponent newContentPane = new StartFrame();
        newContentPane.setOpaque(true);

        frame.setTitle("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(newContentPane);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);

        Optional<Image> icon = ImageUtility.getImage("icon", "");
        icon.ifPresent(frame::setIconImage);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("OK")) {
            setParametersForLevel(levelOfGame);
            frame.setVisible(false);
            startOfGame();
        } else {
            levelOfGame = Level.valueOf(e.getActionCommand());
        }
    }

    private void initialLabel() {
        Font font = new Font("Arial", Font.BOLD, 48);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Select level of game");
        label.setFont(font);
        panel.add(label);
        add(panel, BorderLayout.NORTH);
    }

    private void initialRadioButtons() {
        Font font = new Font("Arial", Font.PLAIN, 32);

        String beginner = "Beginner (10 mines; 9x9 tile grid)";
        String intermediate = "Intermediate (40 mines; 16x16 tile grid)";
        String advanced = "Advanced (99 mines; 16x30 tile grid)";

        List<JRadioButton> radioButtons = new ArrayList<>();
        Optional<JRadioButton> radioButton = getRadioButton(beginner, Level.BEGINNER, font, true);
        radioButton.ifPresent(radioButtons::add);
        radioButton = getRadioButton(intermediate, Level.INTERMEDIATE, font, false);
        radioButton.ifPresent(radioButtons::add);
        radioButton = getRadioButton(advanced, Level.ADVANCED, font, false);
        radioButton.ifPresent(radioButtons::add);

        ButtonGroup group = new ButtonGroup();
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));

        for (JRadioButton button : radioButtons) {
            group.add(button);
            button.addActionListener(this);
            radioPanel.add(button);
        }

        add(radioPanel, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    private void initialButton() {
        Font font = new Font("Arial", Font.BOLD, 48);
        JPanel panel = new JPanel();
        JButton button = new JButton("OK");
        button.setFont(font);
        button.addActionListener(this);
        panel.add(button);
        add(panel, BorderLayout.SOUTH);
    }

    private Optional<JRadioButton> getRadioButton(String text, Level level, Font font, boolean selected) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setActionCommand(String.valueOf(level));
        radioButton.setFont(font);
        radioButton.setSelected(selected);
        return Optional.of(radioButton);
    }

    private void setParametersForLevel(Level level) {
        if (level == Level.BEGINNER) {
            COLUMNS = 9;
            ROWS = 9;
            MINES = 10;
            CELL_SIZE = 50;
        } else if (level == Level.INTERMEDIATE) {
            COLUMNS = 16;
            ROWS = 16;
            MINES = 40;
            CELL_SIZE = 37;
        } else if (level == Level.ADVANCED) {
            COLUMNS = 30;
            ROWS = 16;
            MINES = 99;
            CELL_SIZE = 39;
        }
    }

    private void startOfGame() {
        ImageUtility.setImages(levelOfGame);
        GameController gameController = new GameController(COLUMNS, ROWS, MINES);
        MainFrame gameFrame = new MainFrame(MINES, CELL_SIZE, gameController);
        gameController.start();
        gameFrame.initGameFrame();
    }
}