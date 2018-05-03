import java.util.Hashtable;

/**
 * Vertex class
 */
public class Vertex {

    /**
     * Value of the vertex
     */
    private String value;

    /**
     * Vertex's edges
     */
    protected Hashtable<String, Edge> edges;
    
    /**
     * Parent Vertex
     */
    private Vertex parent;

    /**
     * Vertex constructor
     *
     * @param value value of the vertex
     */
    public Vertex(String value) {
        this.value = value;
        edges = new Hashtable<>();
    }

    /**
     * Get value of vertex
     *
     * @return String value
     */
    public String getValue() {
        return value;
    }

    /**
     * Get edges of vertex
     *
     * @return HashTable edges
     */
    public Hashtable<String, Edge> getEdges() {
        return edges;
    }
    
    public void setParent(Vertex v)
    {
    	parent = v;
    }
    
    public Vertex getParent()
    {
    	return parent;
    }
}
