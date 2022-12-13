package ae.sanowskiriesterer;

import java.util.ArrayList;
import java.util.Stack;

public class SimpleBST {
    //public ArrayList<Vertex> graph;
    //public ArrayList<Vertex> cover;
    public static int k;
    public static Stack<ArrayList<Vertex[]>> stack = new Stack<ArrayList<Vertex[]>>();
    public static Stack<Vertex> cover = new Stack<Vertex>();
    public static ArrayList<Vertex> graph = new ArrayList<Vertex>();

    public static void main(String[] args){
        Vertex v1 = new Vertex(1);
        Vertex v2 = new Vertex(2);
        Vertex v3 = new Vertex(3);
        Vertex v4 = new Vertex(4);
        Vertex v5 = new Vertex(5);
        Vertex v6 = new Vertex(6);
        Vertex v7 = new Vertex(7);
        Vertex v8 = new Vertex(8);
        v1.addNeighbors(v2,v3,v4);
        v2.addNeighbors(v1,v3,v5);
        v3.addNeighbors(v1,v2,v4,v6,v7);
        v4.addNeighbors(v1,v3,v7);
        v5.addNeighbors(v2,v6,v8);
        v6.addNeighbors(v3,v5,v8);
        v7.addNeighbors(v3,v4,v8);
        v8.addNeighbors(v5,v6,v7);

        //ArrayList<Vertex> graph = new ArrayList<Vertex>();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(v5);
        graph.add(v6);
        graph.add(v7);
        graph.add(v8);

        k = Integer.parseInt(args[0]);
        simpleBST(cover);
        System.out.println(cover.size()+"\n");
        for(Vertex v : cover){
            System.out.println(v.id);
        }
    }

    public static void simpleBST(Stack<Vertex> cover){
        Vertex[] edge = new Vertex[2];
        Boolean noEdges = true;
        for(Vertex v : graph){
            if(v.neighbors.size() > 0){
                noEdges = false;
                edge[0] = v;
                edge[1] = v.neighbors.get(0);
                break;
            }
        }
        if(cover.size() == k && !noEdges){
            cover.push(new Vertex(-1));
            return;
        } else if(cover.size() <= k && noEdges){
            return;
        }
        //if(!stack.empty()){
            //addToGraph(stack.pop());
        //}
        stack.push(remove(edge[0]));
        cover.push(edge[0]);
        //Stack<Vertex> copyCover = (Stack<Vertex>) cover.clone();
        simpleBST(cover);
        if(cover.size() > 0 && cover.peek().id >= 0){
            return;
        } else {
            cover.pop();
        }
        //cover = (Stack<Vertex>) copyCover.clone();
        while(cover.peek() != edge[0]){
            cover.pop();
        }
        cover.pop();
        addToGraph(stack.pop());
        stack.push(remove(edge[1]));
        cover.push(edge[1]);
        simpleBST(cover);
        if(cover.size() > 0 && cover.peek().id >= 0){
            return;
        } else {
            cover.pop();
        }
        cover.push(new Vertex(-1));
        return;

    }

    public static ArrayList<Vertex[]> remove(Vertex v){
        
        ArrayList<Vertex[]> edges = new ArrayList<Vertex[]>();
        for(Vertex w : v.neighbors) {
            Vertex[] edge = {v,w};
            edges.add(edge);
            for(Vertex x : w.neighbors){
                if(x.id==v.id){
                    w.neighbors.remove(x);
                    break;
                }
            }
        }
        v.neighbors.clear();
        return edges;

    }

    public static void addToGraph(ArrayList<Vertex[]> edges){
        for(Vertex[] edge : edges){
            edge[0].neighbors.add(edge[1]);
            edge[1].neighbors.add(edge[0]);
        }
    }
}
