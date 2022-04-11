package minesweeper;

import minesweeper.frames.StartFrame;

import javax.swing.*;

public class Minesweeper {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartFrame::showStartFrame);
    }
}
