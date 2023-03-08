#!/bin/bash

echo "Instance,RunningTime,#CutEdges" >> resultParallelMaxCut.csv
for instance in dataPace/*; do
  java -cp ./target/MaxCut-uber.jar riesterer.exam.ParallelMaxCut ${instance} >> resultParallelMaxCut.csv
done