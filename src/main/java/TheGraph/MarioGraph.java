package TheGraph;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Class implementing a graph with adjacency matrix.
 * @param <E> the type of the elements stored in the vertices of the graph
 */
public class MarioGraph<E> extends Graph<E>{
    /**
     * The matrices will store the value of its index/vertex
     */
    private Boolean[][] adjBoolMatrix; //Boolean Adjancecy Matrix
    private Double[][] adjWeightMatrix; //Double Adjancecy Matrix
    /**
     * The ArrayList will store the vertices. The vertex index in the list is the same
     * in the matrix
     */
    private ArrayList<E> vertices;

    private boolean[] visited;

    /**
     * Constructor for MarioGraph class.
     * @param numVrx a int value indicating the number of vertices the Matrix will have
     * @param isWeighted a boolean value indicating whether the graph is weighted or not
     */
    public MarioGraph(int numVrx,boolean isWeighted){
        super(isWeighted);
        if (!isWeighted) {
            this.adjBoolMatrix = new Boolean[numVrx][numVrx];
            for (int i = 0; i < adjBoolMatrix.length; i++){
                for(int j = 0; j < adjBoolMatrix.length; j++){
                    adjBoolMatrix[i][j] = false;
                }
                adjBoolMatrix[i][i] = true;
            }
        }
        if(isWeighted) {
            this.adjWeightMatrix = new Double[numVrx][numVrx]; //numVrx = Number of Vertex
            for(int i = 0; i < numVrx; i++){
                adjWeightMatrix[i][i] = 0.0;
            }
        }
        this.vertices = new ArrayList<>();
        visited = new boolean[numVrx];

    }

    /**
     * Adds a vertex to the graph.
     * @param vtx the element to be added as a vertex to the graph.
     * @return true if the vertex was added successfully, false if the element recived is null,
     * if already exists on the graph or if the Matrix is already full
     */
    @Override
    public boolean addVertex(E vtx) {
        if(super.isWeighted){
            if(vertices.size() == adjWeightMatrix.length) return false;
        }
        if(!super.isWeighted){
            if(vertices.size() == adjBoolMatrix.length) return false;
        }
        if(this.vertices.contains(vtx))
            return false;
        this.vertices.add(vtx);
        return true;
    }

    /**
     * Adds an unweighted edge between two vertices in the graph.
     * @param index1 the vertex in the row of the matrix
     * @param index2 the vertex in the column of the matrix
     * @return true if the edge was added successfully, false if the graph is weighted, if the
     * indexes are equal or do not exist
     */
    @Override
    public boolean addEdge(E index1, E index2) {
        if(super.isWeighted) return false;
        if(index1.equals(index2))
            return false;
        int ind1 = this.vertices.indexOf(index1);
        int ind2 = this.vertices.indexOf(index2);
        if(ind1 == -1 || ind2 == -1){
            return false;
        }
        if(!adjBoolMatrix[ind1][ind2] || !adjBoolMatrix[ind2][ind1]){
            adjBoolMatrix[ind1][ind2] = true;
            adjBoolMatrix[ind2][ind1] = true;
            return true;
        }
        return false;
    }

    /**
     * Adds a weighted edge between two vertices in the graph.
     * @param index1 the vertex in the row of the matrix
     * @param index2 the vertex in the column of the matrix
     * @param weight the weight of the edge
     * @return true if the edge was added successfully, false if the edge already exists,
     * if the index1 and index2 are equals or do not exist, or the matrix is not weighted.
     */
    @Override
    public boolean addEdge(E index1, E index2, double weight) {
        if(!super.isWeighted) return false;
        int ind1 = this.vertices.indexOf(index1);
        int ind2 = this.vertices.indexOf(index2);
        if(index1.equals(index2))
            return false;
        if(ind1 == -1 || ind2 == -1){
            return false;
        }
        if(adjWeightMatrix[ind1][ind2] == null || adjWeightMatrix[ind2][ind1] == null){
            adjWeightMatrix[ind1][ind2] = weight;
            adjWeightMatrix[ind2][ind1] = weight;
            return true;
        }
        return false;
    }

