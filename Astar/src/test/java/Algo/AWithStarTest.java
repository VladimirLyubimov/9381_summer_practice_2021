package Algo;

import Graph.MyGraph;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class AWithStarTest {
    private AWithStar algo;
    private MyGraph graph;

    @Before
    public void setUp() throws Exception {
        String[] edges = new String[] {"A B 2", "A C 2", "B D 2", "B E 2", "D F 3", "E H 3", "F I 4", "F J 2", "I K 3", "J L 1", "H J 2", "L K 4"};
        String[] vertexes = new String[] {"A 0 0", "B 2 0" , "C 0 2", "D 4 0", "E 2 2", "F 4 3", "I 8 3", "J 4 5", "H 2 5", "K 8 6", "L 4 6"};
        try{
            graph = new MyGraph(edges, vertexes);
        }
        catch (IOException err){
        }
        algo = new AWithStar(graph);
    }

    @Test
    public void doAlgo() {
        String start = "A";
        String finish = "K";
        String expected_path = "[A, B, D, F, I, K]";
        String actual_path;

        try{
            actual_path = algo.doAlgo(start, finish).toString();
            assertEquals(expected_path, actual_path);
        }
        catch (IndexOutOfBoundsException err){
            assertEquals(1,0);
        }

        algo.resetAlgo();
        start = "C";
        finish = "K";
        expected_path = "[No path!]";
        try{
            actual_path = algo.doAlgo(start, finish).toString();
            assertEquals(expected_path, actual_path);
        }
        catch (IndexOutOfBoundsException err){
            assertEquals(1,0);
        }

        algo.resetAlgo();
        start = "K";
        expected_path = "[Start and finish vertexes are same!]";
        try{
            actual_path = algo.doAlgo(start, finish).toString();
            assertEquals(expected_path, actual_path);
        }
        catch (IndexOutOfBoundsException err){
            assertEquals(1,0);
        }

        algo.resetAlgo();
        start = "A";
        finish = "O";
        expected_path = "[No path! Finish vertex doesn't exist!]";
        try{
            actual_path = algo.doAlgo(start, finish).toString();
            assertEquals(expected_path, actual_path);
        }
        catch (IndexOutOfBoundsException err){
            assertEquals(1,0);
        }

        algo.resetAlgo();
        start = "U";
        finish = "K";
        try{
            actual_path = algo.doAlgo(start, finish).toString();
            assertEquals(1,0);
        }
        catch (IndexOutOfBoundsException err){
            assertEquals(1,1);
        }
    }
}