# 1. General
* you started off quite well, but then..
* results on BST are missing completely :-(
  You should have -at least- fixed it in a way to produce *some* results.
* improvements on BST are missing, too

# 2. Paper
## Abstract & Introduction
> Boundary Search Tree
* it's called a Bounded Search Tree 
* nice pseudo-code

## Algorithm and Implementation
* using a fixed, static variable 'k' is too inflexible. It's the main reason why you had a hard time getting results. 
* Better: start with small k, increment it until you find a solution (the first one is already optimal), or you run out of time.
* switching from recursive to iterative implementation does little to improve results (the algorithm remains basically the same). Stack overflows can be more effectively avoided by putting a limit on k.
* 3.2: passing variables to recursive calls is, actually, very cheap. That was not the cause of your problems.
* 3.3: it's nice that you list possible improvements. But we expected you to **implement** (at least some of) them:
  * Kernelization is probably too difficult
  * removing self loops would help
  * branches can be cut off, if you can estimate that the number of remaining edges would exceed k



## Experimental Evaluation

* pretty plots
* having NO results for BST is very bad, indeed
* you correctly noticed that stack overflows are related to large k. Why didn't you fix that problem?


# 3. Code & Test Cases
* nice README
* running JUnit test is more convenient
* more meaningful & readable test **output**, please!
* removing entries from lists is expensive. Try to keep lists intact, mark visited nodes/edges instead.

# 4. Assessment
a very close Pass. 

You understood the basic concepts, but did too little to get it running.

