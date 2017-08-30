import progetto.eccezioni.OperazioneNonSupportata;
import progetto.util.*;

public class ClasseTest {
	
	public static void testUno() {
		System.out.println("== Test Uno ==");
		Matrice<Integer> mm1 = new MatriceSparsa<Integer>(2, 3, 0);
		mm1.aggiorna(0, 0, 1);
		mm1.aggiorna(0, 1, 0);
		mm1.aggiorna(0, 2, 2);
		mm1.aggiorna(1, 0, -1);
		mm1.aggiorna(1, 1, 3);
		mm1.aggiorna(1, 2, 1);
		System.out.println("Matrice 1\n" + mm1);
		
		Matrice<Integer> mm2 = new MatriceDensa<Integer>(3, 2, 0);
		mm2.aggiorna(0, 0, 3);
		mm2.aggiorna(0, 1, 1);
		mm2.aggiorna(1, 0, 2);
		mm2.aggiorna(1, 1, 1);
		mm2.aggiorna(2, 0, 1);
		mm2.aggiorna(2, 1, 0);
		System.out.println("Matrice 2\n" + mm2);
		
		System.out.println("Matrice prodotto:");
		System.out.println(mm1.moltiplicazione(mm2));
	}
	
	public static Insieme<Busta<Integer>> somme(int n) {
		Insieme<Busta<Integer>> ins = new Insieme<Busta<Integer>>();
		
		Integer[] vett = new Integer[n];
		for(int i = 1; i <= n; i++) {
			vett[i-1] = i;
		}
		
		for(int temp = n; temp > 0; temp--) {
			if(temp == n) {
				Busta<Integer> bu = new Busta<Integer>();
				bu.agg(temp);
				ins.agg(bu);
			}
			else {
				for(int i = temp; i > 0; i--) {
					for(int j = n; j > 0; j--) {
						
						Busta<Integer> bu = new Busta<Integer>();
						int val = temp;
						bu.agg(temp);
						while(val < n) {
							bu.agg(j);
							val += j;
						}
						if(val == n) {
							ins.agg(bu);
						}
					}
				}
			}
		}
		
		return ins;
	}
	
	public static void testDue() {
		System.out.println("\n== Test Due ==");
		System.out.println(somme(4));
	}
	
	public static void testTre() {
		System.out.println("\n== Test Tre ==");
		Vettore<Integer> vett = new Vettore<Integer>(9);
		for(int i = 0; i < 4; i++) {
			try {
				vett.agg(i);
			} catch (ArrayStoreException e) {
				System.out.println(e);
			} catch (OperazioneNonSupportata e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(vett);
		System.out.println("\nPermutazioni vettore");
		vett.permutazioni(0, vett.dim());
	}
	
	public static void testQuattro() {
		System.out.println("\n== Test Quattro == ");
		NodoBin<Integer> a = new NodoBin<Integer>(1);
		NodoBin<Integer> b = new NodoBin<Integer>(2);
		NodoBin<Integer> c = new NodoBin<Integer>(3);
		NodoBin<Integer> d = new NodoBin<Integer>(4);
		NodoBin<Integer> e = new NodoBin<Integer>(5);
		NodoBin<Integer> f = new NodoBin<Integer>(6);
		a.setLeft(b);
		a.setRight(c);
		b.setLeft(d);
		b.setRight(e);
		c.setLeft(f);
		
		System.out.println("\nVisitaPreOrder:");
		a.visitaPreOrder(a);
		
		System.out.println("\nVisita Per livelli:");
		a.visitaPerLiveli();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testUno();
		
		testDue();
		
		testTre();
		
		testQuattro();
	}

}
