package ejercicio4;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.pd.AlgoritmoPD.Sp;
import us.lsi.pd.AlgoritmoPD.Tipo;
import us.lsi.pd.ProblemaPD;

public class Ejercicio4PD implements ProblemaPD<String, Integer, Ejercicio4PD> {
	
	private static String cadena;
	private static Integer len;
	private Integer i, j;
	
	public static Ejercicio4PD create(String string) {
		cadena = string;
		len = string.length();
		return new Ejercicio4PD(0, len);
	}
	
	public Ejercicio4PD(Integer i, Integer j) {
		this.i = i;
		this.j = j;
	}
	
	@Override
	public Tipo getTipo() {
		return Tipo.Min;
	}

	@Override
	public int size() {
		return j-i;
	}

	@Override
	public boolean esCasoBase() {
		return j-i<2 || isPalindrome();
	}

	@Override
	public Sp<Integer> getSolucionParcialCasoBase() {
		return Sp.create(null, 0.);
	}

	@Override
	public Ejercicio4PD getSubProblema(Integer a, int np) {
		Ejercicio4PD result = null;
		if(np==0) {
			result = new Ejercicio4PD(i, a);
		}else {
			result = new Ejercicio4PD(a, j);
		}
		return result;
	}

	@Override
	public Sp<Integer> getSolucionParcialPorAlternativa(Integer a, List<Sp<Integer>> ls) {
		Sp<Integer> result = null;
		Double cortes = ls.stream().mapToDouble(sp->sp.propiedad).sum();
		result = Sp.create(a, cortes+1);
		return result;
	}

	@Override
	public List<Integer> getAlternativas() {
		return IntStream.range(i+1, j).boxed().collect(Collectors.toList());
	}

	@Override
	public int getNumeroSubProblemas(Integer a) {
		return 2;
	}

	@Override
	public String getSolucionReconstruidaCasoBase(Sp<Integer> sp) {
		String result = cadena.substring(i,j);
		return result;
	}

	@Override
	public String getSolucionReconstruidaCasoRecursivo(Sp<Integer> sp, List<String> ls) {
		return ls.get(0) + "|" + ls.get(1);
	}
	
	
	private boolean isPalindrome() {
		boolean result = true;
		int j2 = j-1;
		int medio = (i+j2)/2;
		for(int i2=i; i2<medio; i++) {
			result = cadena.charAt(i2) == cadena.charAt(j2);
			if(!result) {
				break;
			}
		}
		return result;
	}
	
	
}
