package TheGraphTest;
import exceptions.NullObjectReceivedException;
import exceptions.WrongGraphMethodException;
import TheGraph.*;

import java.util.function.DoubleFunction;

public class TestGraph {
    public static void main(String[] args) throws NullObjectReceivedException, WrongGraphMethodException {
        MarioGraph<String> strGraph = new MarioGraph<>(9,false);
        strGraph.addVertex("Yael");
        strGraph.addVertex("Beto");
        strGraph.addVertex("Yair");
        strGraph.addVertex("Juan");
        strGraph.addVertex("Nate");
        strGraph.addVertex("Saul");
        strGraph.addVertex("Xime");
        strGraph.addVertex("Mary");
        strGraph.addVertex("Sara");


        strGraph.addArc("Xime","Yael");
        strGraph.addArc("Nate","Yael");
        strGraph.addEdge("Juan","Nate");
        strGraph.addEdge("Juan","Xime");
        strGraph.addEdge("Beto","Juan");
        strGraph.addEdge("Beto","Xime");
        strGraph.addEdge("Beto","Yael");
        strGraph.addEdge("Beto","Saul");
        strGraph.addEdge("Beto","Nate");
        strGraph.addEdge("Xime","Nate");
        strGraph.addEdge("Juan","Saul");
        strGraph.addEdge("Juan","Yael");
        strGraph.addEdge("Saul","Yair");
        strGraph.addEdge("Mary","Sara");
        strGraph.addEdge("Beto","Sara");
        strGraph.addEdge("Xime","Sara");
        strGraph.addEdge("Xime","Mary");

        //strGraph.removeArc("Yair","Saul");
        //strGraph.removeEdge("Mary","Sara");
        System.out.println(strGraph.toString());
        //strGraph.removeVertex("Sara");
        //System.out.println(strGraph.toString());
    }
}
