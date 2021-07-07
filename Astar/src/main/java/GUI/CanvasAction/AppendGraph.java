package GUI.CanvasAction;

import Graph.MyGraph;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AppendGraph extends MouseAdapter {
    private MyGraph graph;
    private int step;

    public AppendGraph(MyGraph graph, int step){
        this.graph = graph;
        this.step = step;
    }

    public void mouseClicked(MouseEvent me){
        int x = me.getX()/step;
        int y = me.getY()/step;
        if(graph.getVertex(x,y).isEmpty()){
            graph.addVertex(x + y + "", x, y);
            System.out.println(graph);
        }
        else{
            System.out.println("Already exists");
        }
    }
}
