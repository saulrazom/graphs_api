package TheGraph;
import exceptions.NullObjectReceivedException;
import exceptions.WrongGraphMethodException;

import java.util.*;
/**
 * Class implementing a graph with adjacency lists.
 * @param <E> the type of the elements stored in the vertices of the graph
 */
public class LuigiGraph<E> extends Graph<E>{
    /**
     * The vertexMap will map the value to its vertex
     */
    private final Map<E, Vertex> vertexMap;

    /**
     * Constructor for LuigiGraph class.
     * @param isWeighted a boolean value indicating whether the graph is weighted or not
     */
    public LuigiGraph(boolean isWeighted) {
        super(isWeighted);
        this.vertexMap = new HashMap<>();
    }

    /**
     * Private class representing a pair of vertices and their weight in the graph.
     */
    private class Pair {
        Vertex v;
        Double weight;
        public Pair(Vertex v, Double weight)
        {
            this.v = v;
            this.weight = weight;
        }
    }
    /**
     * Private class representing a vertex in the graph
     * It has a list of all the pairs (Vertex, weight) that are related to it.
     */
    private class Vertex {
        E key;
        boolean visited;
        List<Pair> neighbours;
        public Vertex(E key){
            this.key = key;
            this.neighbours = new ArrayList<>();
        }
    }

    /**
     * Adds a vertex to the graph.
     * @param vtx the element to be added as a vertex to the graph.
     * @return true if the vertex was added successfully, false if the element recived is null or already exists on the graph.
     */
    @Override
    public boolean addVertex(E vtx) {
        if (vertexMap.get(vtx) != null || vtx == null)
            return false;
        vertexMap.put(vtx, new Vertex(vtx));
        return true;
    }

    /**
     * Adds an unweighted edge between two vertices in the graph.
     * @param src the element in the source vertex of the edge.
     * @param dest the element in the destination vertex of the edge.
     * @return true if the edge was added successfully, false if the graph is weighted or any element is null
     */
    @Override
    public boolean addEdge(E src, E dest){
        if (this.isWeighted)
            return false;
        return addEdgeHelper(src, dest, null);
    }

    /**
     * Adds a weighted edge between two vertices in the graph.
     * @param src the element in the source vertex of the edge
     * @param dest the element in the destination vertex of the edge
     * @param weight the weight of the edge
     * @return true if the edge was added successfully, false if the graph is unweighted or any element is null
     */
    @Override
    public boolean addEdge(E src, E dest, double weight){
        if (!this.isWeighted)
            return false;
        return addEdgeHelper(src, dest, weight);
    }

    /**
     * Adds an edge between two vertices in the graph.
     * It helps with the two addEdge functions.
     * If there is only an arc between the two vertices, it will override its weight
     * @param src the element in the source vertex of the edge
     * @param dest the element in the destination vertex of the edge
     * @param weight the weight of the edge, if the vertex is unweighted its value is null.
     * @return true if the edge was added successfully, false if there's already an edge or two arcs.
     */
    private boolean addEdgeHelper(E src, E dest, Double weight){
        Vertex srcV = vertexMap.get(src);
        Vertex destV = vertexMap.get(dest);

        if (srcV == null || destV == null)
            return false;

        boolean srcDestArc = arcExists(src, dest);
        boolean destSrcArc = arcExists(dest, src);

        if (srcDestArc && destSrcArc)
            return false;

        if (srcDestArc && !destSrcArc) {
            destV.neighbours.add(new Pair(srcV, weight));
            for (Pair pair: srcV.neighbours){
                if (pair.v.key.equals(dest))
                    pair.weight = weight;
            }
        }
        else if(!srcDestArc && destSrcArc) {
            srcV.neighbours.add(new Pair(destV, weight));
            for (Pair pair : destV.neighbours) {
                if (pair.v.key.equals(dest))
                    pair.weight = weight;
            }
        }
        else {
            srcV.neighbours.add(new Pair(destV, weight));
            destV.neighbours.add(new Pair(srcV, weight));
        }
        return true;
    }

