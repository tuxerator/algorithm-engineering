package ae.sanowskiriesterer;

import java.util.ArrayList;

public class App {

    public static void main(String[] args)throws Exception{
        String file = args[0];
        ArrayList<Vertex> graph = Reader.read(file);
        //ArrayList<Vertex> maxMatching = MaxMatching.getMatching(graph);
        MaxMatching.getMatching(graph);
        //System.out.println("Max matching of size "+MaxMatching.cover.size());
        // /*for(Vertex v : MaxMatching.cover){
        //     System.out.println(v.id);
        // }*/
        //System.out.println("\n");
        SimpleBST.graph = Reader.read(file);
        int kValue = MaxMatching.cover.size();
        SimpleBST.k = (kValue+1)/2;
        long maxTime = 0;
        // int kValue = MaxMatching.cover.size();
        // System.out.println("Trying k="+kValue);
        SimpleBST.simpleBST(System.currentTimeMillis(),maxTime);
        while((SimpleBST.cover.empty() || SimpleBST.cover.peek().id == -1) && SimpleBST.cover.size() <= kValue){
            SimpleBST.k++;
            System.out.println("Trying k="+SimpleBST.k);
            SimpleBST.cover.clear();
            //This case should never happen
            while(!SimpleBST.stack.empty()){
                //System.out.println("Added edges to graph");
                SimpleBST.addToGraph(SimpleBST.stack.pop());
            }
            SimpleBST.simpleBST(System.currentTimeMillis(),maxTime);
            if(SimpleBST.cover.peek().id==-2){
                break;
            }
        }

        //System.out.println("Optimal: "+SimpleBST.k);
        //for(Vertex v : SimpleBST.cover) {
            //System.out.println(v.id);
        //}
    }
    


}

