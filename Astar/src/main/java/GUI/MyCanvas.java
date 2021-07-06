package GUI;

import Graph.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class MyCanvas extends JComponent {
    private MyGraph graph;
    private int step = 60;
    private int shift = step/8;
    private int cshift = step/2 - 1;

    public MyCanvas(MyGraph graph){
        this.graph = graph;
    }

    @Override
    public void paintComponent(Graphics graphics){
        Graphics2D g2d = (Graphics2D) graphics;
        drawGraph(graph, g2d);
    }

    public void drawGraph(MyGraph graph, Graphics2D g2d){
        int graph_size = graph.getSize();
        Vertex cur_ver;
        for(int i = 0; i < graph_size; i++){
            cur_ver = graph.getVertex(i);
            int edge_count = cur_ver.getEdgeAmount();
            Edge cur_edge;
            for(int j = 0; j < edge_count; j++){
                cur_edge = cur_ver.getEdge(j);
                g2d.drawLine(graph.getVertex(cur_edge.getStart()).getX()*step + cshift, graph.getVertex(cur_edge.getStart()).getY()*step + cshift, graph.getVertex(cur_edge.getFinish()).getX()*step + cshift, graph.getVertex(cur_edge.getFinish()).getY()*step + cshift);
            }
        }
        Color old_color = g2d.getColor();
        Color color = new Color(0,255,0);
        for(int i = 0; i < graph_size; i++){
            cur_ver = graph.getVertex(i);
            Ellipse2D circle = new Ellipse2D.Double(cur_ver.getX()*step + shift, cur_ver.getY()*step + shift, 3*step/4, 3*step/4);
            g2d.draw(circle);
            g2d.setColor(color);
            g2d.fill(circle);
            g2d.setColor(old_color);
            g2d.drawString(cur_ver.getLabel(), cur_ver.getX()*step + step/2 - cur_ver.getLabel().length()*4, cur_ver.getY()*step + 9*step/16);
        }
    }
}
