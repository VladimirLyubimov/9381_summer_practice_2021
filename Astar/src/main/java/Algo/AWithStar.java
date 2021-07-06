package Algo;

import Graph.*;

import java.util.ArrayList;
import java.util.Collections;

public class AWithStar {
    private MyGraph graph;
    private ArrayList<Vertex> open_set;
    private ArrayList<Vertex> close_set;
    private ArrayList<String> path;
    private String start;
    private  String finish;

    public AWithStar(MyGraph graph, String start_label, String finish_label){
        this.graph = graph;
        this.start = start_label;
        this.finish = finish_label;
        open_set = new ArrayList<Vertex>();
        close_set = new ArrayList<Vertex>();
    }

    public void resetAlgo(){
        graph.resetGraph();
        path = null;
        open_set = new ArrayList<Vertex>();
        close_set = new ArrayList<Vertex>();
    }

    public String getFinish() {
        return finish;
    }

    public String getStart() {
        return start;
    }

    public ArrayList<String> doAlgo(String start_label, String finish_label) throws IndexOutOfBoundsException{
        if(!graph.isVertexExist(start_label)){
            throw new IndexOutOfBoundsException("Start vertex with name " + start_label + " doesn't exist!");
        }

        if(!graph.isVertexExist(finish_label)){
            path = new ArrayList<String>();
            path.add("No path! Finish vertex doesn't exist!");
            return path;
        }
        //int start = graph.getNumByLabel(start_label);
        //int finish = graph.getNumByLabel(finish_label);

        if(start_label.equals(finish_label)){
            path = new ArrayList<String>();
            path.add("Start and finish vertexes are same!");
            return path;
        }

        open_set.add(graph.getVertex(start_label));

        int f_x = graph.getVertex(finish_label).getX();
        int f_y = graph.getVertex(finish_label).getY();
        graph.getVertex(start_label).setPathVal(0);
        graph.getVertex(start_label).setTotalVal(0);

        while(!open_set.isEmpty()){
            Vertex cur_vertex = findMin();

            System.out.println("Current vertex is: " + cur_vertex.getLabel());
            System.out.print("Open set is: ");
            for(Vertex vertex : open_set){
                System.out.print(vertex.getLabel() + " ");
            }
            System.out.println();
            System.out.print("Close set is: ");
            for(Vertex vertex : close_set){
                System.out.print(vertex.getLabel() + " ");
            }
            System.out.println("\n-------");


            if(cur_vertex.getLabel().equals(finish_label)){
                makePath(finish_label);
                return path;
            }

            int neighbour_amount = cur_vertex.getEdgeAmount();
            for(int i = 0; i < neighbour_amount; i++){
                Edge cur_edge = cur_vertex.getEdge(i);
                Vertex cur_neighbour = graph.getVertex(cur_edge.getFinish());
                //System.out.println(cur_neighbour);

                if(close_set.contains(cur_neighbour)){
                    //System.out.println("Exist!");
                    continue;
                }

                int temp_path_val = cur_vertex.getPathVal() + cur_edge.getWeight();
                //System.out.println(cur_node + " " + cur_neighbour + " " + temp_path_val);
                boolean need_update = false;

                if(!open_set.contains(cur_neighbour)){
                    open_set.add(cur_neighbour);
                    //need_update = true;
                }

                //System.out.println(cur_node + " " + cur_neighbour + " " + graph.getVertex(cur_neighbour).getPathVal());

                if(temp_path_val < cur_neighbour.getPathVal()){
                    //System.out.println(cur_node + " " + cur_neighbour + " " + temp_path_val);
                    need_update = true;
                }

                if(need_update){
                    cur_neighbour.setCameFrom(cur_vertex.getLabel());
                    cur_neighbour.setPathVal(temp_path_val);
                    int heuristic = Math.abs(cur_neighbour.getX() - f_x) + Math.abs(cur_neighbour.getY() - f_y);
                    cur_neighbour.setTotalVal(temp_path_val + heuristic);
                }
            }

            close_set.add(cur_vertex);
            open_set.remove(cur_vertex);
        }

        path = new ArrayList<String>();
        path.add("No path!");
        return path;
    }

    private Vertex findMin(){
        Vertex res = open_set.get(0);
        for(Vertex vertex: open_set){
            if(res.getTotalVal() > vertex.getTotalVal()){
                res = vertex;
            }
        }

        return res;
    }

    private void makePath(String finish){
        String prev = graph.getVertex(finish).getCameFrom();
        path = new ArrayList<String>();
        path.add(finish);

        while(!prev.equals("")){
            path.add(prev);
            prev = graph.getVertex(prev).getCameFrom();
        }

        Collections.reverse(path);
    }
}
