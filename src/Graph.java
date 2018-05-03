import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

/**
 * Graph class
 */
public class Graph {

    /**
     * HashTable of vertices
     */
    private Hashtable<String, Vertex> vertices;

    /**
     * Graph constructor
     */
    public Graph() {
        vertices = new Hashtable<>();
    }

    /**
     * Check if graph is empty
     *
     * @return True if graph is empty, false otherwise
     */
    public boolean isEmpty() {
        return vertices.size() == 0;
    }

    /**
     * Get the vertex v
     *
     * @param v Given vertex
     * @return Requested vertex
     */
    public Vertex getVertex(String v) {
        return vertices.get(v);
    }

    /**
     * Get vertices of graph
     *
     * @return ArrayList of vertices
     */
    public ArrayList<Vertex> getVertices() {
        ArrayList<Vertex> result = new ArrayList<>();
        Set<String> keys = vertices.keySet();
        Iterator<String> iterator = keys.iterator();

        while (iterator.hasNext()) {
            result.add(vertices.get(iterator.next()));
        }

        return result;
    }

    /**
     * Add edge to graph
     *
     * @param from Vertex the edge is coming from
     * @param to   Vertex the edge is going to
     */
    public void addEdge(String from, String to) {
        if (vertices.get(from) == null) {
            vertices.put(from, new Vertex(from));
        }
        if (vertices.get(to) == null) {
            vertices.put(to, new Vertex(to));
        }
        if (!vertices.get(from).edges.contains(to)) {
            vertices.get(from).edges.put(to, new Edge(vertices.get(from), vertices.get(to)));
        }
    }

    /**
     * Turns graph into a string
     *
     * @return String of graph
     */
    public String toString() {
        String string = "";
        for (int i = 0; i < vertices.size(); i++)
            if (vertices.get(i) != null)
                string += i + ": " + vertices.get(i).toString() + "\n";
        return string;
    }
}