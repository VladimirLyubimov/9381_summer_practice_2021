package Graph;

import java.util.ArrayList;

public class Vertex {
    private final String label;
    private int x;
    private int y;
    private String came_from = "";
    private int path_val = 1000000;
    private int total_val = 1000000;
    private boolean exist = true;
    private ArrayList<Edge> neighbours;

    public Vertex(String label, int x, int y){
        this.label = label;;
        this.x = x;
        this.y = y;
        neighbours = new ArrayList<Edge>();
    }

    public String getLabel() {
        return label;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getCameFrom() {
        return came_from;
    }

    public int getPathVal() {
        return path_val;
    }

    public int getTotalVal() {
        return total_val;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCameFrom(String came_from) {
        this.came_from = came_from;
    }

    public void setPathVal(int path_val) {
        this.path_val = path_val;
    }

    public void setTotalVal(int total_val) {
        this.total_val = total_val;
    }

    public void addEdge(String neighbour_label, int weight){
        neighbours.add(new Edge(label, neighbour_label, weight));
    }

    public int getEdgeAmount(){
        return neighbours.size();
    }

    public Edge getEdge(int pos){
        if(pos < 0 || pos > 4){
            return null;
        }
        return neighbours.get(pos);
    }

    public void deleteEdge(int pos){
        neighbours.remove(pos);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }

        if(obj instanceof Vertex){
            Vertex ver = (Vertex) obj;
            if(label.equals(ver.getLabel()) && x == ver.getX() && y == ver.getY()){
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString(){
        String st = label + " " + x + ";" + y + " " + ". Edges: ";
        for(Edge edge : neighbours){
            st += edge.getStart() + " " + edge.getFinish() + " : " + edge.getWeight() + " ||| ";
        }

        return st;
    }
}
