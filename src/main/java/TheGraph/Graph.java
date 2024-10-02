package TheGraph;
import exceptions.NullObjectReceivedException;
import exceptions.WrongGraphMethodException;

/**
 * Interface for a Graph data structure.
 * @param <E> the type of elements stored in the vertices of the graph.
 */
public abstract class Graph<E> {
    /**
     * Indicates whether the graph is weighted or not.
     */
    boolean isWeighted;

    /**
     * Constructs a new Graph that can be weighted or unweighted
     * @param isWeighted indicates whether the graph is weighted or not.
     */
    public Graph(boolean isWeighted) {
        this.isWeighted = isWeighted;
    }

    /**
     * Adds a new vertex to the graph.
     * @param obj the element to be stored in the new vertex and the key to be accesed.
     * @return true if the vertex was added successfully, false otherwise.
     */
    public abstract boolean addVertex(E obj);

    /**
     * Adds a new unweighted edge between two vertices in the graph.
     * @param src the source vertex.
     * @param dest the destination vertex.
     * @return true if the edge was added successfully, false otherwise.
     */
    public abstract boolean addEdge(E src, E dest);

    /**
     * Adds a new weighted edge between two vertices in the graph.
     * @param src the source vertex.
     * @param dest the destination vertex.
     * @param weight the weight of the edge.
     * @return true if the edge was added successfully, false otherwise.
     */
    public abstract boolean addEdge(E src, E dest, double weight);

    /**
     * Adds a new unweighted directed arc from one vertex to another in the graph.
     * @param src the source vertex.
     * @param dest the destination vertex.
     * @return true if the arc was added successfully, false otherwise.
     */
    public abstract boolean addArc (E src, E dest);

    /**
     * Adds a new weighted directed arc from one vertex to another in the graph.
     * @param src the source vertex.
     * @param dest the destination vertex.
     * @param weight the weight of the arc.
     * @return true if the arc was added successfully, false otherwise.
     */
    public abstract boolean addArc (E src, E dest, double weight);

    /**
     * Gets the number of vertices in the graph.
     * @return the number of vertices in the graph.
     */
    public abstract int vertexCount();

    /**
     * Removes a vertex and all its incident edges/arcs from the graph.
     * @param obj the element stored (The key) in the vertex to be removed.
     * @return true if the vertex was removed successfully, false otherwise.
     */
    public abstract boolean removeVertex(E obj);

    /**
     * Removes a directed arc from the graph.
     * @param src the source vertex of the arc to be removed.
     * @param dest the destination vertex of the arc to be removed.
     * @return true if the arc was removed successfully, false otherwise.
     */
    public abstract boolean removeArc (E src, E dest);

    /**
     * Removes an undirected edge from the graph.
     * @param src one of the vertices incident to the edge to be removed.
     * @param dest the other vertex incident to the edge to be removed.
     * @return true if the edge was removed successfully, false otherwise.
     */
    public abstract boolean removeEdge(E src, E dest);

    /**
     * Updates the weight of a directed arc in the graph.
     * @param src the source vertex of the arc to be updated.
     * @param dest the destination vertex of the arc to be updated.
     * @param weight the new weight of the arc.
     * @return true if the arc weight was updated successfully, false otherwise.
     */
    public abstract boolean updateArc (E src, E dest, double weight);

    /**
     * Updates the weight of an undirected edge in the graph.
     * @param src one of the vertices incident to the edge to be updated.
     * @param dest the other vertex incident to the edge to be updated.
     * @param weight the new weight of the edge.
     * @return true if the edge weight was updated successfully, false otherwise.
     */
    public abstract boolean updateEdge(E src, E dest, double weight);

    /**
     * Gets the weight of a directed arc in the graph.
     * @param src the source vertex of the arc.
     * @param dest the destination vertex of the arc.
     * @return the weight of the arc.
     */
    public abstract Double  getArcWeight (E src, E dest);

    /**
     * Gets the weight of an undirected edge in the graph.
     * @param src one of the vertices incident to the edge.
     * @param dest the other vertex incident to the edge.
     * @return the weight of the edge.
     */
    public abstract Double  getEdgeWeight(E src, E dest);

    @Override
    public String toString() {
        return "Graph{" +
                "isWeighted=" + isWeighted +
                '}';
    }
}
