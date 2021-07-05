package Graph;

public class Edge {
    private final String start;
    private final String finish;
    private final int weight;

    public Edge(String start, String finish, int weight){
        this.start = start;
        this.finish= finish;
        this.weight = weight;
    }

    public int getWeight(){
        return weight;
    }

    public String getFinish(){
        return finish;
    }

    public String getStart(){
        return start;
    }
}
