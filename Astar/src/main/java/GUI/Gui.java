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

    private JTextField filename;
    private JTextField vertex_amount;
    private JTextField edge_amount;
    private JTextField min_weight;
    private JTextField max_weight;

    private JLabel filename_text;
    private JLabel vertex_amount_text;
    private JLabel edge_amount_text;
    private JLabel min_weight_text;
    private JLabel max_weight_text;

    private MyGraph graph;
    private String start;
    private String finish;

    public Gui(MyGraph graph, String start, String finish){
        this.graph = graph;
        this.start =start;
        this.finish = finish;
        window = new JFrame("Practise project");
        window.setSize(1200, 650);
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
        random_grah.setBounds(610, 300, 200, 30);
        window.add(random_grah);

        from_file = new JButton("Load graph from file");
        from_file.setFont(font);
        from_file.setMargin(inset);
        from_file.setBounds(860, 90, 200, 30);
        window.add(from_file);

        step_forward = new JButton("Step forward");
        step_forward.setFont(font);
        step_forward.setMargin(inset);
        step_forward.setBounds(860, 400, 200, 30);
        window.add(step_forward);

        step_back = new JButton("Step back");
        step_back.setFont(font);
        step_back.setMargin(inset);
        step_back.setBounds(610, 400, 200, 30);
        window.add(step_back);

        go_end = new JButton(new FinishAlgoAction(this.graph, this.start, this.finish));
        go_end.setText("Go to end");
        go_end.setFont(font);
        go_end.setMargin(inset);
        go_end.setBounds(860, 450, 200, 30);
        window.add(go_end);

        go_start = new JButton(new RestartAlgoAction(this.graph));
        go_start.setText("Go to start");
        go_start.setFont(font);
        go_start.setMargin(inset);
        go_start.setBounds(610, 450, 200, 30);
        window.add(go_start);

        filename = new JTextField();
        filename.setToolTipText("Input filename");
        filename.setBounds(860, 50, 200, 30);
        window.add(filename);

        vertex_amount = new JTextField();
        vertex_amount.setToolTipText("Input vertex amount");
        vertex_amount.setBounds(610, 50, 200, 30);
        window.add(vertex_amount);

        edge_amount = new JTextField();
        edge_amount.setToolTipText("Input edge amount");
        edge_amount.setBounds(610, 120, 200, 30);
        window.add(edge_amount);

        min_weight = new JTextField();
        min_weight.setToolTipText("Input minimal edge weight");
        min_weight.setBounds(610, 190, 200, 30);
        window.add(min_weight);

        max_weight = new JTextField();
        max_weight.setToolTipText("Input maximal edge weight");
        max_weight.setBounds(610, 260, 200, 30);
        window.add(max_weight);

        filename_text = new JLabel("Input filename:");
        filename_text.setBounds(860, 30, 200, 20);
        window.add(filename_text);

        vertex_amount_text = new JLabel("Input vertex amount:");
        vertex_amount_text.setBounds(610, 30, 200, 20);
        window.add(vertex_amount_text);

        filename_text = new JLabel("Input edge amount:");
        filename_text.setBounds(610, 100, 200, 20);
        window.add(filename_text);

        filename_text = new JLabel("Input minimal edge weight:");
        filename_text.setBounds(610, 170, 200, 20);
        window.add(filename_text);

        filename_text = new JLabel("Input maximal edge weight:");
        filename_text.setBounds(610, 240, 200, 20);
        window.add(filename_text);

        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
