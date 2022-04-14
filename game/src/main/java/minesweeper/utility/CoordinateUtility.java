package minesweeper.utility;

public class CoordinateUtility {
    private final int x;
    private final int y;

    public CoordinateUtility(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoordinateUtility that = (CoordinateUtility) o;
        return x == that.x && y == that.y;
    }
}
