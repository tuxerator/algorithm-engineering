#!/bin/bash

echo "Instance,RunningTime,#CutEdges" >> resultILPMaxCutWithBound.csv
for instance in dataPace/*; do
  java -cp ./target/MaxCut-uber.jar riesterer.exam.ILPMaxCut ${instance} >> resultILPMaxCutWithBound.csv
done