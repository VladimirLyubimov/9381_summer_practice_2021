import Graph.*;
import Algo.*;
import GUI.*;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hi");
        String[] edges = new String[] {"AGH B 2", "AGH C 2", "B D 2", "B E 2", "D F 3", "E H 3", "F I 4", "F J 2", "I K 3", "J L 1", "H J 2", "L K 4"};
        String[] vertexes = new String[] {"AGH 0 0", "B 2 0" , "C 0 2", "D 4 0", "E 2 2", "F 4 3", "I 8 3", "J 4 5", "H 2 5", "K 8 6", "L 4 6"};
        MyGraph graph;
        try{
            graph = new MyGraph(edges, vertexes);
        }
        catch (IOException err){
            System.out.println("Error!");
            return;
        }
        //MyGraph graph = new MyGraph();
        System.out.print(graph);

        //graph.deleteVertex("C");

        try{
            graph.addVertex("T", 3, 0);
            graph.addEdge("T", "D", 1);
            graph.addEdge("B", "T", 1);
            graph.deleteEdge("B", "D");
        }
        catch (IOException err){
            System.out.println("Error!");
            return;
        }

        /*try{
            graph.addEdge("F", "H", 1);
        }
        catch (IOException err){

        }*/

        //System.out.print(graph);

        ArrayList<String> path;
        /*try {
            path = algo.doAlgo("AGH", "K");
        }
        catch (IndexOutOfBoundsException err){
            System.out.println("Error!");
            return;
        }

        for(String label : path){
            System.out.print(label + " ");
        }
        System.out.println();*/

        //graph = new MyGraph(25, 48, 1, 2);
        //graph = new MyGraph(100);
        System.out.print(graph);
        Gui gui = new Gui(graph, "C", "14");
    }
}
