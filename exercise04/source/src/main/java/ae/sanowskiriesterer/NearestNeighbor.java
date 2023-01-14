package ae.sanowskiriesterer;

import java.util.ArrayList;

import ae.sanowskiriesterer.nnh.Grid;
import ae.sanowskiriesterer.nnh.Reader;

/** Hello world! */
public class NearestNeighbor {
  public static void main(String[] args) throws Exception {
    Grid grid = Reader.read(args[0]);
    String[] path = args[0].split("/");
    String instance = path[path.length - 1];

    long startTime = System.nanoTime();

    ArrayList<Edge> nnhTour = grid.nearestNeighborHeuristic();
    double nnhTime = (System.nanoTime() - startTime) / 1000000.0;
    float nnhLength = nnhTour.stream().reduce(0f, (partLength, edge) -> partLength + edge.weight, Float::sum);
    DrawTour.draw(nnhTour, "./tours/" + instance + ".nnh.svg");

    grid = Reader.read(args[0]);
    startTime = System.nanoTime();

    ArrayList<Edge> nnhqTour = grid.nearestNeighborHeuristicQuadratic();
    double nnhqTime = (System.nanoTime() - startTime) / 1000000.0;
    float nnhqLength = nnhqTour.stream().reduce(0f, (partLength, edge) -> partLength + edge.weight, Float::sum);
    DrawTour.draw(nnhqTour, "./tours/" + instance + ".nnhq.svg");

    System.out.printf("%s, %f, %f, %f, %f%n", instance, nnhTime, nnhLength, nnhqTime, nnhqLength);
  }
}
