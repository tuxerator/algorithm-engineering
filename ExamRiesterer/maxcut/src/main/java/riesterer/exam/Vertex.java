package riesterer.exam;

// import java.util.ArrayList;

public class Vertex {

    private int id;
    private int group;

    public Vertex(int identifier){
        this.id = identifier;
        this.group = 0;
    }

    // public Vertex(int identifier, int groupId){
    //     this.id = identifier;
    //     this.group = groupId;
    // }

    public void setGroup(int groupId){
        this.group = groupId;
    }

    public int getGroup(){
        return this.group;
    }

    public int getId(){
        return this.id;
    }
    
}
