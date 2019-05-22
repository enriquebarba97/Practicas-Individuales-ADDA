package ejercicio1;

import java.util.ArrayList;
import java.util.List;
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
	private Integer sumaRestante1;			// Suma restante hasta el objetivo
	private Integer sumaRestante2;
	
	//Create
	public static Ejercicio1PD create(List<Integer> lista) {
		numeros = new ArrayList<>(lista);
		Integer suma = lista.stream().mapToInt(i->i).sum();
		if(suma%2!=0) {
			throw new IllegalArgumentException("La suma de los elementos de la lista debe ser par. Suma actual: " + suma);
		}else {
			return new Ejercicio1PD(0, suma/2, suma/2);
		}
	}
	
	// Constructor
	public Ejercicio1PD(Integer i, Integer sumaRestante1, Integer sumaRestante2) {
		this.i = i;
		this.sumaRestante1 = sumaRestante1;
		this.sumaRestante2 = sumaRestante2;
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
		return i == numeros.size();
	}

	@Override
	public Sp<Elegir> getSolucionParcialCasoBase() {
		Sp<Elegir> result = null;
		if(sumaRestante1 == 0 && sumaRestante2 == 0) {
			result = Sp.create(null, 0.);
		}
		return result;
	}

	@Override
	public Ejercicio1PD getSubProblema(Elegir a) {
		Ejercicio1PD result = null;
		if(a.equals(Elegir.CONJUNTO1)) {
			result = new Ejercicio1PD(i+1, sumaRestante1-numeros.get(i), sumaRestante2); 
		}else if(a.equals(Elegir.CONJUNTO2)) {
			result = new Ejercicio1PD(i+1, sumaRestante1, sumaRestante2-numeros.get(i));
		}
		return result;
	}

	@Override
	public Sp<Elegir> getSolucionParcialPorAlternativa(Elegir a, Sp<Elegir> s) {
		Sp<Elegir> result = null;
		if(a.equals(Elegir.CONJUNTO1)) {
			result = Sp.create(a, s.propiedad+1);
		}else if(a.equals(Elegir.CONJUNTO2)) {
			result = Sp.create(a, s.propiedad);
		}
		return result;
	}

	@Override
	public List<Elegir> getAlternativas() {
		List<Elegir> result = new ArrayList<>();
		if(sumaRestante1-numeros.get(i)>=0)
			result.add(Elegir.CONJUNTO1);
		if(sumaRestante2-numeros.get(i)>=0)
			result.add(Elegir.CONJUNTO2);
		return result;
	}

	@Override
	public Tuple2<List<Integer>, List<Integer>> getSolucionReconstruidaCasoBase(Sp<Elegir> sp) {
		List<Integer> lista1 = new ArrayList<>();
		List<Integer> lista2 = new ArrayList<>();
		return Tuple.create(lista1, lista2);
	}

	@Override
	public Tuple2<List<Integer>, List<Integer>> getSolucionReconstruidaCasoRecursivo(Sp<Elegir> sp,
			Tuple2<List<Integer>, List<Integer>> s) {
		if(sp.alternativa.equals(Elegir.CONJUNTO1)) {
			s.getV1().add(numeros.get(i));
		}else if(sp.alternativa.equals(Elegir.CONJUNTO2)) {
			s.getV2().add(numeros.get(i));
		}
		return s;
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
		Ejercicio1PD other = (Ejercicio1PD) obj;
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

	

}