    /**
     * Adds a weighted arc from one vertex to another in the graph.
     * @param index1 the vertex in the row of the matrix
     * @param index2 the vertex in the column of the matrix
     * @param weight the weight of the arc.
     * @return true if the arc was added successfully, false if the arc already exists,
     * if the index1 and index2 are equal or do not exist, or the matrix is not weighted.
     */
    @Override
    public boolean addArc(E index1, E index2, double weight) {
        if(!super.isWeighted) return false;
        if(index1.equals(index2))
            return false;
        int ind1 = this.vertices.indexOf(index1);
        int ind2 = this.vertices.indexOf(index2);
        if(ind1 == -1 || ind2 == -1){
            return false;
        }
        if(adjWeightMatrix[ind1][ind2] == null){
            adjWeightMatrix[ind1][ind2] = weight;
            return true;
        }
        return false;
    }

    /**
     * Adds an unweighted arc from one vertex to another in the graph.
     * @param index1 the vertex in the row of the matrix
     * @param index2 the vertex in the column of the matrix
     * @return true if the arc was added successfully, false if the arc already exists, if the
     * indexes are equal or do not exist, or if the matrix is weighted.
     */
    @Override
    public boolean addArc(E index1, E index2){
        if(super.isWeighted) return false;
        if(index1.equals(index2))
            return false;
        int ind1 = this.vertices.indexOf(index1);
        int ind2 = this.vertices.indexOf(index2);
        if(ind1 == -1 || ind2 == -1){
            return false;
        }
        if(!adjBoolMatrix[ind1][ind2]){
            adjBoolMatrix[ind1][ind2] = true;
            return true;
        }
        return false;
    }

    /**
     * Gets the number of vertices in the graph.
     * @return the number of vertices in the graph
     */
    @Override
    public int vertexCount() {
        return this.vertices.size();
    }

    /**
     * Function used in removeVertex. When a vertex is deleted this functions helps removing the values in the matrix's column
     * of the indicated vertex, by sorting it to the last column
     * @param matrix the matrix that will be modificated
     * @param indObj the index of the vertex that will be deleted
     * @param lenght the size/lenght of the matrix (total number of vertices)
     */
    private void swapVertical(Object[][] matrix,int indObj, int lenght){
        for(int i = 0; i < lenght; i++){
            for(int j = indObj; j < lenght - 1; j++){
                matrix[i][j] = matrix[i][j++];
            }
        }
    }

    /**
     * Function used in removeVertex. When a vertex is deleted this functions helps removing the values in the matrix's row
     * of the indicated vertex, by sorting it to the last column
     * @param matrix the matrix that will be modificated
     * @param indObj the index of the vertex that will be deleted
     * @param length the size/lenght of the matrix (total number of vertices)
     */
    private void swapHorizontal(Object[][] matrix,int indObj, int length){
        for(int i = indObj; i < length - 1; i++){
            for(int j = 0; j < length; j++){
                matrix[i][j] = matrix[i][j++];
            }
        }
    }

    /**
     * Function used in removeVertex. When a vertex is deleted this functions helps filling with nulls the last row and column
     * of the matrix, which are the previous deleted vertex, indicating that there is a new vertex
     * available to add
     * @param matrix the matrix that will be modificated
     * @param length the size/lenght of the matrix (total number of vertices)
     */
    private void fillNulls(Object[][] matrix, int length){
        for(int i = 0; i < length; i++){
            matrix[i][length-1] = null;
        }
        for(int i = 0; i < length; i++){
            matrix[length-1][i] = null;
        }
    }

