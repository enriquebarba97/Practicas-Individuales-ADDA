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
import org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths;
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
		Monumento m7 = Monumento.create("Sitio7");
		Monumento m8 = Monumento.create("Sitio8");
		Monumento m9 = Monumento.create("Sitio9");
		
		Graph<Monumento, Camino> grafoConexiones = GraphsReader.newGraph("ficheros/grafo1.txt", 
				Monumento::create, 
				Camino::create, 
				() -> new SimpleWeightedGraph<>(Monumento::create,Camino::create), 
				Camino::getTiempo);
		
		Graph<Monumento, Camino> grafoPrecedencias = GraphsReader.newGraph("ficheros/grafo2.txt", 
				Monumento::create, 
				Camino::create, 
				() -> new SimpleDirectedGraph<>(Camino.class));
		
		System.out.println("=========================================== APARTADO A ===========================================");
		estanConectados(grafoConexiones);
		
		System.out.println("=========================================== APARTADO B ===========================================");
		System.out.println("Los monumentos que puedes visitar sin visitar otro antes son: "+visitasSinPrecedencias(grafoPrecedencias));
		

		List<Monumento> recorrido = Arrays.asList(m0,m1,m2);
		List<Monumento> recorrido2 = Arrays.asList(m4,m5,m6);
		List<Monumento> recorrido3 = Arrays.asList(m0,m1,m3,m5,m7);
		List<Monumento> recorrido4 = Arrays.asList(m4,m8,m9);
		
		System.out.println("=========================================== APARTADO C ===========================================");
		apartadoC(grafoConexiones, grafoPrecedencias, recorrido);
		apartadoC(grafoConexiones, grafoPrecedencias, recorrido2);
		apartadoC(grafoConexiones, grafoPrecedencias, recorrido3);
		apartadoC(grafoConexiones, grafoPrecedencias, recorrido4);
		
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
			System.out.println("Todos los monumentos est�n conectados entre s�");
		}else {
			System.out.println("Los monumentos no est�n conectados entre s�");
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
			System.out.println("No es posible realizar el recorrido " + recorrido + " debido a que los monumentos no est�n conectados entre s�");
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
	
	private static void caminoMinimo(Graph<Monumento, Camino> grafoConexiones, List<Monumento> recorrido) {
		FloydWarshallShortestPaths<Monumento, Camino> grafo = new FloydWarshallShortestPaths<>(grafoConexiones);
		var caminos = IntStream.range(0, recorrido.size()-1)
				.mapToObj(i->grafo.getPath(recorrido.get(i), recorrido.get(i+1))).collect(Collectors.toList());
		double tiempo =	caminos.stream()
			.mapToDouble(c->c.getWeight()).sum();
		List<List<Monumento>> intermedio = caminos.stream()
				.map(c->c.getVertexList()).collect(Collectors.toList());
		List<Monumento> recorridoFinal = new ArrayList<>();
		recorridoFinal.addAll(intermedio.get(0));
		for(int i = 1; i<intermedio.size(); i++) {
			List<Monumento> camino = intermedio.get(i);
			recorridoFinal.addAll(camino.subList(1, camino.size()));
		}
		System.out.println("Recorrido m�nimo: " + recorridoFinal);
		System.out.println("Tiempo: " + tiempo);
	}
	
}
