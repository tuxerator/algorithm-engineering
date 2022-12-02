#!/bin/bash
timeMS=()
# timeQS=()
for((k=1; k<=16; k++)); do
  echo $k
  for ((j=1; j<=100; j++)); do
    timeMS+=($(java -cp "." "App.ParallelMergesort" $k 1000000))
    # timeQS+=($(java -cp "." "App.ParallelQuicksort" $k 1000000))
  done
  let sum=0
  for i in ${timeMS[@]}; do
    sum=`echo $sum $i | awk '{print $1 + $2}'`
  done
  sum=`echo $sum 100 | awk '{print $1 / $2}'`
  echo $sum
  timeMS=()
  # let sum=0
  # for i in ${timeQS[@]}; do
  #   sum=`echo $sum $i | awk '{print $1 + $2}'`
  # done
  # sum=`echo $sum 100 | awk '{print $1 / $2}'`
  # echo $sum
  # timeQS=()
done
