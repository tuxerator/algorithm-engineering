#!/bin/bash

echo "Instance,RunningTime,#CutEdges" >> resultApproxMaxCut.csv
for instance in dataPace/*; do
  java -cp ./target/MaxCut-uber.jar riesterer.exam.ApproxMaxCut ${instance} >> resultApproxMaxCut.csv
done