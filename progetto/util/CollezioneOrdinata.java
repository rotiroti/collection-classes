package progetto.util;

import java.util.Iterator;

import progetto.eccezioni.*;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public class CollezioneOrdinata<T> extends ListaArray<T> {

	/**
	 * Costruttore base
	 * <p>
	 * @see progetto.util.ListaArray#ListaArray()
	 */
	public CollezioneOrdinata() {
		super();
	}

	/**
	 * @param dim
	 * @throws IllegalArgumentException
	 */
	public CollezioneOrdinata(int dim) throws IllegalArgumentException {
		super(dim);
	}
	
	/**
	 * Aggiunge un elemento in testa alla lista
	 * <p>
	 * @param t elemento da aggiungere in testa
	 */
	public void aggPrimo(T t) {
		try {
			agg(0, t);
		} catch (OperazioneNonSupportata ex) {
			System.out.println(ex);
		}
	}

	/**
	 * Aggiunge un elemento in coda alla lista
	 * <p>
	 * @param t elemento da inserire
	 */
	public void aggUltimo(T t) {
		try {
			agg(dim, t);
		} catch (OperazioneNonSupportata ex) {
			System.out.println(ex);
		}
	}
	
	/**
	 * Ritorna l'elemento in testa alla lista, rimuovendolo
	 * <p>
	 * @return primo elemento della lista
	 */
	public T rimPrimo() {
		try {
			return rimuovi(0);
		} catch (OperazioneNonSupportata ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	/**
	 * Ritorna l'elemento in coda alla lista
	 * <p>
	 * @return	elemento in coda alla lista
	 */
	public T rimUltimo() {
		try {
			return rimuovi(dim-1);
		} catch (OperazioneNonSupportata ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	/**
	 * Aggiunge l'elemento t2 prima dell'elemento t1
	 * <p> 
	 * @param t1	elemento che deve seguire t2
	 * @param t2	elemento da aggiungere
	 */
	public void aggPrima(T t1, T t2) {
		int t1Index = -1;
		try {
			t1Index = indiceDi(t1);
		} catch (OperazioneNonSupportata ex) {
			System.out.println(ex);
		}
		
		if(t1Index == -1) {
			try {
				agg(dim, t2);
			} catch (OperazioneNonSupportata ex) {
				System.out.println(ex);
			}
		} else {
			try {
				agg(t1Index, t2);
			} catch (OperazioneNonSupportata ex) {
				System.out.println(ex);
			}
		}
	}
	
	/**
	 * Aggiunge l'elemento t2 dopo l'elemento t1
	 * <p>
	 * @param t1	elemento che deve precedere t2
	 * @param t2	elemento da inserire
	 */
	public void aggDopo(T t1, T t2) {
		try {
			int t1Index = indiceDi(t1);
			if(t1Index == -1)
				agg(dim ,t2);
			else
				agg(t1Index+1, t2);
		} catch (OperazioneNonSupportata ex) {
			System.out.println(ex);
		}
	}
	
	public boolean lessOrEqual(CollezioneOrdinata<T> l) {
		if(dim < l.dim())
			return true;
		else if(dim > l.dim())
			return false;
		else {
			Iterator<T> iter = iteratore();
			Iterator<T> liter = l.iteratore();
			
			while(iter.hasNext() && liter.hasNext()) {
				int val = iter.next().toString().compareTo(liter.next().toString());
				if(val > 0)
					return false;
			}
			return true;
		}
	}
}
