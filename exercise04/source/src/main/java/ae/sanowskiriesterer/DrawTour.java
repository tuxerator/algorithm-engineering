package ae.sanowskiriesterer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

import ae.sanowskiriesterer.Edge;

public class DrawTour {

    static float xMin;
    static float xMax;
    static float yMin;
    static float yMax;
    static float yValue;
    static float xValue;

    public static void main(String[] args) throws Exception{

        // Node a = new Node(1,1);
        // Node b = new Node(2,1);
        // Node c = new Node((float)1.5,2);

        // LinkedList<Edge> edges = new LinkedList<Edge>();
        // edges.add(new Edge(a,b));
        // edges.add(new Edge(b,c));
        // edges.add(new Edge(c,a));

        // draw(edges,"example.svg");
    }

    public static void draw(ArrayList<Edge> tour, String outputFile) throws Exception{
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        getRange(tour);
        writer.write("<svg width=\""+xValue+"\" height=\""+yValue+"\">");
        writer.newLine();
        for(int i = 0; i < tour.size(); i++){
            if(i==0){
                //add both nodes
                float[] point = scale(tour.get(i).nodeA.xCoord,tour.get(i).nodeA.yCoord);
                writer.write("<circle cx=\""+point[0]+"\" cy=\""+point[1]+"\" r=\"5\" stroke=\"black\" fill=\"black\" />");
                writer.newLine();
                point = scale(tour.get(i).nodeB.xCoord,tour.get(i).nodeB.yCoord);
                writer.write("<circle cx=\""+point[0]+"\" cy=\""+point[1]+"\" r=\"5\" stroke=\"black\" fill=\"black\" />");
                writer.newLine();
                
            } else if(i<tour.size()-1){
                float [] point = scale(tour.get(i).nodeB.xCoord,tour.get(i).nodeB.yCoord);
                writer.write("<circle cx=\""+point[0]+"\" cy=\""+point[1]+"\" r=\"5\" stroke=\"black\" fill=\"black\" />");
                writer.newLine();
            }
            // draw a line
            float[] pointStart = scale(tour.get(i).nodeA.xCoord,tour.get(i).nodeA.yCoord);
            float [] pointEnd = scale(tour.get(i).nodeB.xCoord,tour.get(i).nodeB.yCoord);
            writer.write("<line x1=\""+pointStart[0]+"\" y1=\""+pointStart[1]+"\" x2=\""+pointEnd[0]+"\" y2=\""+pointEnd[1]+"\" style=\"stroke:black;stroke-width:2\" />");
            writer.newLine();
        }

        writer.write("</svg>");
        writer.close();

    }

    private static void getRange(ArrayList<Edge> edges){

        xMin = edges.get(0).nodeA.xCoord;
        xMax = edges.get(0).nodeA.xCoord;
        yMin = edges.get(0).nodeA.yCoord;
        yMax = edges.get(0).nodeA.yCoord;

        for(int i = 1; i< edges.size(); i++){
            if(edges.get(i).nodeA.xCoord > xMax){
                xMax = edges.get(i).nodeA.xCoord;
            } else if(edges.get(i).nodeA.xCoord < xMin){
                xMin = edges.get(i).nodeA.xCoord;
            }
            if(edges.get(i).nodeA.yCoord < yMin){
                yMin = edges.get(i).nodeA.yCoord;
            } else if(edges.get(i).nodeA.yCoord > yMax){
                yMax = edges.get(i).nodeA.yCoord;
            }
        }

        yValue = yMax - yMin + 30;
        xValue = xMax - xMin + 30;

    }
    // ($In - $InUG) / ($InOG - $InUG) * ($OutOG - $OutUG) + $OutUG
    private static float[] scale(float x, float y){
        float[] scaled = new float[2];
        scaled[0] = (x-xMin)/(xMax - xMin) * xValue;
        scaled[1] = (y-yMin)/(yMax - yMin) * yValue;

        return scaled;
    }
}
