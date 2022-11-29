# 1. General
* well done

# 2. Paper
## Abstract & Introduction
* good
* last sentence is cut off

## Algorithm and Implementation
* pseudo-code is readable (though a bit too detailed)


## Experimental Evaluation
* 10MB memory buffers are very small. using about 100-200MB would give more meaningful results
* in-memory merge-sort failed on 10MB data? why is that?
* can you give an educated guess about the growth in running time of EMM ?

# 3. Code & Test Cases
* a detailed README file, that's good
* have you thought about using Unit-Tests for collecting all the experimental data?

* MergeSort.java
  * line 31,71,72: you are allocating new buffers in each call (old ones will be cleaned up by the GC)

  * it would be more efficient to work on just **two** buffers. Merge from one into the other, then switch roles --> no more wasted memory.

* ExternalMemMergeSort.java:
	* looks good


# 4. Assessment
Pass

