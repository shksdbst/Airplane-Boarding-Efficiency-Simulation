public class Passenger {
    private String name; // For fun
    private int id;
    private Seat seat;
    private boolean isSeated;
    private PassengerState state;
    private int baggageTime;
    private int interferenceTime; // ticks to wait for neighbor to move
    private int pos; // Position in the aisle, -1 if not in aisle

    public Passenger(int id, Seat seat) {
        name = "DEFAULT_NAME";
        isSeated = false;
        this.id = id;
        this.seat = seat;
        baggageTime = 0; // Default is no one has baggage
        interferenceTime = 1;
        state = PassengerState.MOVING; // Default state
        pos = -1;
    }

    public int getID() {
        return id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setRandomBaggageTime(int start, int end) {
        Random rand = new Random();
        this.baggageTime = rand.nextInt(end - start + 1) + start;
    }

    public void setBaggageTime(int time) {
        this.baggageTime = time;
    }

    public int getBaggageTime() {
        return baggageTime;
    }

    public void setInterferenceTime(int time) {
        this.interferenceTime = time;
    }

    public int getInterferenceTime() {
        return interferenceTime;
    }

    public void sit() {
        isSeated = true;
        state = PassengerState.SEATED;
    }
    
    public boolean isSeated() {
        return isSeated;
    }

    public void changeState(PassengerState newState) {
        this.state = newState;
    }

    public PassengerState getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}