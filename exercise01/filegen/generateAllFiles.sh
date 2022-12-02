#!/bin/bash

args=("$@")
git_root=$(git rev-parse --show-toplevel)

lower_range=${args[$(( ${#args[@]} - 3))]}
upper_range=${args[$(( ${#args[@]} - 2))]}
dest_dir=${args[$(( ${#args[@]} - 1))]}


unset args[$(( ${#args[@]} - 1))]
unset args[$(( ${#args[@]} - 1))]
unset args[$(( ${#args[@]} - 1))]

for file_size in "${args[@]}"; do
  echo "$file_size"
  ${git_root}/exercise01/filegen/target/release/filegen "$file_size" "$lower_range" "$upper_range" "${dest_dir}/${file_size}k"
done
