package ejercicio1;

import java.util.ArrayList;
import java.util.List;

public class DatosEjercicio1 {
	
	public static DatosEjercicio1 create(List<Integer> lista) {
		Integer suma = lista.stream().mapToInt(i->i).sum();
		if(suma%2!=0) {
			throw new IllegalArgumentException("La suma de los elementos de la lista debe ser par. Suma actual: " + suma);
		}
		return new DatosEjercicio1(lista);
	}
	
	private static List<Integer> lista;
	
	DatosEjercicio1(List<Integer> lista){
		DatosEjercicio1.lista = new ArrayList<>(lista);
	}

	public static List<Integer> getLista() {
		return lista;
	}

	public static void setLista(List<Integer> lista) {
		Integer suma = lista.stream().mapToInt(i->i).sum();
		if(suma%2!=0) {
			throw new IllegalArgumentException("La suma de los elementos de la lista debe ser par. Suma actual: " + suma);
		}
		DatosEjercicio1.lista = new ArrayList<>(lista);
	}
	
	
}
