package ejercicio4;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;
import org.jgrapht.traverse.TopologicalOrderIterator;

import us.lsi.common.Files2;
import us.lsi.graphs.GraphsReader;

public class Ejercicio4 {

	public static void main(String[] args) {
		Graph<Monumento, Camino> grafo1 = GraphsReader.newGraph("ficheros/grafo1.txt", 
				Monumento::create, 
				Camino::create, 
				() -> new SimpleDirectedWeightedGraph<>(Monumento::create,Camino::create), 
				Camino::getTiempo);
		
		Graph<Monumento, Camino> grafo2 = GraphsReader.newGraph("ficheros/grafo2.txt", 
				Monumento::create, 
				Camino::create, 
				() -> new SimpleDirectedWeightedGraph<>(Monumento::create, Camino::create), 
				Camino::getTiempo);

		ConnectivityInspector<Monumento, Camino> grafoCon = new ConnectivityInspector<>(grafo1);
		System.out.println("¿Es conexo?: " + grafoCon.isConnected());
		System.out.println("Componentes conexas: " + grafoCon.connectedSets());
		
		TopologicalOrderIterator<Monumento, Camino> grafoTopo = new TopologicalOrderIterator<>(grafo1);
		
		List<Monumento> ordenTopo = new ArrayList<>();
		grafoTopo.forEachRemaining(v->ordenTopo.add(v));
		System.out.println(ordenTopo);
			
		
//		DOTExporter<Monumento,Camino> de1 = new DOTExporter<Monumento,Camino>(
//				new IntegerComponentNameProvider<>(),
//				x->x.getNombre(), 
//				x->x.getId()+"--"+x.getTiempo());
//		
//		PrintWriter f = Files2.getWriter("ficheros/grafo1.gv");
//		de1.exportGraph(grafo, f);
	}

}