    /**
     * Checks if there is an arc between two vertices.
     * @param src the element in the source vertex of the edge
     * @param dest the element in the destination vertex of the edge
     * @return true if the arc exist, false otherwise
     */
    private boolean arcExists(E src, E dest){
        Vertex srcV = this.vertexMap.get(src);
        for (Pair pair: srcV.neighbours){
            if (pair.v.key.equals(dest)){
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an unweighted arc from one vertex to another in the graph.
     * @param src the element in the source vertex of the arc.
     * @param dest the element in the destination vertex of the arc.
     * @return true if the arc was added successfully, false if the arc already exists or any of the elements is null.
     */
    @Override
    public boolean addArc(E src, E dest){
        if (this.isWeighted)
            return false;
         return addArcHelper(src, dest, null);
    }

    /**
     * Adds a weighted arc from one vertex to another in the graph.
     * @param src the element in the source vertex of the arc.
     * @param dest the element in the destination vertex of the arc.
     * @param weight the weight of the arc.
     * @return true if the arc was added successfully, false if the arc already exists or any of the elements is null.
     */
    @Override
    public boolean addArc(E src, E dest, double weight) {
        if (!this.isWeighted)
            return false;
        return addArcHelper(src, dest, weight);
    }

    /**
     * Helps the function addArc to add any type of arc from one vertex to another in the graph.
     * @param src the element in the source vertex of the arc.
     * @param dest the element in the destination vertex of the arc.
     * @param weight the weight of the arc, if the graph is unweighted the value is null.
     * @return true if the arc was added successfully, false if the arc already exists.
     */
    private boolean addArcHelper(E src, E dest, Double weight){
        Vertex srcV = vertexMap.get(src);
        Vertex destV = vertexMap.get(dest);

        if (srcV == null || destV == null) return false;

        if (arcExists(src, dest))
            return false;

        srcV.neighbours.add(new Pair(destV, weight));
        return true;
    }

    /**
     * Gets the number of vertices in the graph.
     * @return the number of vertices in the graph
     */
    @Override
    public int vertexCount() {
        return vertexMap.size();
    }

    /**
     * Removes the specified vertex from the graph.
     * If the vertex does not exist in the graph, returns false.
     * Otherwise, all arcs and edges connected to the vertex are removed as well.
     * @param vtx the vertex to be removed.
     * @return true if the vertex was successfully removed, false otherwise.
     */
    @Override
    public boolean removeVertex(E vtx) {
        if (vertexMap.get(vtx) == null)
            return false;

        for (Vertex vertex: vertexMap.values()){
            vertex.neighbours.removeIf(pair -> pair.v.key.equals(vtx));
        }
        vertexMap.remove(vtx);
        return true;
    }

    /**
     * Removes the specified arc from the graph.
     * If the arc does not exist in the graph, returns false.
     * @param src the source vertex of the arc to be removed.
     * @param dest the destination vertex of the arc to be removed.
     * @return true if the arc was successfully removed, false otherwise.
     */
    @Override
    public boolean removeArc(E src, E dest) {
        if (src == null || dest == null)
            return false;

        Vertex srcV = this.vertexMap.get(src);
        return srcV.neighbours.removeIf(pair -> pair.v.key.equals(dest));
    }

    /**
     * Removes the specified edge from the graph.
     * If the edge does not exist in the graph, returns false.
     * @param src the source vertex of the edge to be removed.
     * @param dest the destination vertex of the edge to be removed.
     * @return true if the edge was successfully removed, false otherwise.
     */
    @Override
    public boolean removeEdge(E src, E dest) {
        if (arcExists(src, dest) && arcExists(dest, src))
            return removeArc(src, dest) && removeArc(dest, src);
        return false;
    }

    /**
     * Updates the weight of the specified arc.
     * If the graph is not weighted, returns false.
     * @param src the source vertex of the arc.
     * @param dest the destination vertex of the arc.
     * @param weight the new weight to be set.
     * @return true if the weight of the arc was successfully updated, If the arc does not exist in the graph, returns false.
     */
    @Override
    public boolean updateArc(E src, E dest, double weight) {
        if (!this.isWeighted)
            return false;

        if (src == null || dest == null)
            return false;

        Vertex srcV = this.vertexMap.get(src);
        for (Pair pair: srcV.neighbours){
            if (pair.v.key.equals(dest)){
                pair.weight = weight;
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the weight of the specified edge.
     * If the graph is not weighted, returns false.
     * @param src the source vertex of the edge.
     * @param dest the destination vertex of the edge.
     * @param weight the new weight to be set.
     * @return true if the weight of the edge was successfully updated, if the edge doesn't exist, returns false.
     */
    @Override
    public boolean updateEdge(E src, E dest, double weight) {
        if (arcExists(src, dest) && arcExists(dest, src))
            return updateArc(src, dest, weight) && updateArc(dest, src, weight);
        return false;
    }

    /**
     * Get the weight of the arc between two vertices.
     * If the graph is not weighted, returns null
     * @param src the source vertex of the arc.
     * @param dest the destination vertex of the arc.
     * @return null if any of the elements is null or the arc doesn't exist.
     */
    @Override
    public Double getArcWeight(E src, E dest) {
        if (src == null || dest == null)
            return null;

        Vertex srcV = this.vertexMap.get(src);
        for (Pair pair: srcV.neighbours){
            if (pair.v.key.equals(dest))
                return pair.weight;
        }
        return null;
    }

    /**
     * Get the weight of the edge between two vertices.
     * If the graph is not weighted, returns null
     * @param src the source vertex of the arc.
     * @param dest the destination vertex of the arc.
     * @return null if any of the elements is null or the edge doesn't exist.
     */
    @Override
    public Double getEdgeWeight(E src, E dest) {
        if (src == null || dest == null)
            return null;

        if (arcExists(src, dest) && arcExists(dest, src))
            return getArcWeight(src, dest);

        return null;
    }

    /**
     * Establishes the route starting from src vertex in depth
     * If the vertex doesn´t exist, an exception is thrown
     * @param src the source vertex key of the arc.
     */
    public void DFS(E src){ //Find all vertexes from a origin Vertex that can be accessed
        if (!vertexMap.containsKey(src)) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }
        System.out.println("DFS:");
        Vertex startVertex = vertexMap.get(src);
        resetVisited();
        Pair startPair = new Pair(startVertex, null);
        recursiveDFS(startPair);
    }

    /**
     * Helper method for DFS
     * @param pair the neighbor of the current visited vertex
     */
    private void recursiveDFS(Pair pair){
        pair.v.visited = true;
        System.out.print(pair.v.key + " -> ");
        for(Pair neighbor: pair.v.neighbours){
            if (!neighbor.v.visited) {
                recursiveDFS(neighbor);
            }
        }
    }

    /**
     * Establishes the route starting from src vertex in breadth
     * If the vertex doesn´t exist, an exception is thrown
     * @param src the source vertex key of the arc.
     */

    public void BFS(E src) {
        if (!vertexMap.containsKey(src)) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }
        System.out.println("\nBFS");
        resetVisited();

        Queue<Vertex> queue = new LinkedList<>();

        Vertex startVertex = vertexMap.get(src);
        startVertex.visited = true;
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            Vertex currentVertex = queue.poll();
            System.out.print(currentVertex.key + " -> ");

            // "Visits the neighbors of the current vertex and adds them to the queue if they have not been visited yet."
            for (Pair neighbor : currentVertex.neighbours) {
                if (!neighbor.v.visited) {
                    neighbor.v.visited = true;
                    queue.add(neighbor.v);
                }
            }
        }
    }

    /**
     * Helper method for DFS & BFS to mark as visited every traversed vertex and then remove that mark
     */
    private void resetVisited() {   // Método para reiniciar el estado de visita de todos los vértices
        for (Vertex vertex : vertexMap.values()) {
            vertex.visited = false;
        }
    }

    /**
     * Prints every vertex in referred graph
     * If the graph is weighted, weight will be printed as well
     */
    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        st.append("Lista de Adyacencia:\n");
        for (Vertex vertex : vertexMap.values()) {
            st.append(vertex.key);
            st.append(" -> ");
            for (Pair pair : vertex.neighbours) {
                st.append(pair.v.key);
                if (this.isWeighted) {
                    st.append(String.format(" (%.2f)", pair.weight));
                }
                st.append(", ");
            }
            st.setLength(st.length() - 2);
            st.append("\n");
        }
        return st.toString();
    }
} 