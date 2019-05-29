package ejercicio1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import us.lsi.bt.EstadoBT;
import us.lsi.common.Tuple;
import us.lsi.common.Tuple2;

public class Ejercicio1BT2 implements EstadoBT<Tuple2<List<Integer>, List<Integer>>, Elegir, Ejercicio1BT2> {
	
	public static Ejercicio1BT2 create() {
		return new Ejercicio1BT2();
	}
	
	private List<Integer> lista;
	private List<Elegir> listaAlt;
	private Integer i;
	private Integer sumaRestante1, sumaRestante2;
	
	Ejercicio1BT2() {
		this.lista = DatosEjercicio1.getLista();
		this.listaAlt = new ArrayList<>();
		this.i=0;
		Integer objetivo = DatosEjercicio1.getSumaObjetivo();
		this.sumaRestante1 = objetivo;
		this.sumaRestante2 = objetivo;
	}
	
	
	@Override
	public Tipo getTipo() {
		return Tipo.Min;
	}

	@Override
	public Ejercicio1BT2 getEstadoInicial() {
		return Ejercicio1BT2.create();
	}

	@Override
	public Ejercicio1BT2 avanza(Elegir a) {
		listaAlt.add(a);
		if(a.equals(Elegir.CONJUNTO1)) {
			sumaRestante1 -= lista.get(i);
			i++;
		}else if(a.equals(Elegir.CONJUNTO2)) {
			sumaRestante2 -= lista.get(i);
			i++;
		}
		return this;
	}

	@Override
	public Ejercicio1BT2 retrocede(Elegir a) {
		if(a.equals(Elegir.CONJUNTO1)) {
			i--;
			sumaRestante1 += lista.get(i);
		}else if(a.equals(Elegir.CONJUNTO2)) {
			i--;
			sumaRestante2 += lista.get(i);
		}
		listaAlt.remove((int) i);
		return this;
	}

	@Override
	public int size() {
		return lista.size()-i;
	}

	@Override
	public boolean esCasoBase() {
		return lista.size()==i;
	}

	@Override
	public List<Elegir> getAlternativas() {
		List<Elegir> result = new ArrayList<>();
		if(sumaRestante1-lista.get(i)>=0)
			result.add(Elegir.CONJUNTO1);
		if(sumaRestante2-lista.get(i)>=0)
			result.add(Elegir.CONJUNTO2);
		return result;
	}

	@Override
	public Tuple2<List<Integer>, List<Integer>> getSolucion() {
		Tuple2<List<Integer>, List<Integer>> result = null;
		List<Integer> sel1 = new ArrayList<>();
		List<Integer> sel2 = new ArrayList<>();
		if(sumaRestante1==0 && sumaRestante2==0) {
			IntStream.range(0, i).forEach(i->{
				if(listaAlt.get(i).equals(Elegir.CONJUNTO1))
					sel1.add(lista.get(i));
				else
					sel2.add(lista.get(i));
			});
			result = Tuple.create(new ArrayList<>(sel1), new ArrayList<>(sel2));
		}
		return result;
	}
	
	@Override
	public Double getObjetivo() {
		return (double) listaAlt.stream().filter(a->a.equals(Elegir.CONJUNTO1)).count();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((i == null) ? 0 : i.hashCode());
		result = prime * result + ((lista == null) ? 0 : lista.hashCode());
		result = prime * result + ((listaAlt == null) ? 0 : listaAlt.hashCode());
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
		Ejercicio1BT2 other = (Ejercicio1BT2) obj;
		if (i == null) {
			if (other.i != null)
				return false;
		} else if (!i.equals(other.i))
			return false;
		if (lista == null) {
			if (other.lista != null)
				return false;
		} else if (!lista.equals(other.lista))
			return false;
		if (listaAlt == null) {
			if (other.listaAlt != null)
				return false;
		} else if (!listaAlt.equals(other.listaAlt))
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