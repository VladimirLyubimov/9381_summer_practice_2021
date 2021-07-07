package Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class MyGraph {
    private ArrayList<Vertex> vertex_list;
    private ArrayList<ArrayList<Integer>> graph_on_plot;
    private int size = 0;
    private int max_width = 600;//canvas width
    private int max_height = 600;//canvas height
    private int step = 60;

    public MyGraph(){
        vertex_list = new ArrayList<>();
        graph_on_plot = new ArrayList<>();
        size = 0;
    }

    public MyGraph(int vertex_count) throws  IndexOutOfBoundsException{//build graph where all edge weights are 1 and all vertexes linked
        if(vertex_count > (max_width/step) * (max_height/step)){
            throw new IndexOutOfBoundsException("You want too much vertexes!");
        }

        vertex_list = new ArrayList<>();
        for(int i = 0; i < max_height/step && vertex_count > 0; i++){
            for(int j = 0; j < max_width/step && vertex_count > 0; j++) {
                vertex_list.add(new Vertex(vertex_count + "", j, i));
                vertex_count -= 1;
                size += 1;
            }
        }

        int x;
        int y;
        for(Vertex vertex : vertex_list){
            x = vertex.getX();
            y = vertex.getY();
            if(getVertex(x+1,y).isPresent()){
                vertex.addEdge(getVertex(x+1,y).get().getLabel(), 1);
            }
            if(getVertex(x-1,y).isPresent()){
                vertex.addEdge(getVertex(x-1,y).get().getLabel(), 1);
            }
            if(getVertex(x,y+1).isPresent()){
                vertex.addEdge(getVertex(x,y+1).get().getLabel(), 1);
            }
            if(getVertex(x,y-1).isPresent()){
                vertex.addEdge(getVertex(x,y-1).get().getLabel(), 1);
            }
        }
    }

    public MyGraph(int vertex_count, int edge_count, int min_weight, int max_weight) throws IndexOutOfBoundsException{
        if(!checkParamForRandomGer(vertex_count, edge_count, max_weight)){
            throw new IndexOutOfBoundsException("Invalid input data!");
        }
        vertex_list = new ArrayList<>();
        int x = 0;
        int y = 0;
        int max_y = 0;
        int max_x = 0;

        Vertex temp_root = new Vertex(vertex_count+"", x, y);
        vertex_list.add(temp_root);
        size += 1;
        vertex_count -= 1;
        while(vertex_count > 0){
            int edge_weight = min_weight + (int)(Math.random() * ((max_weight - min_weight) + 1));
            if(edge_weight + y < max_height/step){
                if(x == 0){
                    edge_weight = max_weight;
                }
                else{
                    if(edge_weight != min_weight){
                        edge_weight -= 1;
                    }
                }
                vertex_list.add(new Vertex(vertex_count+"", x, y + edge_weight));
                temp_root.addEdge(vertex_count+"", edge_weight);
                size += 1;
                vertex_count -= 1;
                if(vertex_count == 0){
                    break;
                }
                if(y + edge_weight > max_y){
                    max_y = y+edge_weight;
                    max_x = x;
                }
            }
            edge_weight = min_weight + (int)(Math.random() * ((max_weight - min_weight) + 1));
            if(edge_weight + x < max_width/step){
                vertex_list.add(new Vertex(vertex_count+"", x + edge_weight, y));
                temp_root.addEdge(vertex_count+"", edge_weight);
                size += 1;
                vertex_count -= 1;
                if(vertex_count == 0){
                    break;
                }
                temp_root = vertex_list.get(size-1);
                x = x + edge_weight;
            }
            else{
                temp_root = getVertex(max_x, max_y).get();
                x = 0;
                y = max_y;
            }
        }
    }

    public MyGraph(String[] edge_list, String[] vertex_list) throws IOException{
        int edge_amount = edge_list.length;
        this.vertex_list = new ArrayList<>();
        size = 0;

        for(String vertex_note : vertex_list){
            String[] data = vertex_note.split("\\s");
            try {
                addVertex(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]));
            }
            catch(IndexOutOfBoundsException err){
                throw new IndexOutOfBoundsException("Vertex with name " + data[0] + " already exists!");
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

    public Optional<Vertex> getVertex(int x, int y){
        for(Vertex vertex : vertex_list){
            if(vertex.getX() == x && vertex.getY() == y){
                return Optional.ofNullable(vertex);
            }
        }

        return Optional.ofNullable(null);
    }

    public boolean checkParamForRandomGer(int vertex_count, int edge_count, int max_weight){
        max_weight *= step;
        if(max_weight >= max_height || max_weight >= max_width){
            return false;
        }
        double width_len = Math.ceil((double) max_width/(double) max_weight);
        double height_len = Math.ceil((double) max_height/(double) max_weight);
        if(width_len * height_len < vertex_count){
            return false;
        }

        if(vertex_count*2 - 2 < edge_count){
            return false;
        }
        return true;
    }

    public void resetGraph(){
        for(Vertex vertex : vertex_list){
            vertex.setTotalVal(1000000);
            vertex.setPathVal(1000000);
            vertex.setCameFrom("");
        }
    }

    public void addVertex(String label, int x, int y) throws IndexOutOfBoundsException {
        if(isVertexExist(label)){
            throw new IndexOutOfBoundsException("Vertex with name " + label + " already exists!");
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

        if(getVertex(start_label).get().getEdgeAmount() == 4){
            throw new IOException("Vertex " + start_label + " has 4 edges. No more edges excepted!");
        }

        int actual_weight = Math.abs(getVertex(start_label).get().getX() - getVertex(finish_label).get().getX()) + Math.abs(getVertex(start_label).get().getY() - getVertex(finish_label).get().getY());
        if(weight != actual_weight){
            throw new IOException("Wrong weight!");
        }

        getVertex(start_label).get().addEdge(finish_label, weight);
    }

    public void deleteVertex(String label) throws IndexOutOfBoundsException{
        if(isVertexExist(label)){
            for(Vertex vertex : vertex_list){
                delEdge(vertex.getLabel(), label);
            }
            vertex_list.remove(getNumByLabel(label));
            size -= 1;
        }
        else{
            throw new IndexOutOfBoundsException("Vertex with name " + label + " doesn't exist!");
        }
    }

    private boolean delEdge(String start_label, String finish_label){
        Vertex start_vertex = getVertex(start_label).get();
        int edge_count = start_vertex.getEdgeAmount();
        for(int i= 0; i < edge_count; i++){
            if(start_vertex.getEdge(i).getFinish().equals(finish_label)){
                start_vertex.deleteEdge(i);
                return true;
            }
        }

        return false;
    }

    public void deleteEdge(String start_label, String finish_label) throws IndexOutOfBoundsException{
        if(!isVertexExist(start_label)){
            throw new IndexOutOfBoundsException("Vertex with name " + start_label + " doesn't exist!");
        }

        if(!isVertexExist(finish_label)){
            throw new IndexOutOfBoundsException("Vertex with name " + finish_label + " doesn't exist!");
        }

        if(!delEdge(start_label, finish_label)) {
            throw new IndexOutOfBoundsException("No such edge exists!");
        }
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

    public Optional<Vertex> getVertex(String label){
        int num = getNumByLabel(label);
        if(num != -1) {
            return Optional.ofNullable(vertex_list.get(num));
        }

        return Optional.ofNullable(null);
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

    public int getSize() {
        return size;
    }

    @Override
    public String toString(){
        StringBuilder st = new StringBuilder(new String(""));
        for(Vertex vertex: vertex_list){
            st.append(vertex.toString()).append("\n");
        }

        return st.toString();
    }
}
