#!/bin/bash

blockSizes=($((128 * 1024)) $((1 * 1024**2)) $((3384 * 1024)))
memSize=$((10 * 1024**2))
fileSizes=(1000 5000 10000 100000 1000000)
git_root=$(git rev-parse --show-toplevel)

echo "${blockSizes[@]}"

# Test every file for each block size 
for blockSize in "${blockSizes[@]}"; do
  echo "Creating files..."
  ${git_root}/exercise01/filegen/generateAllFiles.sh "${fileSizes[@]}" "-9999999" "9999999" "$1" &> /dev/null
 
  for file in $1/*; do 
    printf "\nSorting $file:\n"
    stat --printf="Size %s bytes\n" "$file"
    echo "Block size: ${blockSize}B"
    java -cp "$git_root/exercise01/em-mergesort/bin" "EmMergeSort.ExternalMemMergeSort" "$blockSize" "$memSize" "$file"
  done
done

echo "Classical merge sort:"
${git_root}/exercise01/filegen/generateAllFiles.sh "${fileSizes[@]}" "-9999999" "9999999" "$1" &> /dev/null

java -cp "$git_root/exercise01/em-mergesort/bin" "EmMergeSort.MergeSort" "${1}/${fileSizes[0]}k"
java -cp "$git_root/exercise01/em-mergesort/bin" "EmMergeSort.MergeSort" "${1}/${fileSizes[1]}k"
