## Set up the project

1. Download and install Java 19
2. Download and install maven
3. Execute
```
mvn clean package
```
```
mvn compile
```
in the **src** folder.

There should be a **dependency-reduced-pom.xml** (in **src**) and a **MaxCut-uber.jar** (in **target**).

## Run a file
Execute 
```
java -cp target/MaxCut-uber.jar riesterer.exam.[filename] [instance]
```

## Test
 To run the tests, execute
 ```
 mvn test
 ```
 
 To run the experiments, execute
 ```
 bash [filename].sh
 ```
 Make sure to have the data in a folder called dataPace inside the maxcut directory.
 The results can be found in the created csv-files.
