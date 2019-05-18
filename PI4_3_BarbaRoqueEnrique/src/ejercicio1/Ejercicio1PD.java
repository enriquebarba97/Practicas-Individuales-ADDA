package ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Tuple;
import us.lsi.common.Tuple2;
import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.AlgoritmoPD.Tipo;
import us.lsi.pd.ProblemaPDR;

public class Ejercicio1PD implements ProblemaPDR<Tuple2<List<Integer>,List<Integer>>, Elegir, Ejercicio1PD> {
	
	// PROPIEDADES COMPARTIDAS
	private static List<Integer> numeros; 	// Lista de numeros
	// PROPIEDADES INDIVIDUALES
	private Integer i;						// Posicion actual
	private Integer sumaRestante;			// Suma restante hasta el objetivo
	
	//Create
	public static Ejercicio1PD create(List<Integer> lista) {
		numeros = new ArrayList<>(lista);
		Integer suma = lista.stream().mapToInt(i->i).sum();
		if(suma%2!=0) {
			throw new IllegalArgumentException("La suma de los elementos de la lista debe ser par. Suma actual: " + suma);
		}else {
			return new Ejercicio1PD(0, suma/2);
		}
	}
	
	// Constructor
	public Ejercicio1PD(Integer i, Integer sumaRestante) {
		this.i = i;
		this.sumaRestante = sumaRestante;
	}
	
	@Override
	public Tipo getTipo() {
		return Tipo.Min;
	}

	@Override
	public int size() {
		return numeros.size()-i;
	}

	@Override
	public boolean esCasoBase() {
		return sumaRestante == 0 || i == numeros.size();
	}

	@Override
	public Sp<Elegir> getSolucionParcialCasoBase() {
		Sp<Elegir> result = null;
		if(sumaRestante == 0) {
			result = Sp.create(Elegir.NO, 0.);
		}
		return result;
	}

	@Override
	public Ejercicio1PD getSubProblema(Elegir a) {
		Ejercicio1PD result = null;
		if(a.equals(Elegir.SI)) {
			result = new Ejercicio1PD(i+1, sumaRestante-numeros.get(i)); 
		}else if(a.equals(Elegir.NO)) {
			result = new Ejercicio1PD(i+1, sumaRestante);
		}
		return result;
	}

	@Override
	public Sp<Elegir> getSolucionParcialPorAlternativa(Elegir a, Sp<Elegir> s) {
		Sp<Elegir> result = null;
		if(a.equals(Elegir.SI)) {
			result = Sp.create(a, s.propiedad+1);
		}else if(a.equals(Elegir.NO)) {
			result = Sp.create(a, s.propiedad);
		}
		return result;
	}

	@Override
	public List<Elegir> getAlternativas() {
		List<Elegir> result = new ArrayList<>();
		result.add(Elegir.NO);
		if(sumaRestante-numeros.get(i)>=0) {
			result.add(Elegir.SI);
		}
		return result;
	}

	@Override
	public Tuple2<List<Integer>, List<Integer>> getSolucionReconstruidaCasoBase(Sp<Elegir> sp) {
		List<Integer> lista1 = new ArrayList<>();
		List<Integer> lista2 = IntStream.range(i, numeros.size()).mapToObj(j->numeros.get(j)).collect(Collectors.toList());
		return Tuple.create(lista1, lista2);
	}

	@Override
	public Tuple2<List<Integer>, List<Integer>> getSolucionReconstruidaCasoRecursivo(Sp<Elegir> sp,
			Tuple2<List<Integer>, List<Integer>> s) {
		if(sp.alternativa.equals(Elegir.SI)) {
			s.getV1().add(numeros.get(i));
		}else if(sp.alternativa.equals(Elegir.NO)) {
			s.getV2().add(numeros.get(i));
		}
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((i == null) ? 0 : i.hashCode());
		result = prime * result + ((sumaRestante == null) ? 0 : sumaRestante.hashCode());
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
		Ejercicio1PD other = (Ejercicio1PD) obj;
		if (i == null) {
			if (other.i != null)
				return false;
		} else if (!i.equals(other.i))
			return false;
		if (sumaRestante == null) {
			if (other.sumaRestante != null)
				return false;
		} else if (!sumaRestante.equals(other.sumaRestante))
			return false;
		return true;
	}
	
	

}
