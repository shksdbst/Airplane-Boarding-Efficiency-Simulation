public class Seat {
    int row;
    int col;
    char charColumn; // Character representation of the column (e.g., A, B, C). For String representation if needed.
    boolean occupied;
    int totalCols;

    public Seat(int row, int col) {
        this.row = row;
        this.col = col;
        this.charColumn = ((char) ('A' + col)).toUpperCase().charAt(0);
        this.occupied = false;
        this.totalCols = Airplane.col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void occupy() {
        this.occupied = true;
    }

    public boolean isWindow() {
        return col == 0 || col == totalCols - 1;
    }

    public boolean isMiddle() {
        return col == 1 || col == totalCols - 2;
    }

    public boolean isAisle() {
        return col == 2 || col == totalCols - 3;
    }
}