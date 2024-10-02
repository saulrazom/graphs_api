package TheGraphTest;
import exceptions.NullObjectReceivedException;
import exceptions.WrongGraphMethodException;
import TheGraph.*;

public class TestLuigiGraph {
    public static void main(String[] args)throws NullObjectReceivedException, WrongGraphMethodException {
        LuigiGraph<Integer> strGraph = new LuigiGraph<>(true);
        strGraph.addVertex(1);
        strGraph.addVertex(2);
        strGraph.addVertex(3);
        strGraph.addVertex(4);
        strGraph.addVertex(5);

        strGraph.addArc(2,1,1.4);
        strGraph.addArc(1,2,1.5);
        strGraph.addArc(3,5,0.7);
        strGraph.addArc(4,1,0.3);
        strGraph.addArc(4,1,1.2);
        strGraph.addArc(3,4,2.0);
        strGraph.addArc(2,5,0.5);
        strGraph.addArc(1,5,1.3);
        strGraph.addArc(5,3,0.8);
        strGraph.addArc(5,4,0.1);
        strGraph.addArc(4,3,0.8);
        strGraph.addArc(1,3,0.6);

        System.out.println(strGraph.toString());
        strGraph.DFS(4);
        strGraph.BFS(4);
    }
}
