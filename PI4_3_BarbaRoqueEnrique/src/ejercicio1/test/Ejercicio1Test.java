package ejercicio1.test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import ejercicio1.DatosEjercicio1;
import ejercicio1.Ejercicio1BT;
import ejercicio1.Ejercicio1PD;
import ejercicio1.virtual.Ejercicio1Edge;
import ejercicio1.virtual.Ejercicio1Vertex;
import us.lsi.astar.AStarAlgorithm;
import us.lsi.astar.AStarGraph;
import us.lsi.astar.AStarSimpleVirtualGraph;
import us.lsi.bt.AlgoritmoBT;
import us.lsi.pd.AlgoritmoPD;

public class Ejercicio1Test {
	
	public static void main(String[] args) {
		List<Integer> lista = Arrays.asList(1, 3, 1, 1, 2, 5, 8, 10, 6, 11);
		DatosEjercicio1.setLista(lista);
		
		System.out.println("================================================= Ejercicio1 PD =================================================");
		Ejercicio1PD p = Ejercicio1PD.create(lista);
		var alg = AlgoritmoPD.createPDR(p);
		alg.ejecuta();
		System.out.println(alg.getSolucion());
		
		
		System.out.println("================================================= Ejercicio1 BT =================================================");
		
		Ejercicio1BT estadoInicial = Ejercicio1BT.create();
		
		var algBT = AlgoritmoBT.create(estadoInicial);
		algBT.ejecuta();
		
		System.out.println(algBT.getSolucion());
		
		System.out.println("================================================= Ejercicio1 Grafos Virtuales =================================================");
		
		Ejercicio1Vertex inicial = Ejercicio1Vertex.initial();
		Predicate<Ejercicio1Vertex> goal = (Ejercicio1Vertex v)->v.i==DatosEjercicio1.getLista().size();
		AStarGraph<Ejercicio1Vertex,Ejercicio1Edge> graph = AStarSimpleVirtualGraph.of(e->e.getEdgeWeight());
		var algGV = AStarAlgorithm.of(graph,inicial,goal,null);
		List<Ejercicio1Edge> camino = algGV.getPath().getEdgeList();
		System.out.println(Ejercicio1Vertex.getSolucion(camino));
		
	}
}
