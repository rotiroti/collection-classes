package progetto.util;

import java.util.Iterator;

import progetto.eccezioni.OperazioneNonSupportata;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public abstract class Matrice<T> extends Vettore<T> {
	
	protected Matrice() {
		super(10);
	}
		
	abstract public int dimC();
	
	abstract public int dimR();
	
	abstract public T at(int n, int m);
	
	abstract public void aggiorna(int n, int m, T t);
	
	abstract public Vettore<T> riga(int n);
	
	abstract public Vettore<T> col(int n);
	
	abstract public Vettore<T> atDA(int n);
	
	abstract public Vettore<T> atDD(int n);
	
	abstract public Matrice<T> moltiplicazione(Matrice<T> m);
	
	public Vettore<T> comeVettore() {
		Iterator<T> iter = iteratore();
		Vettore<T> vec = new Vettore<T>(dimR()*dimC());
		
		while(iter.hasNext()) {
			try {
				vec.agg(iter.next());
			} catch (OperazioneNonSupportata e) {
				e.printStackTrace();
			}
		}
		return vec;
	}
	
	public VettoreEstensibile<T> comeVettoreEstensibile() {
		Iterator<T> iter = iteratore();
		VettoreEstensibile<T> vec = new VettoreEstensibile<T>(dimR()*dimC());
		
		while(iter.hasNext()) {
			try {
				vec.agg(iter.next());
			} catch (OperazioneNonSupportata e) {
				e.printStackTrace();
			}
		}
		return vec;
	}
	
	public Insieme<T> comeInsieme() {
		Iterator<T> iter = iteratore();
		Insieme<T> ins = new Insieme<T>();
		
		while(iter.hasNext()) {
			ins.agg(iter.next());
		}
		
		return ins;
	}
	
	public InsiemeOggetti<T> comeInsiemeOggetti() {
		Iterator<T> iter = iteratore();
		InsiemeOggetti<T> ins = new InsiemeOggetti<T>();
		
		while(iter.hasNext()) {
			ins.agg(iter.next());
		}
		
		return ins;
	}
	
	public Busta<T> comeBusta() {
		Iterator<T> iter = iteratore();
		Busta<T> bu = new Busta();
		
		while(iter.hasNext()) {
			bu.agg(iter.next());
		}
		
		return bu;
	}
	
	public CollezioneOrdinata<T> comeCollezioneOrdinata() {
		Iterator<T> iter = iteratore();
		CollezioneOrdinata<T> collOrd = new CollezioneOrdinata<T>();
		
		while(iter.hasNext()) {
			try {
				collOrd.agg(iter.next());
			} catch (OperazioneNonSupportata e) {
				e.printStackTrace();
			}
		}
		
		return collOrd;
	}
	
	public CollezioneSortata<T> comeCollezioneSortata(BloccoOrd<T> b) {
		Iterator<T> iter = iteratore();
		CollezioneSortata<T> collOrd = new CollezioneSortata<T>(b);
		
		while(iter.hasNext()) {
			collOrd.agg(iter.next());
		}
		
		return collOrd;
	}
	
	public boolean lessOrEqual(Matrice<T> m) {
		if((dimR() < m.dimR()) || (dimC() < m.dimC()))
			return true;
		else if((dimR() == m.dimR()) && (dimC() == m.dimC())) {
			Iterator<T> iter = iteratore();
			Iterator<T> miter = m.iteratore();
			
			while(iter.hasNext() && miter.hasNext()) {
				int val = iter.next().toString().compareTo(miter.next().toString());
				if(val > 0)
					return false;
			}
			return true;
		}
		else
			return false;
	}
}
