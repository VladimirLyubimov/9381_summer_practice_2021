package GUI.ButtonAction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import Graph.MyGraph;

public class RestartAlgoAction extends AbstractAction {
    private MyGraph graph;

    public RestartAlgoAction(MyGraph graph){
        this.graph = graph;
    }
    public void actionPerformed(ActionEvent event) {
        graph.resetGraph();
        graph.resetStartFinish();
        System.out.println("Return to the start. Algorithm and graph returned to original state!");
    }
}
