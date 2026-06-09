### Corrected final file list

**Java (20 files)**
```
Main.java
BoardingStrategyType.java
PassengerState.java
Seat.java
Passenger.java
PassengerFactory.java
Airplane.java
BoardingStrategy.java
RandomStrategy.java
BackToFrontStrategy.java
OutsideInStrategy.java
SteffenStrategy.java
BlockStrategy.java
NoAssignedSeatsStrategy.java
BoardingStrategyFactory.java
PassengerSnapshot.java
TickSnapshot.java
SimulationResult.java
Simulator.java
JsonExporter.java
```

Wait — that's 21. Let me recount... yes, **21 Java files**.

**Frontend (3 files)**
```
index.html
style.css
script.js
```

**Generated output (1 file)**
```
simulation_output.json
```

**Project config (1 file)**
```
pom.xml
```

---

### Final folder structure
```
boarding-sim/
├── pom.xml
├── src/
│   └── main/
│       └── java/
│           └── (21 Java files)
├── frontend/
│   ├── index.html
│   ├── style.css
│   └── script.js
└── output/
    └── simulation_output.json
```


-------------------------------------------------------------------------------------------------------------------


### JAVA Class Overview
---

### `BoardingStrategyType.java` *(enum)*
```java
enum BoardingStrategyType {
    RANDOM,
    BACK_TO_FRONT,
    OUTSIDE_IN,
    STEFFEN,
    BLOCK,
    NO_ASSIGNED_SEATS
}
```

---

### `PassengerState.java` *(enum)*
```java
enum PassengerState {
    MOVING,
    STOWING_BAGGAGE,
    WAITING_INTERFERENCE,
    SEATED
}
```

---

### `Seat.java`
```java
class Seat {
    int row;          // 0–29
    int col;          // 0–5
    boolean occupied;
}
```

---

### `Passenger.java`
```java
class Passenger {
    int id;
    Seat targetSeat;
    int aislePosition;      // current row position in aisle
    int baggageTime;        // 0 if no baggage, else ticks remaining
    int interferenceTime;  // ticks to wait for neighbor to move
    boolean isSeated;
    PassengerState state;
}
```

---

### `PassengerFactory.java`
```java
class PassengerFactory {
    // creates all 180 passengers
    // assigns each a unique targetSeat from Airplane's seat grid
    // randomly assigns baggageTime using baggageProbability and baggageTimeTicks
    static List<Passenger> createPassengers(Airplane airplane,
                                            double baggageProbability,
                                            int baggageTimeTicks) { ... }
}
```

---

### `Airplane.java`
```java
class Airplane {
    static final int ROWS = 30;
    static final int COLS = 6;
    Seat[][] seats;        // [30][6]
    Passenger[] aisle;     // one slot per row, null if empty

    Airplane() { ... }
    boolean isAislePositionFree(int row) { ... }
    void seatPassenger(Passenger p) { ... }
    boolean hasInterference(Passenger p) { ... }
    void reset() { ... }
}
```

---

### `BoardingStrategy.java` *(interface)*
```java
interface BoardingStrategy {
    Queue<Passenger> generateQueue(List<Passenger> passengers);
}
```

---

### `RandomStrategy.java`
```java
// shuffles all passengers into a random order
class RandomStrategy implements BoardingStrategy { ... }
```

---

### `BackToFrontStrategy.java`
```java
// sorts passengers by row descending (row 29 boards first)
class BackToFrontStrategy implements BoardingStrategy { ... }
```

---

### `OutsideInStrategy.java`
```java
// window seats first, then middle, then aisle
class OutsideInStrategy implements BoardingStrategy { ... }
```

---

### `SteffenStrategy.java`
```java
// window seats back to front, every other row — theoretically fastest
class SteffenStrategy implements BoardingStrategy { ... }
```

---

### `BlockStrategy.java`
```java
// divides plane into 3 zones, boards back zone randomly first,
// then middle zone, then front zone
class BlockStrategy implements BoardingStrategy { ... }
```

