package GUI;

import Algo.AWithStar;
import GUI.AlgoVisual.AlgoVisualization;
import Graph.MyGraph;
import Graph.Vertex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.Timer;

public class Gui {
    private JFrame window;
    private MyCanvas graph_drawer;

    private JButton random_grah;
    private JButton unar_grah;
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
    private JLabel open_set_text;
    private JLabel time_text;

    private MyGraph graph;
    private final AlgoVisualization AVisual = new AlgoVisualization();
    private String start;
    private String finish;
    private boolean[] states = new boolean[] {false/*prepare algo*/, false/*finish reached*/};

    private Logger logger = LogManager.getLogger();

    public MyGraph getGraph(){
        return graph;
    }

    public Gui(MyGraph gr, String start, String finish){
        this.graph = gr;
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


        time_text = new JLabel("00:00:00");
        time_text.setBounds(610, 10, 200, 10);
        window.add(time_text);
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                time_text.setText(format.format(new java.util.Date()));
            }
        });
        timer.start();

        int[] graph_param = new int[4];
        random_grah = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    graph_param[0] = Integer.parseInt(vertex_amount.getText());
                    graph_param[1] = Integer.parseInt(edge_amount.getText());
                    graph_param[2] = Integer.parseInt(min_weight.getText());
                    graph_param[3] = Integer.parseInt(max_weight.getText());
                }
                catch (NumberFormatException err){
                    logger.error(err.getMessage(), err);
                    System.out.println("Invalid data! Try Again!");
                    return;
                }
                try {
                    makeRandomGraph(graph_param);
                    graph_drawer.updateGraph(graph);
                    System.out.println(graph);
                    graph_drawer.repaint();
                }
                catch (IOException err){
                    logger.error(err.getMessage(), err);
                }
            }
        });
        random_grah.setText("Create random graph");
        random_grah.setFont(font);
        random_grah.setMargin(inset);
        random_grah.setBounds(610, 300, 200, 30);
        window.add(random_grah);

        random_grah = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int vertex_count = 0;
                try {
                    vertex_count = Integer.parseInt(vertex_amount.getText());
                }
                catch (NumberFormatException err){
                    logger.error(err.getMessage(), err);
                    System.out.println("Invalid data! Try Again!");
                    return;
                }
                try {
                    makeUnarGraph(vertex_count);
                    graph_drawer.updateGraph(graph);
                    System.out.println(graph);
                    graph_drawer.repaint();
                }
                catch (IOException err){
                    logger.error(err.getMessage(), err);
                }
            }
        });
        random_grah.setText("Create unar graph");
        random_grah.setFont(font);
        random_grah.setMargin(inset);
        random_grah.setBounds(860, 300, 200, 30);
        window.add(random_grah);

        from_file = new JButton("Load graph from file");
        from_file.setFont(font);
        from_file.setMargin(inset);
        from_file.setBounds(860, 90, 200, 30);
        window.add(from_file);

        //open_set_text = new JLabel();
        //open_set_text.setBounds(100, 610, 1000, 80);
        //window.add(open_set_text);

        step_forward = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(!states[0]){
                    try {
                        AVisual.prepare(graph);
                        states[0] = true;
                    }
                    catch (IndexOutOfBoundsException err){
                        logger.error(err.getMessage(), err);
                        return;
                    }
                }
                if(!states[1]) {
                    states[1] = AVisual.makeStep(graph);
                    /*StringBuilder st = new StringBuilder("Open set in order of adding is:\n");
                    for(Vertex vertex : graph.getOpen_set()){
                        st.append("Vertex name : ").append(vertex.getLabel()).append("; Path value : ").append(vertex.getPathVal()).append("; Total value : ").append(vertex.getTotalVal()).append("; Came from : ").append(vertex.getCameFrom()).append("\n");
                    }
                    open_set_text.setText(new String(st));*/
                    graph_drawer.repaint();
                }
                else{
                    graph.setCurVertex(null);
                    graph_drawer.repaint();
                    System.out.println("End reached! All I can has been done!");
                }
            }
        });
        step_forward.setText("Step forward");
        step_forward.setFont(font);
        step_forward.setMargin(inset);
        step_forward.setBounds(860, 400, 200, 30);
        window.add(step_forward);

        step_back = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ArrayList<Vertex> close_set = graph.getClose_set();
                ArrayList<Vertex> open_set = graph.getOpen_set();
                ArrayList<Vertex> remove_set = new ArrayList<>();
                if(!close_set.isEmpty()){
                    Vertex cur_ver = close_set.get(close_set.size()-1);
                    close_set.remove(cur_ver);
                    for(Vertex vertex : open_set){
                        if(cur_ver.isLinked(vertex)){
                            remove_set.add(vertex);
                        }
                    }
                    for(Vertex vertex : remove_set){
                        vertex.resetVertex();
                        open_set.remove(vertex);
                    }
                    open_set.add(cur_ver);
                    graph.setCurVertex(cur_ver);
                    AWithStar.makePath(graph, cur_ver.getLabel(), graph.getPath());
                    graph.setPath(graph.getPath());
                    graph_drawer.repaint();
                }
                else{
                    System.out.println("You are in the start! I can't go in the past deeper!");
                }
            }
        });
        step_back.setText("Step back");
        step_back.setFont(font);
        step_back.setMargin(inset);
        step_back.setBounds(610, 400, 200, 30);
        window.add(step_back);

        go_end = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                states[0] = false;
                states[1] = false;
                graph.resetGraph();
                graph.setCurVertex(null);
                states[0] = true;
                states[1] = true;
                try {
                    ArrayList<String> path = AWithStar.doAlgo(graph);
                }
                catch (IndexOutOfBoundsException err){
                    logger.error(err.getMessage(), err);
                    return;
                }
                graph_drawer.repaint();
            }
        });
        go_end.setText("Go to end");
        go_end.setFont(font);
        go_end.setMargin(inset);
        go_end.setBounds(860, 450, 200, 30);
        window.add(go_end);

        go_start = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                graph.resetGraph();
                graph.resetStartFinish();
                graph.setCurVertex(null);
                states[0] = false;
                states[1] = false;
                System.out.println("Return to the start. Algorithm and graph returned to original state!");
                graph_drawer.repaint();
            }
        });
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

    public void makeRandomGraph(int[] graph_param) throws IOException {
        try {
            graph = new MyGraph(graph_param[0], graph_param[1], graph_param[2], graph_param[3]);
        }
        catch (IndexOutOfBoundsException err){
            logger.error(err.getMessage(), err);
            throw new IOException("Problem with random graph generation");
        }
    }

    public void makeUnarGraph(int vertex_count) throws IOException {
        try {
            graph = new MyGraph(vertex_count);
        }
        catch (IndexOutOfBoundsException err){
            logger.error(err.getMessage(), err);
            throw new IOException("Problem with random graph generation");
        }
    }
}
