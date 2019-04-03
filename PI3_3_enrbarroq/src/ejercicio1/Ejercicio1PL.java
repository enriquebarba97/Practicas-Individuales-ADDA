package ejercicio1;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Streams2;
import us.lsi.lpsolve.solution.AlgoritmoPLI;
import us.lsi.lpsolve.solution.SolutionPLI;

public class Ejercicio1PL {

	public static void main(String[] args) {
		String constraints = getConstraints();
		System.out.println("La cadena con formato LPSolve generada es:");
		System.err.println("-------------------");
		System.out.println(constraints);
		SolutionPLI a = AlgoritmoPLI.getSolution(constraints);
		System.out.println("-------------------");	
		System.out.println("________");
		System.out.println("Numero de estaciones (funcion  objetivo): " + a.getGoal());
		System.out.println("Barrios con estacion:");
		for (int j = 0; j < a.getNumVar(); j++) {
			if(a.getSolution()[j]!=0)
				System.out.println(a.getName(j)+" = "+a.getSolution()[j]);
		}
		System.out.println("________");
		
	}
	
	private static String getConstraints(){
		// Carga de los barrios desde el fichero de texto
		List<Barrio> barrios = Streams2.fromFile("ficheros/ejercicio1.txt")
				.map(s -> Barrio.create(s))
				.collect(Collectors.toList());
		
		String result = "";
		
		// Construccion de la funcion objetivo
		result += "min:";
		for(int i = 0; i<barrios.size(); i++) {
			Barrio b = barrios.get(i);
			if(i!=0) result += "+";
			result += "b" + b.getId();
		}
		result += ";\n\n";
		
		// Construccion de las restricciones para cubrir a todos los barrios
		for(int i = 0; i<barrios.size(); i++) {
			List<Integer> b = barrios.get(i).getVecinos();
			for(int j = 0; j<b.size();j++) {
				if(j!=0) result += "+";
				result += "b" + b.get(j);
			}
			result += ">=1;\n";
		}
		result += "\n";
		
		// Restricciones binarias
		result += "bin ";
		for(int i = 0; i<barrios.size(); i++) {
			if(i != 0) result += ",";
			result += "b" + barrios.get(i).getId();
		}
		result += ";";
		return result;
	}
			
}
