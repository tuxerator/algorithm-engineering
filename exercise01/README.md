# External Memory Merge Sort

## Build

To build the programs follow these steps:

### filegen

1. Download the rust-language through your favorite package manager or from [rust-lang.org](rust-lang.org)

2. run 

    cargo build --release

in the filegen directory

### ExternalMemMergeSort

You can use the precompiled binary in em-mergesort/bin or compile it like the following:

1. Install Java19

2. Execute 

    javac -d bin src/Em-MergeSort/*.java

in the em-mergesort directory.


## Execute

### filegen

    ./filegen [size in kB] [lower bound] [upper bound] [destination]

### em-mergesort

Move to the em-mergesort directory

    java -cp bin Em-MergeSort.ExternalMemMergeSort [block-size] [memory-size] [file]

## Test

Just execute the script in the tests directory like follows

    ./test-merge-sort.sh [directory]

where [directory] is the location where the test data is saved.
