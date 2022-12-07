package ae.sanowskiriesterer;

import java.util.ArrayList;

public class SimpleBST {
    //public ArrayList<Vertex> graph;
    //public ArrayList<Vertex> cover;
    public static int k;

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

        ArrayList<Vertex> graph = new ArrayList<Vertex>();
        graph.add(v1);
        graph.add(v2);
        graph.add(v3);
        graph.add(v4);
        graph.add(v5);
        graph.add(v6);
        graph.add(v7);
        graph.add(v8);

        k = 5;
        ArrayList<Vertex> result = simpleBST(graph,new ArrayList<Vertex>());
        System.out.println(result.size()+"\n");
        for(Vertex v : result){
            System.out.println(v.id);
        }
    }

    public static ArrayList<Vertex> simpleBST(ArrayList<Vertex> graph, ArrayList<Vertex> cover){
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
            cover.clear();
            return cover;
        } else if(cover.size() <= k && noEdges){
            return cover;
        }
        // ArrayList<Vertex> graphA = new ArrayList<Vertex>(graph);
        ArrayList<Vertex> graphA = copyGraph(graph);
        // System.out.println("Graph A:");
        // for(Vertex v : graphA){
        //     System.out.println(v.id+":");
        //     for(Vertex w : v.neighbors){
        //         System.out.println(w.id);
        //     }
        // }
        remove(graphA, edge[0]);
        ArrayList<Vertex> coverA = new ArrayList<Vertex>(cover);
        coverA.add(edge[0]);
        ArrayList<Vertex> coverAResult = simpleBST(graphA, coverA);
        if(coverAResult.size() > 0){
            return coverAResult;
        }
        // ArrayList<Vertex> graphB = new ArrayList<Vertex>(graph);
        ArrayList<Vertex> graphB = copyGraph(graph);
        // System.out.println("Graph B:");
        // for(Vertex v : graphB){
        //     System.out.println(v.id+":");
        //     for(Vertex w : v.neighbors){
        //         System.out.println(w.id);
        //     }
        // }
        remove(graphB, edge[1]);
        ArrayList<Vertex> coverB = new ArrayList<Vertex>(cover);
        coverB.add(edge[1]);
        ArrayList<Vertex> coverBResult = simpleBST(graphB, coverB);
        if(coverBResult.size() > 0){
            return coverBResult;
        }
        cover.clear();
        return cover;

    }

    public static void remove(ArrayList<Vertex> graph, Vertex v){
        for(Vertex w : v.neighbors) {
            for(Vertex x : w.neighbors){
                if(x.id==v.id){
                    w.neighbors.remove(x);
                    break;
                }
            }
        }
        v.neighbors.clear();

    }

    public static ArrayList<Vertex> copyGraph(ArrayList<Vertex> graph){

        ArrayList<Vertex> clone = new ArrayList<Vertex>(graph);
        for(int i = 0; i < clone.size(); i++){
            clone.get(i).neighbors = new ArrayList<Vertex>(graph.get(i).neighbors);
        }
        return clone;
    }
}
