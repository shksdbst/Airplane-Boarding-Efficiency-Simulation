Data models first, then logic, then output, then frontend.
* Treat the 'Day's like numberings, not literal days (I'm NOT doing this in 14 days)
---

### Phase 1 — Data Models
These have no dependencies, so write them first.
```
Day 1    Seat.java
Day 1    Passenger.java
Day 1    PassengerState.java        ← enum, 10 lines
Day 1    BoardingStrategyType.java  ← enum, 10 lines
```

---

### Phase 2 — Core Environment
```
Day 2    Airplane.java
Day 2    PassengerFactory.java
```

---

### Phase 3 — Strategies
All implement the same interface so write them one per sitting.
```
Day 3    BoardingStrategy.java      ← interface first
Day 3    RandomStrategy.java
Day 4    BackToFrontStrategy.java
Day 4    OutsideInStrategy.java
Day 5    SteffenStrategy.java
Day 5    BlockStrategy.java
Day 5    NoAssignedSeatsStrategy.java
Day 6    BoardingStrategyFactory.java
```

---

### Phase 4 — Simulation Engine
The hardest phase. Tick logic, interference, baggage — take your time here.
```
Day 7    PassengerSnapshot.java
Day 7    TickSnapshot.java
Day 7    SimulationResult.java
Day 8    Simulator.java             ← most complex class, give it a full day
```

---

### Phase 5 — Output & Entry Point
```
Day 9    JsonExporter.java
Day 9    Main.java
Day 9    pom.xml
```

---

### Phase 6 — Frontend
```
Day 10   index.html                 ← form + grid layout
Day 11   style.css
Day 12   script.js                  ← animation loop, reads JSON
```

---

### Phase 7 — Integration & Testing
```
Day 13   Connect frontend → Java → JSON → JS animation
Day 14   Test all 6 strategies, edge cases, baggage/interference bugs
```