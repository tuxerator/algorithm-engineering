package ae.sanowskiriesterer;

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
    }
}
