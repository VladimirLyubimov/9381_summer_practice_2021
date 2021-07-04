package Graph;

import java.io.IOException;
import java.util.ArrayList;

public class MyGraph {
    private ArrayList<Vertex> vertex_list;
    private ArrayList<ArrayList<Integer>> graph_on_plot;
    private int size = 0;

    public MyGraph(){
        vertex_list = new ArrayList<Vertex>();
        graph_on_plot = new ArrayList<ArrayList<Integer>>();
    }

    /*public MyGraph(int vertex_count, int edge_count, int min_weight, int max_weight){
        for (int i = 0; i < vertex_count; i++){
            vertex_list.add(new Vertex(String.valueOf(i+1), i, i, i));
        }

        Min + (int)(Math.random() * ((Max - Min) + 1))
    }*/

    public MyGraph(String[] edge_list, String[] vertex_list){
        int edge_amount = edge_list.length;
        this.vertex_list = new ArrayList<Vertex>();
        size = 0;

        for(String vertex_note : vertex_list){
            String[] data = vertex_note.split("\\s");
            this.vertex_list.add(new Vertex(data[0], size, Integer.parseInt(data[1]), Integer.parseInt(data[2])));
            size += 1;
        }

        for(String edge_note: edge_list){
            String[] data = edge_note.split("\\s");
            this.vertex_list.get(getNumByLabel(data[0])).addEdge(getNumByLabel(data[1]), Integer.parseInt(data[2]));
        }
    }

    public void addVertex(String label, int x, int y) throws IOException {
        if(isVertexExist(label)){
            throw new IOException("Vertex with name " + label + " already exists!");
        }
        else {
            vertex_list.add(new Vertex(label, size, x, y));
            size += 1;
        }
    }

    public void addEdge(String start_label, String finish_label, int weight) throws IOException{
        if(!isVertexExist(finish_label) | !isVertexExist(start_label)){
            throw new IOException("Vertex doesn't exist!");
        }

        vertex_list.get(getNumByLabel(start_label)).addEdge(getNumByLabel(finish_label), weight);
        size += 1;
    }

    public void deleteVertex(String label) throws IndexOutOfBoundsException{
        if(isVertexExist(label)){
            for(Vertex vertex : vertex_list){
                try{
                    deleteEdge(vertex.getLabel(), label);
                }
                catch (IndexOutOfBoundsException err){

                }
            }
            vertex_list.get(getNumByLabel(label)).setExist(false);
        }
        else{
            throw new IndexOutOfBoundsException("Vertex with name " + label + " doesn't exist!");
        }
    }

    public void deleteEdge(String start_label, String finish_label) throws IndexOutOfBoundsException{
        if(!isVertexExist(start_label)){
            throw new IndexOutOfBoundsException("Vertex with name " + start_label + " doesn't exist!");
        }

        Vertex start_vertex = vertex_list.get(getNumByLabel(start_label));
        int edge_count = start_vertex.getEdgeAmount();
        for(int i= 0; i < edge_count; i++){
            if(start_vertex.getEdge(i).getFinish() == getNumByLabel(finish_label)){
                start_vertex.deleteEdge(i);
                return;
            }
        }

        throw new IndexOutOfBoundsException("No such edge exists!");
    }

    public int getNumByLabel(String label){
        for(Vertex ver : vertex_list){
            if(ver.getLabel().equals(label) && ver.isExist()){
                return ver.getNum();
            }
        }
        return -1;
    }

    public Vertex getVertex(int num){
        return vertex_list.get(num);
    }

    public Vertex getVertex(String label){
        int num = getNumByLabel(label);
        if(num != -1) {
            return vertex_list.get(num);
        }

        return null;
    }

    public boolean isVertexExist(int num){
        for(Vertex vertex : vertex_list){
            if(vertex.getNum() == num && vertex.isExist()){
                return true;
            }
        }

        return false;
    }

    public boolean isVertexExist(String label){
        for(Vertex vertex : vertex_list){
            if(vertex.getLabel().equals(label) && vertex.isExist()){
                return true;
            }
        }

        return false;
    }

    public boolean isVertexExist(int x, int y){
        return graph_on_plot.get(y).get(x) != 0;
    }

    public void printGraph(){
        for(Vertex vertex: vertex_list){
            if(vertex.isExist()) {
                vertex.printVertex();
            }
        }
    }
}
