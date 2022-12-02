#!/bin/bash
timeMS=0
timePMS=0
# timeQS=0
# timePQS=0
#inputsize 10**9 not possible to check, because java heap space is too small for input size
for((i=3;i<=8;i++)); do
  echo $i
  sum=$((10**i))
  timeMS=($(java -cp "." "App.Mergesort" $sum))
  timePMS=($(java -cp "." "App.ParallelMergesort" 10 $sum)) #need to put k in
  # timeQS=($(java -cp "." "App.Quicksort" $sum))
  # timePQS=($(java -cp "." "App.ParallelQuicksort"  $sum)) #need to put k in
  echo $sum
  echo $timeMS
  echo $timePMS
  # echo $timeQS
  # echo $timePQS
done