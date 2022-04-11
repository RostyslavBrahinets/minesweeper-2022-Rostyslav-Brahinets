package minesweeper.utility;

import minesweeper.Minesweeper;
import minesweeper.enums.Cell;
import minesweeper.enums.Level;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Optional;

public class ImageUtility {
    public static void setImages(Level level) {
        for (Cell cell : Cell.values()) {
            Optional<Image> imageOptional = getImage(
                cell.name().toLowerCase(),
                String.valueOf(level)
            );
            imageOptional.ifPresent(image -> cell.image = image);
        }
    }

    public static Optional<Image> getImage(String name, String level) {
        String fileName;
        if (level.isBlank()) {
            fileName = "/images/" + name + ".png";
        } else {
            fileName = "/images/" + level.toLowerCase() + "/" + name + ".png";
        }

        ImageIcon imageIcon = new ImageIcon(
            Objects.requireNonNull(Minesweeper.class.getResource(fileName))
        );

        return Optional.ofNullable(imageIcon.getImage());
    }
}
