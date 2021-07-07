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
        drawEdges(graph, g2d);
        drawVertexes(graph, g2d);
    }

    private void drawEdges(MyGraph graph, Graphics2D g2d){
        int graph_size = graph.getSize();
        Vertex cur_ver;
        Vertex fin_ver;
        for(int i = 0; i < graph_size; i++){
            cur_ver = graph.getVertex(i);
            int edge_count = cur_ver.getEdgeAmount();
            Edge cur_edge;
            for(int j = 0; j < edge_count; j++){
                cur_edge = cur_ver.getEdge(j);
                fin_ver = graph.getVertex(cur_edge.getFinish()).get();
                g2d.drawLine(cur_ver.getX()*step + cshift, cur_ver.getY()*step + cshift, fin_ver.getX()*step + cshift, fin_ver.getY()*step + cshift);

                if(cur_ver.getX() < fin_ver.getX()){
                    drawRightArrow(fin_ver.getX(), fin_ver.getY(), g2d);
                    continue;
                }
                if(cur_ver.getX() > fin_ver.getX()){
                    drawLeftArrow(fin_ver.getX(), fin_ver.getY(), g2d);
                    continue;
                }
                if(cur_ver.getY() < fin_ver.getY()){
                    drawDownArrow(fin_ver.getX(), fin_ver.getY(), g2d);
                    continue;
                }
                if(cur_ver.getY() > fin_ver.getY()){
                    drawUpArrow(fin_ver.getX(), fin_ver.getY(), g2d);
                    continue;
                }
            }
        }
    }

    private void drawRightArrow(int x, int y, Graphics2D g2d){
        g2d.drawLine(x*step,y*step + 3*step/8, x*step + step/8, y*step + cshift);
        g2d.drawLine(x*step,y*step + 5*step/8 - 1, x*step + step/8, y*step + cshift);
    }

    private void drawLeftArrow(int x, int y, Graphics2D g2d){
        g2d.drawLine(x*step + step - 1,y*step + 3*step/8, x*step + 7*step/8, y*step + cshift);
        g2d.drawLine(x*step + step - 1,y*step + 5*step/8 - 1, x*step + 7*step/8, y*step + cshift);
    }

    private void drawUpArrow(int x, int y, Graphics2D g2d){
        g2d.drawLine(x*step + 3*step/8,y*step + step - 1, x*step + cshift, y*step + 7*step/8);
        g2d.drawLine(x*step + 5*step/8 -1,y*step + step - 1, x*step + cshift, y*step + 7*step/8);
    }

    private void drawDownArrow(int x, int y, Graphics2D g2d){
        g2d.drawLine(x*step + 3*step/8,y*step, x*step + cshift, y*step + step/8);
        g2d.drawLine(x*step + 5*step/8 -1,y*step, x*step + cshift, y*step + step/8);
    }

    private void drawVertexes(MyGraph graph, Graphics2D g2d){
        int graph_size = graph.getSize();
        Vertex cur_ver;
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
