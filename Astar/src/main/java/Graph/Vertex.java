package Graph;

import java.util.ArrayList;

public class Vertex {
    private final String label;
    private final int num;
    private int x;
    private int y;
    private int came_from = -1;
    private int path_val = 1000000;
    private int total_val = 1000000;
    private ArrayList<Edge> neighbours;

    public Vertex(String label, int num, int x, int y){
        this.label = label;
        this.num = num;
        this.x = x;
        this.y = y;
        neighbours = new ArrayList<Edge>();
    }

    public String getLabel() {
        return label;
    }

    public int getNum() {
        return num;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCameFrom() {
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

    public void setCameFrom(int came_from) {
        this.came_from = came_from;
    }

    public void setPathVal(int path_val) {
        this.path_val = path_val;
    }

    public void setTotalVal(int total_val) {
        this.total_val = total_val;
    }

    public void addEdge(int neighbour_num, int weight){
        neighbours.add(new Edge(num, neighbour_num, weight));
    }

    public int getEdgeAmount(){
        return neighbours.size();
    }

    public Edge getEdge(int pos){
        return neighbours.get(pos);
    }

    public void printVertex(){
        System.out.print(label + " " + x + ";" + y + " " + num + ". Edges: ");
        for(Edge edge : neighbours){
            System.out.print(edge.getStart() + " " + edge.getFinish() + " : " + edge.getWeight() + " ||| ");
        }
        System.out.println();
    }
}
