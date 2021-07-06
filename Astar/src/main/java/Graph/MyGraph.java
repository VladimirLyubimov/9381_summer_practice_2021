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
        size = 0;
    }

    /*public MyGraph(int vertex_count, int edge_count, int min_weight, int max_weight){
        for (int i = 0; i < vertex_count; i++){
            vertex_list.add(new Vertex(String.valueOf(i+1), i, i, i));
        }

        Min + (int)(Math.random() * ((Max - Min) + 1))
    }*/

    public MyGraph(String[] edge_list, String[] vertex_list) throws IOException{
        int edge_amount = edge_list.length;
        this.vertex_list = new ArrayList<Vertex>();
        size = 0;

        for(String vertex_note : vertex_list){
            String[] data = vertex_note.split("\\s");
            try {
                addVertex(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]));
            }
            catch(IOException err){
                throw new IOException("Vertex with name " + data[0] + " already exists!");
            }
        }

        for(String edge_note: edge_list){
            String[] data = edge_note.split("\\s");
            try {
                addEdge(data[0], data[1], Integer.parseInt(data[2]));
            }
            catch (IOException err){
                throw new IOException("Unable to create edge from " + data[0] + " to " + data[1]);
            }
        }
    }

    public void resetGraph(){
        for(Vertex vertex : vertex_list){
            vertex.setTotalVal(1000000);
            vertex.setPathVal(1000000);
            vertex.setCameFrom("");
        }
    }

    public void addVertex(String label, int x, int y) throws IOException {
        if(isVertexExist(label)){
            throw new IOException("Vertex with name " + label + " already exists!");
        }
        else {
            vertex_list.add(new Vertex(label, x, y));
            size += 1;
        }
    }

    public void addEdge(String start_label, String finish_label, int weight) throws IOException{
        if(!isVertexExist(finish_label) | !isVertexExist(start_label)){
            throw new IOException("Vertex doesn't exist!");
        }

        if(getVertex(start_label).getEdgeAmount() == 4){
            throw new IOException("Vertex " + start_label + " has 4 edges. No more edges excepted!");
        }

        int actual_weight = Math.abs(getVertex(start_label).getX() - getVertex(finish_label).getX()) + Math.abs(getVertex(start_label).getY() - getVertex(finish_label).getY());
        if(weight != actual_weight){
            throw new IOException("Wrong weight!");
        }

        getVertex(start_label).addEdge(finish_label, weight);
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
            vertex_list.remove(getNumByLabel(label));
            size -= 1;
        }
        else{
            throw new IndexOutOfBoundsException("Vertex with name " + label + " doesn't exist!");
        }
    }

    public void deleteEdge(String start_label, String finish_label) throws IndexOutOfBoundsException{
        if(!isVertexExist(start_label)){
            throw new IndexOutOfBoundsException("Vertex with name " + start_label + " doesn't exist!");
        }

        if(!isVertexExist(finish_label)){
            throw new IndexOutOfBoundsException("Vertex with name " + finish_label + " doesn't exist!");
        }

        Vertex start_vertex = getVertex(start_label);
        int edge_count = start_vertex.getEdgeAmount();
        for(int i= 0; i < edge_count; i++){
            if(start_vertex.getEdge(i).getFinish().equals(finish_label)){
                start_vertex.deleteEdge(i);
                return;
            }
        }

        throw new IndexOutOfBoundsException("No such edge exists!");
    }

    private int getNumByLabel(String label){
        int i = 0;
        for(Vertex ver : vertex_list){
            if(ver.getLabel().equals(label)){
                return i;
            }
            i += 1;
        }
        return -1;
    }

    public Vertex getVertex(String label){
        int num = getNumByLabel(label);
        if(num != -1) {
            return vertex_list.get(num);
        }

        return null;
    }

    public Vertex getVertex(int num){
        return vertex_list.get(num);
    }

    public boolean isVertexExist(String label){
        for(Vertex vertex : vertex_list){
            if(vertex.getLabel().equals(label)){
                return true;
            }
        }

        return false;
    }

    public boolean isVertexExist(int x, int y){
        return graph_on_plot.get(y).get(x) != 0;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString(){
        String st = new String("");
        for(Vertex vertex: vertex_list){
            st += vertex.toString() + "\n";
        }

        return st;
    }
}
