package ejercicio4;

public class Camino {
	
	public static Camino create() {
		return new Camino();
	}
	
	public static Camino create(Monumento m1, Monumento m2) {
		return new Camino(m1, m2);
	}
	
	public static Camino create(Monumento m1, Monumento m2, String[] formato) {
		return new Camino(m1, m2, formato);
	}
	
	public static Camino create(Monumento m1, Monumento m2, Double tiempo) {
		return new Camino(m1, m2, tiempo);
	}
	
	private static int num;
	private Monumento source;
	private Monumento target;
	private Double tiempo;
	private int id;
	
	private Camino() {
		this.source = null;
		this.target = null;
		this.tiempo = 0.;
		this.id = num;
		num++;
	}
	
	private Camino(Monumento m1, Monumento m2) {
		this.source = m1;
		this.target = m2;
		this.tiempo = 0.;
		this.id = num;
		num++;
	}
	
	private Camino(Monumento m1, Monumento m2, String[] formato) {
		this.source = m1;
		this.target = m2;
		this.tiempo = Double.parseDouble(formato[2]);
		this.id = num;
		num++;
	}
	
	private Camino(Monumento m1, Monumento m2, Double tiempo) {
		this.source = m1;
		this.target = m2;
		this.tiempo = tiempo;
		this.id = num;
		num++;
	}

	public Monumento getSource() {
		return source;
	}

	public void setSource(Monumento source) {
		this.source = source;
	}

	public Monumento getTarget() {
		return target;
	}

	public void setTarget(Monumento target) {
		this.target = target;
	}

	public Double getTiempo() {
		return tiempo;
	}

	public void setTiempo(Double tiempo) {
		this.tiempo = tiempo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Camino [id=" + id + ", source=" + source + ", target=" + target + ", tiempo=" + tiempo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		result = prime * result + ((tiempo == null) ? 0 : tiempo.hashCode());
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
		Camino other = (Camino) obj;
		if (id != other.id)
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		if (tiempo == null) {
			if (other.tiempo != null)
				return false;
		} else if (!tiempo.equals(other.tiempo))
			return false;
		return true;
	}
	
	
}
