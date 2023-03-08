package riesterer.exam;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMaxCut extends RecursiveAction {

    public static Graph graph;
    public static int[] results;
    public static ForkJoinPool fjp;
    public static int processors;
    private int[] groupVertices;
    private int id;

    public static void main(String args[]) throws Exception{
        graph = Reader.read(args[0]);
        long start = System.currentTimeMillis();
        // keep one processor for managing the tasks
        processors = Runtime.getRuntime().availableProcessors()-1;
        fjp = new ForkJoinPool(processors);
        results = new int[processors];
        for(int i = 0; i < processors; i++){
            fjp.invoke(new ParallelMaxCut(i));
        }
        int max = -1;
        for(int i : results){
            if( i > max){
                max = i;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println(args[0]+","+(end-start)+","+max);
    }

    public ParallelMaxCut(int id){
        this.groupVertices = new int[graph.getVertexList().size()];
        this.id = id;
    }

    @Override
    protected void compute() {
        Random rand = new Random();
        int result = -1;
        for(int i = 0; i < 100/processors; i++){
            // random group the vertices
            for(int j = 0; j < graph.getVertexList().size(); j++){
                this.groupVertices[j] = rand.nextInt(2);
            }
            //count the cut edges
            int count = 0;
            for(Edge e : graph.getEdgeList()){
                int groupFirst = this.groupVertices[e.getFirst().getId()];
                int groupSecond = this.groupVertices[e.getSecond().getId()];
                if(groupFirst != groupSecond){
                    count++;
                }
            }
            if(count > result){
                result = count;
            }
        }
        results[this.id] = result;
        
        // // count the cut edges
        // results[this.id] = 0;
        // for(Edge e : graph.getEdgeList()){
        //     int groupFirst = this.groupVertices[e.getFirst().getId()];
        //     int groupSecond = this.groupVertices[e.getSecond().getId()];
        //     if(groupFirst != groupSecond){
        //         results[this.id]++;
        //     }
        // }
    }
}
