package GUI.ButtonAction;

import Algo.AWithStar;
import Graph.MyGraph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class FinishAlgoAction extends AbstractAction {
    private MyGraph graph;
    private String start;
    private String finish;

    public FinishAlgoAction(MyGraph graph, String start, String finish){
        this.graph = graph;
        this.start = start;
        this.finish = finish;
    }
    public void actionPerformed(ActionEvent event){
        graph.resetGraph();
        ArrayList<String> path = AWithStar.doAlgo(graph, start, finish);
        for(String label : path){
            System.out.print(label + " ");
        }
        System.out.println();
    }
}
