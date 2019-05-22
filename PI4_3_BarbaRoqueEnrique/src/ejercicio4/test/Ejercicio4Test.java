package ejercicio4.test;


import ejercicio4.Ejercicio4PD;
import us.lsi.pd.AlgoritmoPD;

public class Ejercicio4Test {
	
	public static void main(String[] args) {
		System.out.println("================================================= Ejercicio1 PD =================================================");
		Ejercicio4PD p = Ejercicio4PD.create("ababbbabbababa");
		var alg = AlgoritmoPD.createPD(p);
		alg.ejecuta();
		System.out.println(alg.getSolucion());
	}
	
}
