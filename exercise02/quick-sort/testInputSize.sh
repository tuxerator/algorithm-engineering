#!/bin/bash
timeMS=0
timePMS=0
timeQS=0
timePQS=0
#inputsize 10**9 not possible to check, because java heap space is too small for input size
for((i=1;i<=8;i++)); do
  echo $i
  sum=$((10**i))
  timeMS=($(java -cp ".\target\classes" "ae.sanowskiriesterer.Mergesort" $sum))
  timePMS=($(java -cp ".\target\classes" "ae.sanowskiriesterer.ParallelMergesort" 15 $sum)) #k is best value from previous test
  timeQS=($(java -cp ".\target\classes" "ae.sanowskiriesterer.QuickSortApp" $sum))
  timePQS=($(java -cp ".\target\classes" "ae.sanowskiriesterer.ParallelQuickSortApp" 3 $sum)) #k is best value from previous test
  echo $sum
  echo $timeMS
  echo $timePMS
  echo $timeQS
  echo $timePQS
done