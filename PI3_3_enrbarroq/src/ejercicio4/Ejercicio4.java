package ejercicio4;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

import us.lsi.common.Files2;
import us.lsi.graphs.GraphsReader;

public class Ejercicio4 {

	public static void main(String[] args) {
		
		Monumento m0 = Monumento.create("Sitio0");
		Monumento m1 = Monumento.create("Sitio1");
		Monumento m2 = Monumento.create("Sitio2");
		Monumento m3 = Monumento.create("Sitio3");
		Monumento m4 = Monumento.create("Sitio4");
		Monumento m5 = Monumento.create("Sitio5");
		Monumento m6 = Monumento.create("Sitio6");
		
		Graph<Monumento, Camino> grafoConexiones = GraphsReader.newGraph("ficheros/grafoConexiones.txt", 
				Monumento::create, 
				Camino::create, 
				() -> new SimpleWeightedGraph<>(Monumento::create,Camino::create), 
				Camino::getTiempo);
		
		Graph<Monumento, Camino> grafoPrecedencias = GraphsReader.newGraph("ficheros/grafoPrecedencias.txt", 
				Monumento::create, 
				Camino::create, 
				() -> new SimpleDirectedGraph<>(Camino.class));
		
		System.out.println("=========================================== APARTADO A ===========================================");
		estanConectados(grafoConexiones);
		
		System.out.println("=========================================== APARTADO B ===========================================");
		System.out.println("Los monumentos que puedes visitar sin visitar otro antes son: "+visitasSinPrecedencias(grafoPrecedencias));
		

		List<Monumento> recorrido1 = Arrays.asList(m0,m1,m4);
		List<Monumento> recorrido2 = Arrays.asList(m0,m1,m3,m5);
		
		System.out.println("=========================================== APARTADO C ===========================================");
		apartadoC(grafoConexiones, grafoPrecedencias, recorrido1);
		apartadoC(grafoConexiones, grafoPrecedencias, recorrido2);
		
		DOTExporter<Monumento,Camino> de1 = new DOTExporter<Monumento,Camino>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre(), 
				x-> "T = " +x.getTiempo());
		
		DOTExporter<Monumento,Camino> de2 = new DOTExporter<Monumento,Camino>(
				new IntegerComponentNameProvider<>(),
				x->x.getNombre(),
				null);
		
		PrintWriter f = Files2.getWriter("ficheros/grafoConexiones.gv");
		de1.exportGraph(grafoConexiones, f);
		
		PrintWriter f2 = Files2.getWriter("ficheros/grafoPrecedencias.gv");
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
	

	
	private static void apartadoC(Graph<Monumento, Camino> grafoConexiones, Graph<Monumento, Camino> grafoPrecedencias, List<Monumento> recorrido) {
		Boolean result = true;
		ConnectivityInspector<Monumento, Camino> grafoCon = new ConnectivityInspector<>(grafoConexiones);
		result = IntStream.range(0, recorrido.size()-1).allMatch(i -> grafoCon.pathExists(recorrido.get(i), recorrido.get(i+1)));
		if(result) {
			result = checkPrecedencias(grafoPrecedencias, recorrido);
			if(result) {
				System.out.println("Es posible realizar el recorrido " + recorrido);
				caminoMinimo(grafoConexiones, recorrido);
			}else {
				System.out.println("No es posible realizar el recorrido " + recorrido + ", ya que no se cumplen las precedencias");
			}
		}else {
			System.out.println("No es posible realizar el recorrido " + recorrido + " debido a que los monumentos no están conectados entre sí");
		}
	}
	
	private static Boolean checkPrecedencias(Graph<Monumento, Camino> grafoPrecedencias, List<Monumento> recorrido) {
		Boolean result = true;
		int i = recorrido.size()-1;
		while(i>=0 && result) {
			Set<Monumento> precedencias = grafoPrecedencias.incomingEdgesOf(recorrido.get(i)).stream()
					.map(e->e.getSource()).collect(Collectors.toSet());
			if(!precedencias.isEmpty()) {
				List<Monumento> parcial = recorrido.subList(0, i);
				result = precedencias.stream().anyMatch(p->parcial.contains(p));
			}
			i--;
		}
		return result;
	}
	
	// Camino minimo con Dijkstra
	private static void caminoMinimo(Graph<Monumento, Camino> grafoConexiones, List<Monumento> recorrido) {
		DijkstraShortestPath<Monumento, Camino> grafo = new DijkstraShortestPath<>(grafoConexiones);
		
		// Lista de caminos mínimos de un vértice del recorrido al siguiente
		var caminos = IntStream.range(0, recorrido.size()-1)
				.mapToObj(i->grafo.getPath(recorrido.get(i), recorrido.get(i+1))).collect(Collectors.toList());
		
		// Tiempo del camino total
		double tiempo =	caminos.stream()
			.mapToDouble(c->c.getWeight()).sum();
		
		// Presentación del camino en una lista de vétices
		
		// Obtenemos las listas de vértices de los caminos intermedios
		List<List<Monumento>> intermedio = caminos.stream()
				.map(c->c.getVertexList()).collect(Collectors.toList());
		
		// Los juntamos en una lista quitando los vértices redundantes
		List<Monumento> recorridoFinal = new ArrayList<>();
		recorridoFinal.addAll(intermedio.get(0));
		for(int i = 1; i<intermedio.size(); i++) {
			List<Monumento> camino = intermedio.get(i);
			recorridoFinal.addAll(camino.subList(1, camino.size()));
		}
		System.out.println("Recorrido mínimo: " + recorridoFinal);
		System.out.println("Tiempo: " + tiempo);
	}	
	
}
