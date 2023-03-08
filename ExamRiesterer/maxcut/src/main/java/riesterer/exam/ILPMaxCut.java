package riesterer.exam;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.Solver;

public class ILPMaxCut {
    
    public static void main(String args[]) throws Exception{
        Graph graph = Reader.read(args[0]);
        graph.createAdjacencyList();
        int bound = ApproxMaxCut.start(graph);
        long start = System.currentTimeMillis();
        Number result = start(graph, bound);
        long end = System.currentTimeMillis();

        System.out.println(args[0]+","+(end-start)+","+result);
    }

    public static Number start(Graph g, int lowerBound){
        // create an ILP model
        Model model = new Model();

        //for every edge set an int var
        IntVar[] edges = model.intVarArray("edges", g.getEdgeList().size(), 0, 1);

        //for every vertex set an int var
        IntVar[] vertices = model.intVarArray("vertices", g.getVertexList().size(), 0, 1);

        //for every edge set two constraints
        for(int i = 0; i < edges.length; i++){
            int idFirstVertex = g.getEdgeList().get(i).getFirst().getId();
            int idSecondVertex = g.getEdgeList().get(i).getSecond().getId();
            //e_{u,v} <= x_u + x_v
            model.arithm(edges[i],"<=", vertices[idFirstVertex], "+", vertices[idSecondVertex]).post();
            //e_{u,v} <= 2 - (x_u + x_v) = -(x_u + x_v) + 2
            (vertices[idFirstVertex].add(vertices[idSecondVertex])).neg().add(2).ge(edges[i]).post();
        }

        //set sum (upper and lowerbound)
        IntVar sum;
        if(2*lowerBound <= edges.length){
            sum = model.intVar(lowerBound, 2*lowerBound);
        } else {
            sum = model.intVar(lowerBound, edges.length);
        }
        model.sum(edges, "=", sum).post();

        // //set upper and lower bound for sum
        // //sum <= 2*bound && sum >= bound
        // sum.le(lowerBound * 2).post();
        // sum.ge(lowerBound).post();

        //set maximizing problem
        model.setObjective(Model.MAXIMIZE, sum);
        //model.setObjective(true, sum); //I guess it's the same as above
        //solve problem
        Solver solver = model.getSolver();
        //limit search time to 10 minutes
        Number numberCutEdges = 0;
        solver.limitTime("10m");
        while(solver.solve()){
            //store last solution found
            numberCutEdges = solver.getBestSolutionValue();
        }

        return numberCutEdges;
    }

}