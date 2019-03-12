package ejercicios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import clases.Barrio;
import us.lsi.common.Streams2;
import us.lsi.lpsolve.solution.AlgoritmoPLI;
import us.lsi.lpsolve.solution.SolutionPLI;

public class Ejercicio1 {

	public static void main(String[] args) {
		String constraints = getConstraints2();
		System.out.println(constraints);
		SolutionPLI a = AlgoritmoPLI.getSolution(constraints);
		System.out.println("-------------------");	
		System.out.println("________");
		System.out.println(a.getGoal());
		for (int j = 0; j < a.getNumVar(); j++) {
			System.out.println(a.getName(j)+" = "+a.getSolution()[j]);
		}
		System.out.println("________");
		System.out.println(a.getGoal());
		
	}
	
	private static String getConstraints(){
		List<Barrio> barrios = Streams2.fromFile("ficheros/ejercicio1.txt")
				.map(s -> Barrio.create(s))
				.collect(Collectors.toList());
		String result = "";
		result += "min:";
		for(int i = 0; i<barrios.size(); i++) {
			Barrio b = barrios.get(i);
			if(i!=0) result += "+";
			result += "b" + b.getId();
		}
		result += ";\n\n";
		for(int i = 0; i<barrios.size(); i++) {
			List<Integer> b = barrios.get(i).getVecinos();
			for(int j = 0; j<b.size();j++) {
				if(j!=0) result += "+";
				result += "b" + b.get(j);
			}
			result += ">=1;\n";
		}
		result += "\n";
		
		result += "bin ";
		for(int i = 0; i<barrios.size(); i++) {
			if(i != 0) result += ",";
			result += "b" + barrios.get(i).getId();
		}
		result += ";";
		return result;
	}
	
	private static String getConstraints2(){
		List<List<Integer>> barrios = Streams2.fromFile("ficheros/ejercicio1.txt")
				.map(s -> create(s))
				.collect(Collectors.toList());
		String result = "";
		result += "min:";
		for(int i = 0; i<barrios.size(); i++) {
			List<Integer> b = barrios.get(i);
			if(i!=0) result += "+";
			result += "b" + i;
		}
		result += ";\n\n";
		for(int i = 0; i<barrios.size(); i++) {
			List<Integer> b = barrios.get(i);
			for(int j = 0; j<b.size();j++) {
				if(j!=0) result += "+";
				result += "b" + b.get(j);
			}
			result += ">=1;\n";
		}
		result += "\n";
		
		result += "bin ";
		for(int i = 0; i<barrios.size(); i++) {
			if(i != 0) result += ",";
			result += "b" + i;
		}
		result += ";";
		return result;
	}
	
	private static List<Integer> create(String s){
		List<Integer> result = new ArrayList<>();
		String[] vals = s.split("[,]");
		for(int i = 0; i<vals.length; i++) {
			result.add(Integer.parseInt(vals[i].trim()));
		}
		return result;
	}
	
}
