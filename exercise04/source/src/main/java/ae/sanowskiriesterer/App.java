package ae.sanowskiriesterer;

import java.util.ArrayList;

import ae.sanowskiriesterer.nnh.Grid;
import ae.sanowskiriesterer.nnh.Reader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
      Grid grid = Reader.read(args[0]);
      DrawTour.draw(grid.nearestNeighborHeuristicQuadratic(), "nnhTour.svg"); 
      //you also have to draw the final tour produced by the 2-Opt heuristic when starting form nnh
      //possible solution:
      // TwoOpt.edges = grid.nearestNeighborHeuristicQuadratic();
      // DrawTour.draw(TwoOpt.edges, "initialNNtourname.svg");
      // TwoOpt.improveTour();
      // DrawTour.draw(TwoOpt.edges, "resultNNTwoOpttourname.svg");
    }
}
