package ejercicio1;

import java.util.ArrayList;
import java.util.List;

import us.lsi.bt.EstadoBT;
import us.lsi.common.Tuple;
import us.lsi.common.Tuple2;

public class Ejercicio1BT implements EstadoBT<Tuple2<List<Integer>, List<Integer>>, Elegir, Ejercicio1BT> {
	
	public static Ejercicio1BT create() {
		return new Ejercicio1BT();
	}
	
	private List<Integer> lista, sel1, sel2;
	private Integer i;
	private Integer sumaRestante;
	
	Ejercicio1BT() {
		this.lista = DatosEjercicio1.getLista();
		this.sel1 = new ArrayList<>();
		this.sel2 = new ArrayList<>();
		this.i=0;
		this.sumaRestante = lista.stream().mapToInt(i->i).sum()/2;
	}
	
	
	@Override
	public Tipo getTipo() {
		return Tipo.Min;
	}

	@Override
	public Ejercicio1BT getEstadoInicial() {
		return Ejercicio1BT.create();
	}

	@Override
	public Ejercicio1BT avanza(Elegir a) {
		if(a.equals(Elegir.SI)) {
			sel1.add(lista.get(i));
			sumaRestante -= lista.get(i);
			i++;
		}else if(a.equals(Elegir.NO)) {
			sel2.add(lista.get(i));
			i++;
		}
		return this;
	}

	@Override
	public Ejercicio1BT retrocede(Elegir a) {
		if(a.equals(Elegir.SI)) {
			i--;
			sel1.remove(lista.get(i));
			sumaRestante += lista.get(i);
			
		}else if(a.equals(Elegir.NO)) {
			i--;
			sel2.remove(lista.get(i));
			
		}
		return this;
	}

	@Override
	public int size() {
		return lista.size()-i;
	}

	@Override
	public boolean esCasoBase() {
		return lista.size()==i || sumaRestante==0;
	}

	@Override
	public List<Elegir> getAlternativas() {
		List<Elegir> result = new ArrayList<>();
		result.add(Elegir.NO);
		if(sumaRestante-lista.get(i)>=0) {
			result.add(Elegir.SI);
		}
		return result;
	}

	@Override
	public Tuple2<List<Integer>, List<Integer>> getSolucion() {
		Tuple2<List<Integer>, List<Integer>> result = null;
		if(sumaRestante==0) {
			result = Tuple.create(new ArrayList<>(sel1), new ArrayList<>(sel2));
		}
		return result;
	}
	
	@Override
	public Double getObjetivo() {
		return (double) sel1.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((i == null) ? 0 : i.hashCode());
		result = prime * result + ((lista == null) ? 0 : lista.hashCode());
		result = prime * result + ((sel1 == null) ? 0 : sel1.hashCode());
		result = prime * result + ((sel2 == null) ? 0 : sel2.hashCode());
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
		Ejercicio1BT other = (Ejercicio1BT) obj;
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
		if (sel1 == null) {
			if (other.sel1 != null)
				return false;
		} else if (!sel1.equals(other.sel1))
			return false;
		if (sel2 == null) {
			if (other.sel2 != null)
				return false;
		} else if (!sel2.equals(other.sel2))
			return false;
		return true;
	}
	
	
}
