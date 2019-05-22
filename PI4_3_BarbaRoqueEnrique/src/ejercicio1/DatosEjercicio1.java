package ejercicio1;

import java.util.ArrayList;
import java.util.List;

public class DatosEjercicio1 {
	
	public static void create(List<Integer> lista) {
		Integer suma = lista.stream().mapToInt(i->i).sum();
		if(suma%2!=0) {
			throw new IllegalArgumentException("La suma de los elementos de la lista debe ser par. Suma actual: " + suma);
		}
		DatosEjercicio1.lista = new ArrayList<>(lista);
		DatosEjercicio1.sumaObjetivo = suma/2;
	}
	
	
	private static List<Integer> lista;
	private static Integer sumaObjetivo;
	

	public static List<Integer> getLista() {
		return new ArrayList<>(lista);
	}

	public static void setLista(List<Integer> lista) {
		Integer suma = lista.stream().mapToInt(i->i).sum();
		if(suma%2!=0) {
			throw new IllegalArgumentException("La suma de los elementos de la lista debe ser par. Suma actual: " + suma);
		}
		DatosEjercicio1.lista = new ArrayList<>(lista);
		DatosEjercicio1.sumaObjetivo = suma/2;
	}
	
	public static Integer getNumero(int i) {
		return lista.get(i);
	}
	
	public static Integer getSumaObjetivo() {
		return sumaObjetivo;
	}
	
	
}
