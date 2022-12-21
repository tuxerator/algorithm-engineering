package ae.sanowskiriesterer;

import java.util.LinkedList;

public class CoordSystem {

    public float maxX;
    public float minX;
    public float maxY;
    public float minY;
    LinkedList<Node> nodes;

    public CoordSystem(float xMin,float xMax,float yMin,float yMax,LinkedList<Node> nodes) {
        maxX = xMax;
        minX = xMin;
        minY = yMin;
        maxY = yMax;
        this.nodes = nodes;
    }
    
}
