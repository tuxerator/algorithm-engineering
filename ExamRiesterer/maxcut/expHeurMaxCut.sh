#!/bin/bash

echo "Instance,RunningTime,#CutEdges" >> resultHeurMaxCut.csv
for instance in dataPace/*; do
  java -cp ./target/MaxCut-uber.jar riesterer.exam.HeurMaxCut ${instance} >> resultHeurMaxCut.csv
done