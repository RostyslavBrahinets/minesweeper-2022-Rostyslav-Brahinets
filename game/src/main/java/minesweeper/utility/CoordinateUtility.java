package minesweeper.utility;

public record CoordinateUtility(int x, int y) {
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
