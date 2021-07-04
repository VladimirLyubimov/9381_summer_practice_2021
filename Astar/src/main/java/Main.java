import Graph.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hi");
        String[] edges = new String[] {"A B 2", "A E 2", "B D 1", "D C 1"};
        String[] vertexes = new String[] {"A 0 0", "B 2 0" , "D 2 1", "C 1 1", "E 0 2"};
        MyGraph graph = new MyGraph(edges, vertexes);
        graph.printGraph();
    }
}
