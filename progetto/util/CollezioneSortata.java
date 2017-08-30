package progetto.util;

import java.util.Iterator;

import progetto.eccezioni.OperazioneNonSupportata;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public class CollezioneSortata<T> extends CollezioneOrdinata<T> {
	
	private BloccoOrd sortingFunc;

	/**
	 * Costruttore base
	 */
	public CollezioneSortata(BloccoOrd ord) {
		super();
		sortingFunc = ord;
	}

	/**
	 * Costruttore con dimensione
	 * <p>
	 * @param dim
	 * @throws IllegalArgumentException
	 */
	public CollezioneSortata(int dim, BloccoOrd ord)
	  throws IllegalArgumentException {
		super(dim);
		sortingFunc = ord;
	}
	
	public void cambiaOrdine(BloccoOrd ord) {
		sortingFunc = ord;
		sort(sortingFunc);
	}

	public void agg(T t) {
		try {
			super.agg(t);
		} catch (OperazioneNonSupportata e) {
			e.printStackTrace();
		}
		insertionSort(sortingFunc);
	}
	
	public void agg(int index, T t) {
		try {
			super.agg(index, t);
		} catch (OperazioneNonSupportata e) {
			e.printStackTrace();
		}
		insertionSort(sortingFunc);
	}
	
	public T rimuovi(int index) {
		T el = null;
		try {
			el = super.rimuovi(index);
		} catch (OperazioneNonSupportata e) {
			e.printStackTrace();
		}
		insertionSort(sortingFunc);
		return el;
	}
	
	public void rimuovi(Object c) {
		super.rimuovi(c);
		insertionSort(sortingFunc);
	}
	
	public void aggTutta(Collezione<? extends T> C) {
		try {
			super.aggTutta(C);
		} catch (OperazioneNonSupportata e) {
			e.printStackTrace();
		}
		insertionSort(sortingFunc);
	}

	public void aggPrimo(T t) {
		super.aggPrimo(t);
		insertionSort(sortingFunc);
	}
	
	public void aggUltimo(T t) {
		super.aggUltimo(t);
		insertionSort(sortingFunc);
	}
	
	public void aggDopo(T t1, T t2) {
		super.aggDopo(t1, t2);
		insertionSort(sortingFunc);
	}
	
	public void aggPrima(T t1, T t2) {
		super.aggPrima(t1, t2);
		insertionSort(sortingFunc);
	}
	
	public <S> CollezioneSortata<S> fai(Blocco<T, S> b) {
		Iterator<T> it = iteratore();
		CollezioneSortata<S> coll = new CollezioneSortata<S>(sortingFunc);
		T elem;
		
		while(it.hasNext()) {
			elem = it.next();
			if(elem != null) {
				coll.agg(b.valuta(elem));
			}
		}
		return coll;
	}
	
	public CollezioneSortata<T> seleziona(BloccoBool<T> b) {
		Iterator<T> it = iteratore();
		CollezioneSortata<T> new_class = new CollezioneSortata<T>(sortingFunc);
		T elem;
		
		while(it.hasNext()) {
			elem = it.next();
			if(elem != null && b.valuta(elem))
				new_class.agg(elem);
		}
		return new_class;
	}
	
	public CollezioneSortata<T> rifiuta(BloccoBool<T> b) {
		Iterator<T> it = iteratore();
		CollezioneSortata<T> new_class = new CollezioneSortata<T>(sortingFunc);
		T elem;
		
		while(it.hasNext()) {
			elem = it.next();
			if(elem != null && !(b.valuta(elem)))
				new_class.agg(elem);
		}
		return new_class;
	}
	
	public boolean lessOrEqual(CollezioneSortata<T> l) {
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
