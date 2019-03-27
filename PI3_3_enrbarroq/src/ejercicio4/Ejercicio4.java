package ejercicio4;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
		Graph<Monumento, Camino> grafoConexiones = GraphsReader.newGraph("ficheros/grafo1.txt", 
				Monumento::create, 
				Camino::create, 
				() -> new SimpleWeightedGraph<>(Monumento::create,Camino::create), 
				Camino::getTiempo);
		
		Graph<Monumento, Camino> grafoPrecedencias = GraphsReader.newGraph("ficheros/grafo2.txt", 
				Monumento::create, 
				Camino::create, 
				() -> new SimpleDirectedWeightedGraph<>(Monumento::create, Camino::create), 
				Camino::getTiempo);
		
		estanConectados(grafoConexiones);
		
		
		System.out.println("Los monumentos que puedes visitar sin visitar otro antes son: "+visitasSinPrecedencias(grafoPrecedencias));
		
		Monumento m0 = Monumento.create("Sitio0");
		Monumento m1 = Monumento.create("Sitio1");
		Monumento m2 = Monumento.create("Sitio2");
		Monumento m4 = Monumento.create("Sitio4");
		Monumento m5 = Monumento.create("Sitio5");
		Monumento m6 = Monumento.create("Sitio6");
		List<Monumento> recorrido = Arrays.asList(m0,m1,m2,m5);
		List<Monumento> recorrido2 = Arrays.asList(m4,m5,m6);
		
		System.out.println("¿Se puede recorrer " + recorrido + " según el grafo de precedencias?: " + checkPrecedencias(grafoPrecedencias, recorrido));
		System.out.println("¿Se puede recorrer " + recorrido2 + " según el grafo de precedencias?: " + checkPrecedencias(grafoPrecedencias, recorrido2));
		
		DOTExporter<Monumento,Camino> de1 = new DOTExporter<Monumento,Camino>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre(), 
				x-> "T = " +x.getTiempo());
		
		DOTExporter<Monumento,Camino> de2 = new DOTExporter<Monumento,Camino>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre(),
				null);
		
		PrintWriter f = Files2.getWriter("ficheros/grafo1.gv");
		de1.exportGraph(grafoConexiones, f);
		
		PrintWriter f2 = Files2.getWriter("ficheros/grafo2.gv");
		de2.exportGraph(grafoPrecedencias, f2);
	}
	
	
	private static void estanConectados(Graph<Monumento, Camino> grafoConexiones) {
		ConnectivityInspector<Monumento, Camino> grafoCon = new ConnectivityInspector<>(grafoConexiones);
		if(grafoCon.isConnected()) {
			System.out.println("Todos los monumentos están conectados entre sí");
		}else {
			System.out.println("Los monumentos no están conectados entre sí");
			System.out.println("Componentes conexas: " + grafoCon.connectedSets());
		}
	}
	
	private static List<Monumento> visitasSinPrecedencias(Graph<Monumento, Camino> grafoPrecedencias){
		return grafoPrecedencias.vertexSet().stream()
				.filter(v -> grafoPrecedencias.inDegreeOf(v)==0)
				.collect(Collectors.toList());
	}
	
	private static Boolean checkPrecedencias(Graph<Monumento, Camino> grafoPrecedencias, List<Monumento> recorrido) {
		Boolean result = true;
		int i = recorrido.size()-1;
		while(i>=0 && result) {
			Set<Monumento> precedencias = grafoPrecedencias.incomingEdgesOf(recorrido.get(i)).stream()
					.map(e->e.getSource()).collect(Collectors.toSet());
			List<Monumento> parcial = recorrido.subList(0, i);
			result = precedencias.stream().allMatch(p->parcial.contains(p));
			i--;
		}
		return result;
	}

}
