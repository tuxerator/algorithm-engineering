#!/bin/bash

echo "Instance,Runing time grid-algorithm,Tour length grid-algorithm, Runing time quadratic-algorithm,Tour length quadratic-algorithm" >> resultNearestNeighbor.csv
for instance in data/*; do
  java -cp ./target/classes ae.sanowskiriesterer.NearestNeighbor ${instance} >> resultNearestNeighbor.csv
done
