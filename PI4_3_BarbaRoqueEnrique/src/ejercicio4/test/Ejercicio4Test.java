package ejercicio4.test;


import ejercicio4.Ejercicio4PD;
import us.lsi.pd.AlgoritmoPD;

public class Ejercicio4Test {
	
	public static void main(String[] args) {
		System.out.println("================================================= Ejercicio4 PD =================================================");
		Ejercicio4PD p = Ejercicio4PD.create("ababbbabbababa");
		System.out.println("Cadena original: ababbbabbababa");
		var alg = AlgoritmoPD.createPD(p);
		alg.ejecuta();
		System.out.println("Cadena dividida: " + alg.getSolucion());
	}
	
	
}
