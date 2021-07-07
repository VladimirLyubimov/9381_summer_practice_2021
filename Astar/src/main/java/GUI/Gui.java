package GUI;

import Algo.AWithStar;
import GUI.ButtonAction.FinishAlgoAction;
import GUI.ButtonAction.RestartAlgoAction;
import Graph.MyGraph;

import javax.swing.*;
import java.awt.*;

public class Gui {
    private JFrame window;
    private MyCanvas graph_drawer;

    private JButton random_grah;
    private JButton from_file;
    private JButton step_forward;
    private JButton step_back;
    private JButton go_end;
    private JButton go_start;
    private JButton add_vertex;
    private JButton add_edge;

    private JTextField filename;
    private JTextField vertex_amount;
    private JTextField edge_amount;
    private JTextField min_max_weight;

    private MyGraph graph;
    private String start;
    private String finish;

    public Gui(MyGraph graph, String start, String finish){
        this.graph = graph;
        this.start =start;
        this.finish = finish;
        window = new JFrame("Practise project");
        window.setSize(1200, 600);
        window.setLayout(null);

        graph_drawer = new MyCanvas(this.graph);
        graph_drawer.setBounds(0,0, 600, 600);
        window.add(graph_drawer);

        Font font = new Font("Arial", Font.PLAIN, 12);
        Insets inset = new Insets(0,0,0,0);
        //Rectangle bounds = new Rectangle(610, 50, 200, 30);

        random_grah = new JButton("Create random graph");
        random_grah.setFont(font);
        random_grah.setMargin(inset);
        random_grah.setBounds(610, 50, 200, 30);
        window.add(random_grah);

        from_file = new JButton("Load graph from file");
        from_file.setFont(font);
        from_file.setMargin(inset);
        from_file.setBounds(860, 50, 200, 30);
        window.add(from_file);

        step_forward = new JButton("Step forward");
        step_forward.setFont(font);
        step_forward.setMargin(inset);
        step_forward.setBounds(860, 350, 200, 30);
        window.add(step_forward);

        step_back = new JButton("Step back");
        step_back.setFont(font);
        step_back.setMargin(inset);
        step_back.setBounds(610, 350, 200, 30);
        window.add(step_back);

        go_end = new JButton(new FinishAlgoAction(this.graph, this.start, this.finish));
        go_end.setText("Go to end");
        go_end.setFont(font);
        go_end.setMargin(inset);
        go_end.setBounds(860, 400, 200, 30);
        window.add(go_end);

        go_start = new JButton(new RestartAlgoAction(this.graph));
        go_start.setText("Go to start");
        go_start.setFont(font);
        go_start.setMargin(inset);
        go_start.setBounds(610, 400, 200, 30);
        window.add(go_start);

        add_vertex = new JButton("Add vertex");
        add_vertex.setFont(font);
        add_vertex.setMargin(inset);
        add_vertex.setBounds(610, 250, 200, 30);
        window.add(add_vertex);

        add_edge = new JButton("Add edge");
        add_edge.setFont(font);
        add_edge.setMargin(inset);
        add_edge.setBounds(860, 250, 200, 30);
        window.add(add_edge);

        filename = new JTextField();
        filename.setToolTipText("Input filename");
        filename.setBounds(860, 90, 200, 30);
        window.add(filename);

        vertex_amount = new JTextField();
        vertex_amount.setToolTipText("Input vertex amount");
        vertex_amount.setBounds(610, 90, 200, 30);
        window.add(vertex_amount);

        edge_amount = new JTextField();
        edge_amount.setToolTipText("Input edge amount");
        edge_amount.setBounds(610, 130, 200, 30);
        window.add(edge_amount);

        min_max_weight = new JTextField();
        min_max_weight.setToolTipText("Input minimal and maximal edge weights as <min weight><space><max weight>");
        min_max_weight.setBounds(610, 170, 200, 30);
        window.add(min_max_weight);

        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
