public class Airplane {
    public static int row;
    public static int col;
    public Seat[][] seats;
    public Passenger[] aisle; 
    // Represents the aisle, with each index representing a position in the aisle. Null if no passenger is there.

    public class Airplane(int row, int col) {
        this.row = row;
        this.col = col;
        seats = new Seat[row][col];
        aisle = new Passenger[row + 1]; // +1 for the position in front of the first row
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                seats[i][j] = new Seat(i, j);
            }
        }
    }

    public boolean isAisleEmpty(int pos) {
        return aisle[pos] == null;
    }

    public void sitPassenger(Passenger p) {
        Seat seat = p.getSeat();
        seat.occupy();
        p.sit();
        aisle[p.getSeat().getRow()] = null; // Remove passenger from aisle
    
}