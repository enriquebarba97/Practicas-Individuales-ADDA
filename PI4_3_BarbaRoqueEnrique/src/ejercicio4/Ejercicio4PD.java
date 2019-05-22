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
		cadena = string.replaceAll("\\s", "");
		len = cadena.length();
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
		return Sp.create(-1, 0.);
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
		int i2 = i;
		while(i2<j2) {
			result = Character.toLowerCase(cadena.charAt(i2)) == Character.toLowerCase(cadena.charAt(j2));
			i2++;
			j2--;
			if(!result) {
				break;
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((i == null) ? 0 : i.hashCode());
		result = prime * result + ((j == null) ? 0 : j.hashCode());
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
		Ejercicio4PD other = (Ejercicio4PD) obj;
		if (i == null) {
			if (other.i != null)
				return false;
		} else if (!i.equals(other.i))
			return false;
		if (j == null) {
			if (other.j != null)
				return false;
		} else if (!j.equals(other.j))
			return false;
		return true;
	}
	
	
}
