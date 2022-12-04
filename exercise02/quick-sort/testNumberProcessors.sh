#!/bin/bash
echo "Results number processors test" > numberProcessorsResult.csv
echo "elements:,$2" >> numberProcessorsResult.csv
echo "cores,merge-sort,quick-sort" >> numberProcessorsResult.csv

for elems in ${@:2}; do
  echo $elems
  for((k=1; k<=8; k++)); do
  timesMS=()
  timesQS=()
    echo "Cores used: $k"
    for((j=1; j<=$1; j++)); do
      echo "Round $j"
      echo "Executing merge sort..."
      timesMS+=($(java -cp "target/classes" "ae.sanowskiriesterer.ParallelMergesort" $k $elems))
      echo "Executing quick sort..."
      timesQS+=($(java -cp "target/classes" "ae.sanowskiriesterer.ParallelQuickSortApp" $k $elems))
      printf "\033[3F\033[J"
    done

    let time=0
    for i in ${timesMS[@]}; do
      timeMS=`echo $timeMS $i | awk '{print $1 + $2}'`
    done
    timeMS=`echo $timeMS $1 | awk '{print $1 / $2}'`
    echo $timeMS

    let time=0
    for i in ${timesQS[@]}; do
      timeQS=`echo $timeQS $i | awk '{print $1 + $2}'`
    done
    timeQS=`echo $timeQS $1 | awk '{print $1 / $2}'`
    echo $timeQS
    echo ""

    echo "${k},${timeMS},${timeQS}" >> numberProcessorsResult.csv
  done
done
