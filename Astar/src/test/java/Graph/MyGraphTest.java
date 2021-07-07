package Graph;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.*;

public class MyGraphTest {
    private MyGraph defaultGraph;
    private Logger logger = LogManager.getLogger();

    @Before
    public void setUp() throws Exception{
        String[] edges = new String[] {"A B 2", "A C 2", "B D 2", "B E 2", "D F 3", "E H 3", "F I 4", "F J 2", "I K 3", "J L 1", "H J 2", "L K 4"};
        String[] vertexes = new String[] {"A 0 0", "B 2 0" , "C 0 2", "D 4 0", "E 2 2", "F 4 3", "I 8 3", "J 4 5", "H 2 5", "K 8 6", "L 4 6"};
        try{
            defaultGraph = new MyGraph(edges, vertexes);
        }
        catch (IOException err){
            logger.error(err.getMessage());
        }
    }

    @Test
    public void graphCreation(){
        String[] edges = new String[] {"A B 2", "A C 2", "B D 4"};
        String[] vertexes = new String[] {"A 0 0", "B 2 0" , "C 0 2", "D 2 4"};
        MyGraph testGraph;

        try{
            testGraph = new MyGraph(edges, vertexes);
            assertEquals(1,1);
        }
        catch(IOException err){
            logger.error(err.getMessage());
            assertEquals(0,1);
        }

        vertexes = new String[] {"A 0 0", "A 2 0" , "C 0 2", "D 2 4"};
        try{
            testGraph = new MyGraph(edges, vertexes);
            assertEquals(0,1);
        }
        catch(IOException err){
            logger.error(err.getMessage());
            assertEquals(1,1);
        }

        vertexes = new String[] {"A 0 0", "B 2 0" , "C 0 2", "D 2 4"};
        edges = new String[] {"A U 2", "A C 2", "B D 4"};
        try{
            testGraph = new MyGraph(edges, vertexes);
            assertEquals(0,1);
        }
        catch(IOException err){
            logger.error(err.getMessage());
            assertEquals(1,1);
        }
    }

    @Test
    public void getSize(){
        int expected = 11;
        int actual = defaultGraph.getSize();
        assertEquals(expected, actual);
    }

    @Test
    public void addVertex() {
        String name = "T";
        int x = 10;
        int y = 10;
        int actualCount;
        int expected = 12;
        Vertex expectedVertex = new Vertex(name, x, y);
        try{
            defaultGraph.addVertex(name, x, y);
            actualCount = defaultGraph.getSize();
            assertEquals(expected, actualCount);
            assertEquals(expectedVertex, defaultGraph.getVertex(name));
        }
        catch (IOException err){
            logger.error(err.getMessage());
            assertEquals(0,1);
        }

        try{
            name = "A";
            defaultGraph.addVertex(name, x, y);
            assertEquals(1,0);
        }
        catch (IOException err){
            logger.error(err.getMessage());
            assertEquals(1,1);
        }
    }

    @Test
    public void addEdge() {
        String start = "C";
        String finish = "A";
        int weight = 2;
        Edge expectedEdge = new Edge(start, finish, weight);

        try{
            defaultGraph.addEdge(start, finish, weight);
            Vertex start_vertex = defaultGraph.getVertex(start).get();
            assertEquals(expectedEdge, start_vertex.getEdge(start_vertex.getEdgeAmount()-1));
        }
        catch (IOException err){
            logger.error(err.getMessage());
            assertEquals(1,0);
        }

        start = "Y";
        try{
            defaultGraph.addEdge(start, finish, weight);
            assertEquals(1, 0);
        }
        catch (IOException err){
            logger.error(err.getMessage());
            assertEquals(1,1);
        }

        start = "C";
        weight = 5;
        try{
            defaultGraph.addEdge(start, finish, weight);
            assertEquals(1, 0);
        }
        catch (IOException err){
            logger.error(err.getMessage());
            assertEquals(1,1);
        }

        weight = 2;
        try {
            defaultGraph.addEdge(start, finish, weight);
            defaultGraph.addEdge(start, finish, weight);
            defaultGraph.addEdge(start, finish, weight);
        }
        catch (IOException err){
            logger.error(err.getMessage());
            assertEquals(1,0);
        }

        try {
            defaultGraph.addEdge(start, finish, weight);
            assertEquals(1, 0);
        }
        catch (IOException err){
            logger.error(err.getMessage());
            assertEquals(1,1);
        }
    }

    @Test
    public void deleteVertex() {
        String name = "A";
        int expected_size = 10;
        int actual_size;

        try{
            defaultGraph.deleteVertex(name);
            actual_size = defaultGraph.getSize();
            assertEquals(expected_size, actual_size);
            assertNull(defaultGraph.getVertex(name));
        }
        catch (IndexOutOfBoundsException err){
            logger.error(err.getMessage());
            assertEquals(1,0);
        }

        name = "A";
        try{
            defaultGraph.deleteVertex(name);
            assertEquals(1,0);
        }
        catch (IndexOutOfBoundsException err){
            logger.error(err.getMessage());
            assertEquals(1,1);
        }

        MyGraph emptyGraph = new MyGraph();
        name = "Y";
        try{
            defaultGraph.deleteVertex(name);
            assertEquals(1,0);
        }
        catch (IndexOutOfBoundsException err){
            logger.error(err.getMessage());
            assertEquals(1,1);
        }
    }

    @Test
    public void deleteEdge() {
        String start = "A";
        String finish = "B";
        int expected_edge_count = defaultGraph.getVertex(start).get().getEdgeAmount() - 1;
        int actual_edge_count = 0;

        try{
            defaultGraph.deleteEdge(start,finish);
            actual_edge_count = defaultGraph.getVertex(start).get().getEdgeAmount();
            assertEquals(expected_edge_count, actual_edge_count);
        }
        catch (IndexOutOfBoundsException err){
            logger.error(err.getMessage());
            assertEquals(1,0);
        }

        start = "A";
        finish = "B";
        try{
            defaultGraph.deleteEdge(start,finish);
            assertEquals(1,0);
        }
        catch (IndexOutOfBoundsException err){
            logger.error(err.getMessage());
            assertEquals(1,1);
        }

        start = "Y";
        try{
            defaultGraph.deleteEdge(start,finish);
            assertEquals(1,0);
        }
        catch (IndexOutOfBoundsException err){
            logger.error(err.getMessage());
            assertEquals(1,1);
        }

        start = "A";
        finish = "O";
        try{
            defaultGraph.deleteEdge(start,finish);
            assertEquals(1,0);
        }
        catch (IndexOutOfBoundsException err){
            logger.error(err.getMessage());
            assertEquals(1,1);
        }
    }
}