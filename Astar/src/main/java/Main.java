import Graph.*;
import Algo.*;
import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hi");
        String[] edges = new String[] {"A B 2", "A E 2", "B D 1", "D C 1", "B A 2", "E A 2", "D B 1", "C D 1", "E F 1", "D F 4"};
        String[] vertexes = new String[] {"A 0 0", "B 2 0" , "D 2 1", "C 1 1", "E 0 2", "F 0 3", "T 5 0"};
        MyGraph graph = new MyGraph(edges, vertexes);
        graph.printGraph();
        AWithStar algo = new AWithStar(graph);
        ArrayList<Integer> path = algo.doAlgo("A", "C");
        if(path == null){
            System.out.println("No path!");
            return;
        }
        
        for(int num : path){
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
