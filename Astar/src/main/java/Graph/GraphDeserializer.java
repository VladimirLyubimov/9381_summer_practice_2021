package Graph;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GraphDeserializer implements JsonDeserializer<MyGraph> {
    private static Logger logger = LogManager.getLogger(Graph.GraphDeserializer.class);

    @Override
    public MyGraph deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String ver_st = jsonObject.get("vertexes").getAsString();
        if(!ver_st.matches("(\\w+\\s\\d+\\s\\d+,)*")){
            return new MyGraph();
        }

        String edge_st = jsonObject.get("edges").getAsString();
        if(!edge_st.matches("(\\w+\\s\\w+\\s\\d+,)*")){
            return new MyGraph();
        }

        if(ver_st.length() > 0){
            ver_st = ver_st.substring(0, ver_st.length()-1);
        }

        if(edge_st.length() > 0){
            edge_st = edge_st.substring(0, edge_st.length()-1);
        }

        String[] vertex_list = ver_st.split(",");
        String[] edge_list = edge_st.split(",");
        MyGraph graph;
        try{
            graph = new MyGraph(edge_list, vertex_list);
        }
        catch (IndexOutOfBoundsException err){
            logger.error(err.getMessage(), err);
            graph = new MyGraph();
        }

        return graph;
    }
}
