package ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.ag.ValuesInRangeChromosome;
import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.common.Streams2;

public class BomberosProblemAG implements ValuesInRangeProblemAG<Integer, List<Integer>> {
	
	private List<List<Integer>> barrios;
	
	public BomberosProblemAG(String fichero) {
		this.barrios = cargaBarrios(fichero);
	}
	
	private List<List<Integer>> cargaBarrios(String fichero) {
		return Streams2.fromFile("ficheros/ejercicio1.txt")
				.map(s -> create(s))
				.collect(Collectors.toList());
	}
	
	private static List<Integer> create(String s){
		List<Integer> result = new ArrayList<>();
		String[] vals = s.split("[,]");
		for(int i = 0; i<vals.length; i++) {
			result.add(Integer.parseInt(vals[i].trim()));
		}
		return result;
	}
	
	@Override
	public Integer getVariableNumber() {
		return barrios.size();
	}

	@Override
	public Integer getMax(Integer i) {
		return 1;
	}

	@Override
	public Integer getMin(Integer i) {
		return 0;
	}

	@Override
	public Double fitnessFunction(ValuesInRangeChromosome<Integer> cr) {
		List<Integer> s = getSolucion(cr);
		// Funcion objetivo
		Integer fo = s.size();
		// Restriccion
		long restriccion = barrios.stream().mapToLong(b -> check(b,s)).filter(i->i<0).sum();
		return (double) -fo -getVariableNumber()*restriccion*restriccion;
	}

	private long check(List<Integer> b, List<Integer> s) {
		return b.stream().filter(i -> s.contains(i)).count()-1;
	}

	@Override
	public List<Integer> getSolucion(ValuesInRangeChromosome<Integer> cr) {
		List<Integer> dec = cr.decode();
		List<Integer> result = new ArrayList<>();
		IntStream.range(0, dec.size()).forEach(i->{if(dec.get(i)==1) result.add(i);});
		return result;
	}

}
