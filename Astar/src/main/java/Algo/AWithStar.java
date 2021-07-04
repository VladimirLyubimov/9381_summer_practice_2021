package Algo;

import Graph.*;

import java.util.ArrayList;

public class AWithStar {
    private MyGraph graph;
    private ArrayList<Integer> open_set;
    private ArrayList<Integer> close_set;
    private ArrayList<Integer> path;

    public AWithStar(MyGraph graph){
        this.graph = graph;
        open_set = new ArrayList<Integer>();
        close_set = new ArrayList<Integer>();
    }

    public ArrayList<Integer> doAlgo(String start_label, String finish_label){
        if(!graph.isVertexExist(start_label) || !graph.isVertexExist(finish_label)){
            return null;
        }
        int start = graph.getNumByLabel(start_label);
        int finish = graph.getNumByLabel(finish_label);

        if(start == finish){
            return null;
        }

        open_set.add(start);

        int f_x = graph.getVertex(finish).getX();
        int f_y = graph.getVertex(finish).getY();
        graph.getVertex(start).setPathVal(0);
        graph.getVertex(start).setTotalVal(0);

        while(!open_set.isEmpty()){
            int cur_node = findMin();

            if(cur_node == finish){
                makPath(finish);
                return path;
            }

            int neighbour_amount = graph.getVertex(cur_node).getEdgeAmount();
            Vertex cur_vertex = graph.getVertex(cur_node);
            for(int i = 0; i < neighbour_amount; i++){
                Edge cur_edge = cur_vertex.getEdge(i);
                int cur_neighbour = cur_edge.getFinish();
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

                if(temp_path_val < graph.getVertex(cur_neighbour).getPathVal()){
                    //System.out.println(cur_node + " " + cur_neighbour + " " + temp_path_val);
                    need_update = true;
                }

                if(need_update){
                    Vertex cur_neighbour_vertex = graph.getVertex(cur_neighbour);
                    cur_neighbour_vertex.setCameFrom(cur_node);
                    cur_neighbour_vertex.setPathVal(temp_path_val);
                    int heuristic = Math.abs(cur_neighbour_vertex.getX() - f_x) + Math.abs(cur_neighbour_vertex.getY() - f_y);
                    cur_neighbour_vertex.setTotalVal(temp_path_val + heuristic);
                }
            }

            close_set.add(cur_node);
            open_set.remove(Integer.valueOf(cur_node));
        }

        return  null;
    }

    private int findMin(){
        int res = open_set.get(0);
        for(int num: open_set){
            if(graph.getVertex(res).getTotalVal() > graph.getVertex(num).getTotalVal()){
                res = num;
            }
        }

        return res;
    }

    private void makPath(int finish){
        int prev = graph.getVertex(finish).getCameFrom();
        path = new ArrayList<Integer>();
        path.add(finish);

        while(prev != -1){
            path.add(prev);
            prev = graph.getVertex(prev).getCameFrom();
        }
    }
}
