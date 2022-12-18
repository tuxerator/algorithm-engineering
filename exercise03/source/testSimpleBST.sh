echo "Results" > resultSimpeBST.csv
for graph in data/*; do
  echo "$graph"
  printf "${graph}," >> resultSimpeBST.csv
  java -cp target/classes ae.sanowskiriesterer.App "$graph" >> resultSimpeBST.csv
done
