package ejercicio1.virtual;

import java.util.List;
import java.util.stream.Collectors;
import ejercicio1.DatosEjercicio1;
import ejercicio1.Elegir;
import us.lsi.common.Lists2;
import us.lsi.common.Tuple;
import us.lsi.common.Tuple2;
import us.lsi.graphs.ActionVirtualVertex;

public class Ejercicio1Vertex extends ActionVirtualVertex<Ejercicio1Vertex, Ejercicio1Edge, Elegir> {
	
	public static Ejercicio1Vertex initial() {
		return new Ejercicio1Vertex(0, DatosEjercicio1.getSumaObjetivo(), DatosEjercicio1.getSumaObjetivo());
	}
	
	public static Ejercicio1Vertex of(Integer i, Integer sumaRestante1, Integer sumaRestante2) {
		return new Ejercicio1Vertex(i, sumaRestante1, sumaRestante2);
	}
	
	public Integer i;
	public Integer sumaRestante1;
	public Integer sumaRestante2;
	
	public Ejercicio1Vertex(Integer i, Integer sumaRestante1, Integer sumaRestante2) {
		super();
		this.i = i;
		this.sumaRestante1 = sumaRestante1;
		this.sumaRestante2 = sumaRestante2;
	}
	
	@Override
	public boolean isValid() {
		return 0<=i && i<DatosEjercicio1.getLista().size() && sumaRestante1>=0 && sumaRestante2>=0;
	}

	@Override
	protected List<Elegir> actions() {
		List<Elegir> result = Lists2.newList();
		if(sumaRestante1-DatosEjercicio1.getNumero(i)>=0)
			result.add(Elegir.CONJUNTO1);
		if(sumaRestante2-DatosEjercicio1.getNumero(i)>=0)
			result.add(Elegir.CONJUNTO2);	
		return result;
	}

	@Override
	protected Ejercicio1Vertex getThis() {
		return this;
	}

	@Override
	protected Ejercicio1Vertex neighbor(Elegir a) {
		Ejercicio1Vertex result = null;
		if(a.equals(Elegir.CONJUNTO1)) {
			result = of(i+1, sumaRestante1-DatosEjercicio1.getNumero(i), sumaRestante2);
		}else if(a.equals(Elegir.CONJUNTO2)) {
			result = of(i+1, sumaRestante1, sumaRestante2-DatosEjercicio1.getNumero(i));
		}
		return result;
	}

	@Override
	protected Ejercicio1Edge getEdge(Elegir a) {
		return Ejercicio1Edge.of(this, neighbor(a), a);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((i == null) ? 0 : i.hashCode());
		result = prime * result + ((sumaRestante1 == null) ? 0 : sumaRestante1.hashCode());
		result = prime * result + ((sumaRestante2 == null) ? 0 : sumaRestante2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ejercicio1Vertex other = (Ejercicio1Vertex) obj;
		if (i == null) {
			if (other.i != null)
				return false;
		} else if (!i.equals(other.i))
			return false;
		if (sumaRestante1 == null) {
			if (other.sumaRestante1 != null)
				return false;
		} else if (!sumaRestante1.equals(other.sumaRestante1))
			return false;
		if (sumaRestante2 == null) {
			if (other.sumaRestante2 != null)
				return false;
		} else if (!sumaRestante2.equals(other.sumaRestante2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "{" +i+","+sumaRestante1+","+sumaRestante2+ "}";
	}

	public static Tuple2<List<Integer>, List<Integer>> getSolucion(List<Ejercicio1Edge> camino) {
		Tuple2<List<Integer>, List<Integer>> result = null;
		List<Integer> sel1 = camino.stream().filter(e->e.a.equals(Elegir.CONJUNTO1))
				.map(e->DatosEjercicio1.getNumero(e.getSource().i)).collect(Collectors.toList());
		List<Integer> sel2 = camino.stream().filter(e->e.a.equals(Elegir.CONJUNTO2))
				.map(e->DatosEjercicio1.getNumero(e.getSource().i)).collect(Collectors.toList());
		result = Tuple.create(sel1, sel2);
		return result;
	}

}
