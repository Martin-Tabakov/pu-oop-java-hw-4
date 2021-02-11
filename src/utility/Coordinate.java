package utility;

public class Coordinate {
    private int row;
    private int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * Checks whether two coordinates are equal - both row and col have the same values on the passed Coordinates
     * @param a Coordinate a
     * @param b Coordinate b
     * @return true if their values are both equal respectably
     */
    public static boolean isEqual(Coordinate a, Coordinate b) {
        return a.getCol() == b.getCol() && a.getRow() == b.getRow();
    }
}
