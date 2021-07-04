package Graph;

import java.util.ArrayList;

public class MyGraph {
    private ArrayList<Vertex> vertex_list;
    private ArrayList<ArrayList<Integer>> graph_on_plot;

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
        int vertex_amount = 0;
        this.vertex_list = new ArrayList<Vertex>();
        for(String vertex_note : vertex_list){
            String[] data = vertex_note.split("\\s");
        }
    }

    public boolean isVertexExist(int num){
        for(Vertex vertex : vertex_list){
            if(vertex.getNum() == num){
                return true;
            }
        }

        return false;
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
}
