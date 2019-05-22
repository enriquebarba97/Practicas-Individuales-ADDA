package ejercicio1.virtual;

import ejercicio1.Elegir;
import us.lsi.graphs.SimpleEdge;

public class Ejercicio1Edge extends SimpleEdge<Ejercicio1Vertex> {
	
	public static Ejercicio1Edge of(Ejercicio1Vertex v1, Ejercicio1Vertex v2, Elegir a) {
		Double weight = a.equals(Elegir.CONJUNTO1) ? 1. : 0.;
		return new Ejercicio1Edge(v1, v2, weight, a);
	}
	
	public Elegir a;
	
	public Ejercicio1Edge(Ejercicio1Vertex v1, Ejercicio1Vertex v2, Double weight, Elegir a) {
		super(v1, v2, weight);
		this.a = a;
	}
	public Ejercicio1Edge(Ejercicio1Vertex v1, Ejercicio1Vertex v2) {
		super(v1, v2);
	}
	
	@Override
	public String toString() {
		return getSource() + " <" + a + "> " + getTarget();
	}
}
