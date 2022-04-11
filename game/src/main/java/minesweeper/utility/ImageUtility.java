package minesweeper.utility;

import minesweeper.Minesweeper;
import minesweeper.enums.Cell;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Optional;

public class ImageUtility {
    public static void setImages() {
        for (Cell cell : Cell.values()) {
            Optional<Image> imageOptional = getImage(cell.name().toLowerCase());
            imageOptional.ifPresent(image -> cell.image = image);
        }
    }

    public static Optional<Image> getImage(String name) {
        String fileName = "/images/" + name + ".png";
        ImageIcon imageIcon = new ImageIcon(
            Objects.requireNonNull(Minesweeper.class.getResource(fileName))
        );
        return Optional.ofNullable(imageIcon.getImage());
    }
}
