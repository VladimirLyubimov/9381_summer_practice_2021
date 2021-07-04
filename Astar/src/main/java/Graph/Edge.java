package Graph;

public class Edge {
    private final int start;
    private final int finish;
    private final int weight;

    public Edge(int start, int finish, int weight){
        this.start = start;
        this.finish= finish;
        this.weight = weight;
    }

    public int getWeight(){
        return weight;
    }

    public int getFinish(){
        return finish;
    }

    public int getStart(){
        return start;
    }
}
