# 1. General
* well done
* but some room for improvement ;-)

# 2. Paper
## Abstract & Introduction
* good
* running time of QuickSort is in O(n log n) on average, O(n^2) in the worst case (which we assume to be rare).

## Algorithm and Implementation
* nice pseudo-code (a bit too detailed, though)
* description of parallel merge step is not quite correct (which also shows in the source code)
* important to note that there are no concurrent writes. good.
* for QuickSort you describe a fall-back mechanism, but I didn't find it in your source code? Did I miss something?
* "work-stealing": should not happen, if you set up your tasks properly (but is a useful feature, yes)


## Experimental Evaluation
* nicely presented results
* figures are somewhat contradictory, though
* why is QuickSort significantly faster than MergeSort?
* why doesn't QuickSort speed-up with the number of cores?
* Figure 3: oh dear, there is next to no benefit from parallelization ...

* See below for some hints, why the implementation falls behind expectations.


# 3. Code & Test Cases
* detailed readme file, thanks
* there's no need for <maven.compiler.source>18. 17 works better on my installation
* QuickSort.main is missing ?
* test cases are easy to run
* have you thought about using JUnit for test cases?

* Mergesort.java, line 27: you are creating a new buffer in each call
* better: use just two buffers and copy back and forth

* InputGenerator.java: that's a bit of an overkill. You didn't have to run this in parallel.
* PartIntArray.java: is a wrapper class for int[] ? What's the benefit?

* Parallel Mergesort.java:
	* you are splitting jobs all the way down to the bottom of the recursion
	* but: there's no use creating more jobs than the are processor cores. To the contrary: the overhead for job creation outweighs the benefit.
	* better: fall back to the sequential implementation as soon as all cores are busy (you can tell from the recursion level)
	* running an individual merge step in parallel is missing (useful in the upper levels of recursion, when some cores are idle)
	* line 52: it's not necessary to bin-search every element. What you can do is:
	1. partition sub-chunks
	2. bin-search the *boundaries* of each chunk
	3. merge sub-chunks in parallel (which you don't)
	
* ParallelQuicksort
	* same here: fall back to sequential impl. once all cores are busy
	* please explain ParallelSplit.java. I assume, you are setting up the parallel re-arrangement as described in the lecture?
	* parallel re-arrangement is good, but only if there are some idle cores (i.e. in the upper levels of recursion)

# 4. Assessment
Pass