---

### `NoAssignedSeatsStrategy.java`
```java
// passengers have no targetSeat pre-assigned —
// they sit in the first available seat when they reach it
class NoAssignedSeatsStrategy implements BoardingStrategy { ... }
```

---

### `BoardingStrategyFactory.java`
```java
class BoardingStrategyFactory {
    static BoardingStrategy getStrategy(BoardingStrategyType type) {
        return switch (type) {
            case RANDOM            -> new RandomStrategy();
            case BACK_TO_FRONT     -> new BackToFrontStrategy();
            case OUTSIDE_IN        -> new OutsideInStrategy();
            case STEFFEN           -> new SteffenStrategy();
            case BLOCK             -> new BlockStrategy();
            case NO_ASSIGNED_SEATS -> new NoAssignedSeatsStrategy();
        };
    }
}
```

---

### `PassengerSnapshot.java`
```java
// lightweight per-tick snapshot of a passenger for JSON export
class PassengerSnapshot {
    int id;
    int aislePos;
    String state;
    int targetRow;
    int targetCol;
    int baggageTimeLeft;
}
```

---

### `TickSnapshot.java`
```java
// one frame of the simulation — maps to one entry in tickLog JSON array
class TickSnapshot {
    int tick;
    List<PassengerSnapshot> aisle;  // passengers currently in aisle
    List<Integer> queue;            // IDs of passengers still waiting to board
    List<Integer> newlySeated;      // IDs who sat down this tick
}
```

---

### `SimulationResult.java`
```java
// full output of one simulation run
class SimulationResult {
    BoardingStrategyType strategy;
    double baggageProbability;
    int totalTicks;
    int bottleneckTicks;
    int seatedCount;
    List<TickSnapshot> tickLog;
    List<Passenger> manifest;  // static passenger data, used by JS as a lookup table
}
```

---

### `Simulator.java`
```java
class Simulator {
    BoardingStrategyType strategyType;
    double baggageProbability;
    int baggageTimeTicks;
    Airplane airplane;
    Queue<Passenger> boardingQueue;
    List<Passenger> inAisle;
    List<TickSnapshot> tickLog;
    int totalTicks;
    int bottleneckTicks;

    Simulator(BoardingStrategyType strategyType,
              double baggageProbability,
              int baggageTimeTicks) { ... }

    void initialize() { ... }     // calls PassengerFactory + BoardingStrategyFactory
    void tick() { ... }           // one tick: admit → advance → stow → seat
    boolean isComplete() { ... }  // true when all 180 passengers are seated
    SimulationResult run() { ... }
}
```

---

### `JsonExporter.java`
```java
class JsonExporter {
    // serializes SimulationResult to simulation_output.json using Gson
    void export(SimulationResult result, String filePath) { ... }
}
```

---

### `Main.java`
```java
class Main {
    public static void main(String[] args) {
        // reads strategy, baggageProbability, baggageTimeTicks from frontend JSON
        // constructs Simulator with those values
        // calls sim.run() to get SimulationResult
        // calls JsonExporter to write simulation_output.json
    }
}
```

---

### Class Diagram

```
Main
 └── Simulator (strategyType, baggageProbability, baggageTimeTicks)
      ├── PassengerFactory ──────────────► Passenger
                                             └── PassengerState
      │                                      └── PassengerState (enum)
      ├── Airplane ──────────────────────► Seat[][]
      ├── BoardingStrategyFactory
      │    └── BoardingStrategy (interface)
      │         ├── RandomStrategy
      │         ├── BackToFrontStrategy
      │         ├── OutsideInStrategy
      │         ├── SteffenStrategy
      │         ├── BlockStrategy
      │         └── NoAssignedSeatsStrategy
      └── TickSnapshot
           └── PassengerSnapshot
 └── SimulationResult
      └── BoardingStrategyType (enum)
 └── JsonExporter
```