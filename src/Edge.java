/**
 * Edge class
 */
public class Edge {
    /**
     * Which vertex the edge is coming from
     */
    protected Vertex from;

    /**
     * Which vertex the edge is going to
     */
    protected Vertex to;

    /**
     * Edge constructor
     *
     * @param from vertex the edge is coming from
     * @param to   vertex the edge is going to
     */
    public Edge(Vertex from, Vertex to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Get the value of the from vertex
     *
     * @return value of from vertex
     */
    public String getFrom() {
        return from.getValue();
    }

    /**
     * Get the value of the to vertex
     *
     * @return value of to vertex
     */
    public String getTo() {
        return to.getValue();
    }
}
