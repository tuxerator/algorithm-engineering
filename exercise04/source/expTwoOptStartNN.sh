#!/bin/bash
echo "Tour,RunningTime,#EdgeSwaps,Tour length" > resultTwoOptStartNN.csv
for tour in data/*; do
echo $tour
x=($(java -cp target/classes ae.sanowskiriesterer.TwoOpt $tour -nnh))
printf '%s\n' $tour $x | paste -sd ',' >> resultTwoOptStartNN.csv
done