    /**
     * Removes the specified vertex from the graph by sorting the row and column the vertex
     * represent, and then filling them with null values. Indicating there is a new space available
     * for a new vertex.
     * @param vtx the vertex to be removed.
     * @return true if the vertex was successfully removed, false otherwise.
     */
    @Override
    public boolean removeVertex(E vtx) {
        if(vertices.contains(vtx)){
            int ind = vertices.indexOf(vtx);
            int length = vertices.size();
            if(super.isWeighted){
                swapVertical(adjWeightMatrix,ind,length);
                swapHorizontal(adjWeightMatrix,ind,length);
                fillNulls(adjWeightMatrix,length);
                vertices.remove(vtx);
                return true;
            }
            if(!super.isWeighted){
                swapVertical(adjBoolMatrix,ind,length);
                swapHorizontal(adjBoolMatrix,ind,length);
                fillNulls(adjBoolMatrix,length);
                vertices.remove(vtx);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the specified arc from the graph.
     * @param index1 the vertex in the row of the matrix to be removed
     * @param index2 the vertex in the column of the matrix to be removed
     * @return true if the arc was successfully removed, false when the indexes are equal or do not exist,
     * or if there is not an arc to remove.
     */
    @Override
    public boolean removeArc(E index1, E index2) {
        if(index1.equals(index2))
            return false;
        int ind1 = this.vertices.indexOf(index1);
        int ind2 = this.vertices.indexOf(index2);
        if(ind1 == -1 || ind2 == -1){
            return false;
        }
        if(!super.isWeighted){
            if(adjBoolMatrix[ind1][ind2]){
                adjBoolMatrix[ind1][ind2] = false;
                return true;
            }
        }
        if(super.isWeighted){
            if(adjWeightMatrix[ind1][ind2] == null){ //adjWeightMatrix[ind1][ind2] != null ||
                adjWeightMatrix[ind1][ind2] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the specified edge from the graph.
     * @param index1 the vertex in the row of the matrix to be removed
     * @param index2 the vertex in the column of the matrix to be removed
     * @return true if the edge was successfully removed, false when the indexes are equal or do not exist,
     * or if there is not an edge to remove.
     */
    @Override
    public boolean removeEdge(E index1, E index2) {
        if(index1.equals(index2))
            return false;
        int ind1 = this.vertices.indexOf(index1);
        int ind2 = this.vertices.indexOf(index2);
        if(ind1 == -1 || ind2 == -1){
            return false;
        }
        if(!super.isWeighted){
            if(adjBoolMatrix[ind1][ind2] && adjBoolMatrix[ind2][ind1]){
                adjBoolMatrix[ind1][ind2] = false;
                adjBoolMatrix[ind2][ind1] = false;
                return true;
            }
        }
        if(super.isWeighted){
            if((adjWeightMatrix[ind1][ind2].equals(adjWeightMatrix[ind2][ind1])) && (adjWeightMatrix[ind1][ind2] != null)){
                adjWeightMatrix[ind1][ind2] = null;
                adjWeightMatrix[ind2][ind1] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the weight of the specified arc.
     * If the graph is not weighted, returns false.
     * @param index1 the vertex in the row of the matrix to be removed
     * @param index2 the vertex in the column of the matrix to be removed
     * @param weight the new weight to be set.
     * @return true if the weight of the arc was successfully updated, If the arc does not exist in the graph, returns false.
     */
    @Override
    public boolean updateArc(E index1, E index2, double weight) {
        if(!super.isWeighted) return false;
        if(index1.equals(index2))
            return false;
        int ind1 = this.vertices.indexOf(index1);
        int ind2 = this.vertices.indexOf(index2);
        if(ind1 == -1 || ind2 == -1){
            return false;
        }
        if(adjWeightMatrix[ind1][ind2] != null){
            adjWeightMatrix[ind1][ind2] = weight;
            return true;
        }
        return false;
    }

    /**
     * Updates the weight of the specified edge.
     * If the graph is not weighted, returns false.
     * @param index1 the vertex in the row of the matrix to be removed
     * @param index2 the vertex in the column of the matrix to be removed
     * @param weight the new weight to be set.
     * @return true if the weight of the edge was successfully updated, if the edge doesn't exist, returns false.
     */
    @Override
    public boolean updateEdge(E index1, E index2, double weight) {
        if(!super.isWeighted) return false;
        if(index1.equals(index2))
            return false;
        int ind1 = this.vertices.indexOf(index1);
        int ind2 = this.vertices.indexOf(index2);
        if(ind1 == -1 || ind2 == -1){
            return false;
        }
        if((adjWeightMatrix[ind1][ind2].equals(adjWeightMatrix[ind2][ind1])) && (adjWeightMatrix[ind1][ind2] != null)){
            adjWeightMatrix[ind1][ind2] = weight;
            adjWeightMatrix[ind2][ind1] = weight;
            return true;
        }
        return false;
    }

    /**
     * Get the weight of the arc between two vertices.
     * If the graph is not weighted, returns null
     * @param index1 the vertex in the row of the matrix
     * @param index2 the vertex in the column of the matrix
     * @return null if any of the elements is null or the arc doesn't exist.
     */
    @Override
    public Double getArcWeight(E index1, E index2) {
        if (!super.isWeighted) return null;
        if (index1.equals(index2))
            return null;
        int ind1 = this.vertices.indexOf(index1);
        int ind2 = this.vertices.indexOf(index2);
        if (ind1 == -1 || ind2 == -1) {
            return null;
        }
        return adjWeightMatrix[ind1][ind2];
    }

    /**
     * Get the weight of the edge between two vertices.
     * If the graph is not weighted, returns null
     * @param index1 the vertex in the row of the matrix
     * @param index2 the vertex in the column of the matrix
     * @return null if any of the elements is null or the edge doesn't exist.
     */
    @Override
    public Double getEdgeWeight(E index1, E index2) {
        if(!super.isWeighted) return null;
        if(index1.equals(index2))
            return null;
        int ind1 = this.vertices.indexOf(index1);
        int ind2 = this.vertices.indexOf(index2);
        if(ind1 == -1 || ind2 == -1){
            return null;
        }
        return adjWeightMatrix[ind1][ind2];
    }

    /**
     * Establishes the route starting from src vertex in depth
     * If the vertex doesn´t exist, an exception is thrown
     * A stack is used to add the path the method establishes (src -> neighbors)
     * @param src the source vertex key of the arc.
     */
    public void DFS(E src) {
        if (!vertices.contains(src)) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }
        System.out.println("DFS");
        Stack<E> stack = new Stack<>();
        int srcIndex = vertices.indexOf(src);
        visited[srcIndex] = true;
        stack.push(src);

        while (!stack.isEmpty()) {
            E current = stack.pop();
            System.out.print(current + " ");

            if (this.isWeighted) {
                for (int i = 0; i < adjWeightMatrix.length; i++) {
                    if (adjWeightMatrix[vertices.indexOf(current)][i] != null && !visited[i]) {
                        visited[i] = true;
                        stack.push(vertices.get(i));
                    }
                }
            } else {
                for (int i = 0; i < adjBoolMatrix.length; i++) {
                    if (adjBoolMatrix[vertices.indexOf(current)][i] && !visited[i]) {
                        visited[i] = true;
                        stack.push(vertices.get(i));
                    }
                }
            }
        }
    }

    /**
     * Establishes the route starting from src vertex in breadth
     * If the vertex doesn´t exist, an exception is thrown
     * A queue(LinkedList) is used to print the current value through its index
     * and poll this one, the process would be completed when queue is empty
     * @param src the source vertex key of the arc.
     */
    public void BFS(E src){
        if (!vertices.contains(src)) {
            throw new IllegalArgumentException("El vértice no existe en el grafo.");
        }
        System.out.println("BFS");
        int srcIndex = vertices.indexOf(src);
        visited = new boolean[visited.length];
        visited[srcIndex] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(srcIndex);

        while(!queue.isEmpty()){
            int current = queue.poll();
            System.out.print(vertices.get(current) + " ");

            if(this.isWeighted){
                for (int i = 0; i < adjWeightMatrix.length; i++) {
                    if (adjWeightMatrix[current][i] != null && !visited[i]) {
                        visited[i] = true;
                        queue.add(i);
                    }
                }
            }
            else{
                for (int i = 0; i < adjBoolMatrix.length; i++) {
                    if (adjBoolMatrix[current][i] == true && !visited[i]) {
                        visited[i] = true;
                        queue.add(i);
                    }
                }
            }
        }

    }

    /**
     * Prints the matrix that shows every vertex of the referred graph
     * If the graph is weighted, weight will be printed as well
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int size = vertices.size();
        if (super.isWeighted) {
            sb.append("  ");
            for (E vertex : vertices) {
                sb.append(String.format(" %s   ", vertex.toString()));
            }
            sb.append("\n");
            for (int i = 0; i < size; i++) {
                if (i < vertices.size()) {
                    sb.append(String.format("%s ", vertices.get(i)));
                }
                if (i > (vertices.size() - 1)) {
                    sb.append("  ");
                }
                for (int j = 0; j < size; j++) {
                    if (adjWeightMatrix[i][j] != null) {
                        sb.append(String.format("%.1f  ", adjWeightMatrix[i][j]));
                    }
                    if (adjWeightMatrix[i][j] == null) {
                        sb.append("null ");
                    }
                }
                sb.append("\n");
            }
        }
        if (!super.isWeighted) {
            sb.append("     ");
            for (E vertex : vertices) {
                sb.append(String.format("%s ", vertex));
            }
            sb.append("\n");
            for (int i = 0; i < size; i++) {
                if (i < vertices.size()) {
                    sb.append(String.format("%s ", vertices.get(i)));
                }
                if (i > (vertices.size() - 1)) {
                    sb.append("      ");
                }
                for (int j = 0; j < size; j++) {
                    if (adjBoolMatrix[i][j]) {
                        sb.append(" T   ");
                    }
                    if (!adjBoolMatrix[i][j]) {
                        sb.append(" F   ");
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}