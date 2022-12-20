# max-matching and SimpleBST

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

### MaxMatching

    java -cp target/classes ae.sanowskiriesterer.MaxMatching

### SimpleBST with MaxMatching for finding a starting k

    java -cp target/classes ae.sanowskiriesterer.App [graph]

**[graph]:** location of the .gr file

## Test

### MaxMatching

Just execute **testNumberOfProcessors.sh** in the **source** directory like follows

    ./testMaxMatching.sh

The results can be found in the resultTestMaxMatching.csv.

### SimpleBST

Just execute **testSimpleBST.sh** in the **source** directory like follows

    ./testSimpleBST.sh

The results can be found in the resultSimpleBST.csv.
