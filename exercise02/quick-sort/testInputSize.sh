#!/bin/bash
rm results.csv >> /dev/null
touch results.csv
#inputsize 10**9 not possible to check, because java heap space is too small for input size
for((p=$1;p<=$2;p++)); do
  sum=()
  timeMS=()
  timePMS=()
  timeQS=()
  timePQS=()

  echo "Cores: $p"
  echo "Cores: $p" >> results.csv
  for((i=3;i<=8;i++)); do
    sum+=($((10**i)))
    echo "Testing with ${sum[-1]} elements"
    echo "Classical merge sort:"
    timeMS+=($(java -cp "./target/classes" "ae.sanowskiriesterer.Mergesort" "${sum[-1]}"))
    echo "Time: ${timeMS[-1]}"
    echo "Parallel merge sort:"
    timePMS+=($(java -cp "./target/classes" "ae.sanowskiriesterer.ParallelMergesort" "$p" "${sum[-1]}")) #need to put k in
    echo "Time: ${timePMS[-1]}"
    echo "Classical quick sort:"
    timeQS+=($(java -cp "./target/classes" "ae.sanowskiriesterer.QuickSortApp" "${sum[-1]}"))
    echo "Time: ${timeQS[-1]}"
    echo "Parallel quick sort:"
    timePQS+=($(java -cp "./target/classes" "ae.sanowskiriesterer.ParallelQuickSortApp" "$p" "${sum[-1]}")) #need to put k in
    echo "Time: ${timePQS[-1]}"
    printf "\033[9F\033[J"
  done
  IFS=',' 
  echo "elements,${sum[*]}" >> results.csv
  echo "classical merge sort,${timeMS[*]}" >> results.csv
  echo "parallel merge sort,${timePMS[*]}" >> results.csv
  echo "classical quick sort,${timeQS[*]}" >> results.csv
  echo "parallel quick sort,${timePQS[*]}" >> results.csv
  printf "\033[F\033[J"
done
