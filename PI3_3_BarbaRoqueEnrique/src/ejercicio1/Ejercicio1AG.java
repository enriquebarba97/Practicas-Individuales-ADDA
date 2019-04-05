package ejercicio1;

import java.util.List;

import us.lsi.ag.ValuesInRangeChromosome;
import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class Ejercicio1AG {

	public static void main(String[] args) {
		setConstantes();
		
		ValuesInRangeProblemAG<Integer, List<Integer>> problema = new BomberosProblemAG("ficheros/ejercicio1.txt");
		AlgoritmoAG<ValuesInRangeChromosome<Integer>> al= new AlgoritmoAG<>(ChromosomeType.Binary, problema);
		al.ejecuta();
		
		System.out.println("Mejor cromosoma: " + al.getBestChromosome().decode());
		System.out.println("Solución asociada al cromosoma: " + problema.getSolucion(al.getBestChromosome()));
	}
	private static void setConstantes() {
		// Condiciones de evolucion
		AlgoritmoAG.ELITISM_RATE  = 0.30;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.7;
		AlgoritmoAG.POPULATION_SIZE = 100;
		
		//Condiciones de parada
		StoppingConditionFactory.NUM_GENERATIONS = 10000;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 623.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.SolutionsNumber;
		
	}
}
