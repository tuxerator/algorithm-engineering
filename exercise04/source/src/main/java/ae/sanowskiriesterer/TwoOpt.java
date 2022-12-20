package ae.sanowskiriesterer;

import java.lang.Math;

public class TwoOpt {

    public static void main(String[] args){
        
    }

    public static float euclidianDistance(Node a, Node b){

        return (float) Math.sqrt(Math.pow(a.xCoord-b.xCoord,2) + Math.pow(a.yCoord-b.yCoord,2));
    }

    public static Boolean intersection(Line line, Node a, Node b){
        //if a.yCoord is above the line, then b must lie under the line and between the two newly created lines
        if(a.yCoord > line.solveForY(a.xCoord)) {

            Line aToLineA = new Line(a,line.a);
            Line aToLineB = new Line(a,line.b);
            return (b.yCoord < line.solveForY(b.xCoord) && b.xCoord > aToLineA.solveForX(b.yCoord) && b.xCoord < aToLineB.solveForX(b.yCoord));

        //if b.yCoord is above the line, then c must lie under the line and between teh two newly created lines
        } else if(b.yCoord > line.solveForY(b.xCoord)){

            Line aToLineA = new Line(b,line.a);
            Line aToLineB = new Line(b,line.b);
            return (a.yCoord < line.solveForY(a.xCoord) && a.xCoord > aToLineA.solveForX(a.yCoord) && a.xCoord < aToLineB.solveForX(a.yCoord));

        }

        return false;
    }
    
}
