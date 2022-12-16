#!/bin/bash
x=()
for ((p=1;p<=9;p++)); do
x+=($(java -cp ./target/classes ae.sanowskiriesterer.MaxMatching ./data/vc-exact_00$p.gr))
printf '%s\n' ${x[-3]} ${x[-1]} ${x[-2]} | paste -sd ',' >> resultTestMaxMatching.csv
done
for ((p=10;p<=99;p++)); do
x+=($(java -cp ./target/classes ae.sanowskiriesterer.MaxMatching ./data/vc-exact_0$p.gr))
printf '%s\n' ${x[-3]} ${x[-1]} ${x[-2]} | paste -sd ',' >> resultTestMaxMatching.csv
done
for ((p=100;p<=200;p++)); do
x+=($(java -cp ./target/classes ae.sanowskiriesterer.MaxMatching ./data/vc-exact_$p.gr))
printf '%s\n' ${x[-3]} ${x[-1]} ${x[-2]} | paste -sd ',' >> resultTestMaxMatching.csv
done