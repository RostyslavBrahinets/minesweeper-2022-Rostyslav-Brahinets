package minesweeper;

import java.util.Optional;

public enum Cell {
    EMPTY,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    MINE,
    OPENED,
    CLOSED,
    FLAG,
    FAIL,
    NOMINE,
    QUESTION;

    public Object image;

    Optional<Cell> getNextNumberCell() {
        return Optional.ofNullable(Cell.values()[this.ordinal() + 1]);
    }

    public int getNumber() {
        return this.ordinal();
    }
}
