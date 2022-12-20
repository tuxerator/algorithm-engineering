package ae.sanowskiriesterer;

public class Line {

    public float m;
    public float c;
    public Node a;
    public Node b;

    public Line(Node one, Node two) {
        //ensure leftmost Node is Node a (needed to calcualte if there is an intersection between two lines)
        if(one.xCoord <= two.xCoord){
            a = one;
            b = two;
        } else {
            a = two;
            b = one;
        }
        a = one;
        b = two;
        m = (a.yCoord-b.yCoord)/(a.xCoord-b.xCoord);
        c = a.yCoord - (m * a.xCoord);
    }

    //solve y =mx+c for y
    public float solveForY(float x) {
        return m * x + c;
    }

    //solve y=mx+c for x
    public float solveForX(float y) {
        return (y - c) / m;
    }
    
}
