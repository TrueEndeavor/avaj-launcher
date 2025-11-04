# avaj-launcher

Aircraft simulation program implementing the following patterns
- Observer
- Singleton
- Factory 

## Compilation

```bash
find * -name "*.java" > sources.txt
javac @sources.txt
```

## Cleanup
```bash
find . -name "*.class" -type f -delete && find src -name "*.java"
```

## Running

```bash
java -cp src com.avaj.simulator.Simulator scenario.txt
```

The simulation will generate a `simulation.txt` file with the results.


## Example Simulation

Sample scenario with 4 aircraft over 4 cycles:

```
4
Balloon B1 0 0 21
JetPlane J1 10 10 10
Helicopter H1 100 0 0
Helicopter H4 50 50 3
```

### What Happens

**4 aircraft, 4 different fates:**

- **B1** (Balloon): Fights RAIN → SUN → FOG → SNOW, barely survives (height 2)
- **H4** (Helicopter): Starts too low (height 3) in SNOW, crashes immediately in Cycle 1
- **J1** (JetPlane): Navigates FOG and SNOW, recovers in SUN, ends healthy (height 7)
- **H1** (Helicopter): Lucky coordinates keep it in eternal SUN, thriving (height 8)

**Result:** 1 crashed, 2 healthy, 1 critical

### Flight Data

| Cycle | B1 Position | B1 Weather | J1 Position | J1 Weather | H1 Position | H1 Weather | H4 Status |
|-------|-------------|------------|-------------|------------|-------------|------------|-----------|
| Start | (0,0,21)    | RAIN       | (10,10,10)  | FOG        | (100,0,0)   | SUN        | (50,50,3) SNOW |
| 1     | (0,0,16)    | RAIN ↓5    | (10,11,10)  | FOG →1     | (110,0,2)   | SUN ↑2     | CRASHED ❌ |
| 2     | (2,0,20)    | SUN ↑4     | (10,11,3)   | SNOW ↓7    | (120,0,4)   | SUN ↑2     | — |
| 3     | (2,0,17)    | FOG ↓3     | (10,21,5)   | SUN ↑2     | (130,0,6)   | SUN ↑2     | — |
| 4     | (2,0,2)     | SNOW ↓15   | (10,31,7)   | SUN ↑2     | (140,0,8)   | SUN ↑2     | — |

**Legend:** ↑ climb, ↓ descend, → lateral
**Weather Formula:** (longitude + latitude + height) % 4
**Mapping:** 0=SUN, 1=RAIN, 2=FOG, 3=SNOW