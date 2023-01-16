# TwoOpt and Nearest-Neighbour Heuristics for TSP

## Build

To build the programs follow these steps:

1. Install Java18

2. Install maven

2. Execute 
```
mvn clean install
```

in the **source** directory.


## Execute

Move into the **source** directory.

### TwoOpt

    java -cp target/classes ae.sanowskiriesterer.TwoOpt [graph] [-nnh]

**[graph]:** location of the .tsp file

**-nnh**  Use Nearest-Neighbour for the initial tour

### Nearest-Neighbour

    java -cp target/classes ae.sanowskiriesterer.NearestNeighbour [graph]

**[graph]:** location of the .tsp file

## Test

### TwoOpt

#### Random initial tour

Just execute **expTwoOpt.sh** in the **source** directory like follows

    ./expTwoOpt.sh

The results can be found in the resultTwoOpt.csv.

#### Nearest-Neighbour initial tour

Just execute **expTwoOptNN.sh** in the **source** directory like follows

    ./expTwoOptStartNN.sh

The results can be found in the resultTwoOpt.csv.

### Nearest-Neighbour

Just execute **expNearestNeighbour.sh** in the **source** directory like follows

    ./expNearestNeighbour.sh

The results can be found in the resultNearestNeighbour.csv.
