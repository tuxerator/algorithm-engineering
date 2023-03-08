#!/bin/bash

echo "Instance,RunningTime,#CutEdges" >> resultILPMaxCutWithBound.csv
for instance in data/*; do
  java -cp ./target/MaxCut-uber.jar riesterer.exam.ILPMaxCut ${instance} >> resultILPMaxCutWithBound.csv
done