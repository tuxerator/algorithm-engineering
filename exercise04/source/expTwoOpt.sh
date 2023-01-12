#!/bin/bash
echo "Tour,RunningTime,#EdgeSwaps" > resultTwoOpt.csv
for tour in data/*; do
echo $tour
x=($(java -cp target/classes ae.sanowskiriesterer.TwoOpt $tour))
printf '%s\n' $tour $x | paste -sd ',' >> resultTwoOpt.csv
done