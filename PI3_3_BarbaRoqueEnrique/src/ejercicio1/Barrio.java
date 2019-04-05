package ejercicio1;

import java.util.ArrayList;
import java.util.List;

public class Barrio {
	
	public static Barrio create(String s) {
		return new Barrio(s);
	}
	
	private static Integer nid = 0;
	
	private Integer id;
	private List<Integer> vecinos;
	
	Barrio(String s) {
		String[] vals = s.split("[,]");
		this.id = nid;
		nid++;
		List<Integer> vec = new ArrayList<>();
		for(int i = 0; i<vals.length; i++) {
			vec.add(Integer.parseInt(vals[i]));
		}
		this.vecinos = vec;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Integer> getVecinos() {
		return vecinos;
	}

	public void setVecinos(List<Integer> vecinos) {
		this.vecinos = vecinos;
	}

	
	public String toString() {
		return "Barrio [id=" + id + ", vecinos=" + vecinos + "]";
	}
	
	
	
	
}
