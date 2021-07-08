package GUI;

import Graph.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.util.Calendar;

public class MyCanvas extends JComponent implements MouseListener{
    private MyGraph graph;
    private int step = 60;
    private int shift = step/8;
    private int cshift = step/2 - 1;
    private int start_x = 0;
    private int start_y = 0;
    private int finish_x = 0;
    private int finish_y = 0;
    private int append_click = 0;
    private int delete_click = 0;
    private long button_time = 0;

    public MyCanvas(MyGraph graph){
        addMouseListener(this);
        this.graph = graph;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == 3) {
            if(append_click == 0) {
                start_x = mouseEvent.getX() / step;
                start_y = mouseEvent.getY() / step;
            }
            append_click += 1;
        }

        if(mouseEvent.getButton() == 2) {
            if(delete_click == 0) {
                start_x = mouseEvent.getX() / step;
                start_y = mouseEvent.getY() / step;
            }
            delete_click += 1;
        }

        if(mouseEvent.getButton() == 1) {
            button_time = System.currentTimeMillis();
        }
    }

    private void addingVertex(MouseEvent mouseEvent) throws IndexOutOfBoundsException{
        int x = mouseEvent.getX() / step;
        int y = mouseEvent.getY() / step;
        if (graph.getVertex(x, y).isEmpty()) {
            graph.addVertex(graph.getSize() + "", x, y);
            repaint();
        } else {
            throw new IndexOutOfBoundsException("Vertex already exist here!");
        }
    }

    private void addingEdge(MouseEvent mouseEvent) throws IndexOutOfBoundsException{
        append_click = 0;
        finish_x = mouseEvent.getX()/step;
        finish_y = mouseEvent.getY()/step;
        Vertex start, finish;
        if(graph.getVertex(start_x, start_y).isPresent() & graph.getVertex(finish_x, finish_y).isPresent() && (start_x != finish_x || start_y != finish_y)) {
            start = graph.getVertex(start_x, start_y).get();
            finish = graph.getVertex(finish_x, finish_y).get();
            int weight = Math.abs(start.getX() - finish.getX()) + Math.abs(start.getY() - finish.getY());
            try{
                graph.addEdge(start.getLabel(), finish.getLabel(), weight);
            }
            catch (IOException err){
                throw new IndexOutOfBoundsException(err.getMessage());
            }
            repaint();
        }
        else{
            throw new IndexOutOfBoundsException("Fail");
        }
    }

    private void deletingEdge(MouseEvent mouseEvent) throws IndexOutOfBoundsException{
        delete_click = 0;
        finish_x = mouseEvent.getX()/step;
        finish_y = mouseEvent.getY()/step;
        Vertex start, finish;
        if(graph.getVertex(start_x, start_y).isPresent() & graph.getVertex(finish_x, finish_y).isPresent() && (start_x != finish_x || start_y != finish_y)) {
            start = graph.getVertex(start_x, start_y).get();
            finish = graph.getVertex(finish_x, finish_y).get();
            try{
                graph.deleteEdge(start.getLabel(), finish.getLabel());
            }
            catch (IndexOutOfBoundsException err){
                throw new IndexOutOfBoundsException(err.getMessage());
            }
            repaint();
        }
        else{
            throw new IndexOutOfBoundsException("Fail");
        }
    }

    private void deletingVertex(MouseEvent mouseEvent) throws IndexOutOfBoundsException{
        int x = mouseEvent.getX() / step;
        int y = mouseEvent.getY() / step;
        if (graph.getVertex(x, y).isPresent()) {
            graph.deleteVertex(graph.getVertex(x, y).get().getLabel());
            repaint();
        } else {
            throw new IndexOutOfBoundsException("Vertex already exist here!");
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == 1){
            button_time = System.currentTimeMillis() - button_time;
            if(button_time > 1000){
                try {
                    deletingVertex(mouseEvent);
                } catch (IndexOutOfBoundsException err) {
                    System.out.println("Fail to delete vertex");
                }
            }
            else {
                try {
                    addingVertex(mouseEvent);
                } catch (IndexOutOfBoundsException err) {
                    System.out.println("Already exist");
                }
            }
        }
        if(append_click == 2){
            try{
                addingEdge(mouseEvent);
            }
            catch (IndexOutOfBoundsException err){
                System.out.println("Fail append edge");
            }
        }

        if(delete_click == 2){
            try{
                deletingEdge(mouseEvent);
            }
            catch (IndexOutOfBoundsException err){
                System.out.println("Fail delete edge");
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

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
