#!/bin/bash
time=()
for((k=1; k<=16; k++)); do
  echo $k
  for ((j=1; j<=100; j++)); do
    time+=($(java -cp "." "App.ParallelMergesort" $k 1000000))
  done
  let sum=0
  for i in ${time[@]}; do
    sum=`echo $sum $i | awk '{print $1 + $2}'`
  done
  sum=`echo $sum 100 | awk '{print $1 / $2}'`
  echo $sum
  time=()
done
