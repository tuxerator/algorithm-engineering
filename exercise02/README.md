# Parallel merge and quick-sort

## Build

To build the programs follow these steps:

You can use the precompiled binary in **source/target/classes/** or compile it like the following:

1. Install Java19

2. Install maven

2. Execute 
```
mvn clean install
```

in the **source** directory.


## Execute

Move into the **source** directory.

### Classical merge and quick-sort

    java -cp target/classes ae.sanowskiriesterer.{Mergesort,QuickSort}

### Parallel merge and quick-sort

    java -cp target/classes ae.sanowskiriesterer.{ParallelMergesort,ParallelQuickSort} [threads] [instance_size]

**[threads]:** number of threads to use

**[instance_size]:** size of test instance

## Test

### Number of processors

Just execute **testNumberOfProcessors.sh** in the **experiments** directory like follows

    ./testNumberOfProcessors.sh [sample_size] [elements]

**[sample_size]:** amount of samples to take the average over

**[elements]:** number of elements to test with (can be more than 1 argument)

The results can be found in the numberProcessorsResult.csv.

### Input size

Just execute **testInputSize.sh** in the **experiments** directory like follows

    ./testInputSize.sh [from_threads] [to_treads]

**[from_threads]:** thread count to start test at

**[to_threads]:** thread count to end test at

The results can be found in the imputSizeResult.csv.
