package Algo;

import Graph.*;

import java.util.ArrayList;
import java.util.Collections;

public class AWithStar {
    public static ArrayList<String> doAlgo(MyGraph graph, String start_label, String finish_label) throws IndexOutOfBoundsException{
        ArrayList<Vertex> open_set = new ArrayList<>();
        ArrayList<Vertex> close_set = new ArrayList<>();
        if(graph.getStart().isPresent()) {
            start_label = graph.getStart().get().getLabel();
        }
        if(graph.getFinish().isPresent()) {
            finish_label = graph.getFinish().get().getLabel();
        }

        if(!graph.isVertexExist(start_label)){
            throw new IndexOutOfBoundsException("Start vertex with name " + start_label + " doesn't exist!");
        }

        ArrayList<String> path = checkNodes(graph, start_label, finish_label);
        if(!path.isEmpty()){
            graph.setPath(path);
            return path;
        }

        path = new ArrayList<>();

        open_set.add(graph.getVertex(start_label).get());

        int f_x = graph.getVertex(finish_label).get().getX();
        int f_y = graph.getVertex(finish_label).get().getY();
        graph.getVertex(start_label).get().setPathVal(0);
        graph.getVertex(start_label).get().setTotalVal(0);

        while(!open_set.isEmpty()){
            Vertex cur_vertex = findMin(open_set);

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
                makePath(graph, finish_label, path);
                graph.setPath(path);
                return path;
            }

            openVertex(graph, cur_vertex, open_set, close_set, f_x, f_y);

            close_set.add(cur_vertex);
            open_set.remove(cur_vertex);
        }

        path = new ArrayList<String>();
        path.add("No path!");
        graph.setPath(path);
        return path;
    }

    private static void openVertex(MyGraph graph, Vertex cur_vertex, ArrayList<Vertex> open_set, ArrayList<Vertex> close_set, int f_x, int f_y){
        int neighbour_amount = cur_vertex.getEdgeAmount();
        for(int i = 0; i < neighbour_amount; i++){
            Edge cur_edge = cur_vertex.getEdge(i);
            Vertex cur_neighbour = graph.getVertex(cur_edge.getFinish()).get();

            if(close_set.contains(cur_neighbour)){
                continue;
            }

            int temp_path_val = cur_vertex.getPathVal() + cur_edge.getWeight();
            boolean need_update = false;

            if(!open_set.contains(cur_neighbour)){
                open_set.add(cur_neighbour);
            }


            if(temp_path_val < cur_neighbour.getPathVal()){
                need_update = true;
            }

            if(need_update){
                cur_neighbour.setCameFrom(cur_vertex.getLabel());
                cur_neighbour.setPathVal(temp_path_val);
                int heuristic = Math.abs(cur_neighbour.getX() - f_x) + Math.abs(cur_neighbour.getY() - f_y);
                cur_neighbour.setTotalVal(temp_path_val + heuristic);
            }
        }
    }

    private static ArrayList<String> checkNodes(MyGraph graph, String start_label, String finish_label){
        ArrayList<String> path = new ArrayList<>();
        if(!graph.isVertexExist(finish_label)){
            path.add("No path! Finish vertex doesn't exist!");
            return path;
        }

        if(start_label.equals(finish_label)){
            path.add("Start and finish vertexes are same!");
            return path;
        }

        return path;
    }

    private static Vertex findMin(ArrayList<Vertex> open_set){
        Vertex res = open_set.get(0);
        for(Vertex vertex: open_set){
            if(res.getTotalVal() > vertex.getTotalVal()){
                res = vertex;
            }
        }

        return res;
    }

    private static void makePath(MyGraph graph, String finish, ArrayList<String> path){
        String prev = graph.getVertex(finish).get().getCameFrom();
        path.add(finish);

        while(!prev.equals("")){
            path.add(prev);
            prev = graph.getVertex(prev).get().getCameFrom();
        }

        Collections.reverse(path);
    }
}
