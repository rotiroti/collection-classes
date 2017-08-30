package progetto.util;

import java.util.Iterator;

import progetto.eccezioni.*;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public class Vettore<T> extends ListaArray<T> {
	
	protected int maxSpazio = 0;

	/**
	 * 
	 */
	private Vettore() {
		throw new IllegalArgumentException();
	}

	/**
	 * @param dim
	 * @throws IllegalArgumentException
	 */
	public Vettore(int dim) throws IllegalArgumentException {
		super(dim);
		maxSpazio = dim;
	}
	
	public void agg(T t) throws OperazioneNonSupportata {
		if(dim >= maxSpazio)
			throw new OperazioneNonSupportata();
		
		super.agg(t);
	}
	
	public void aggTutta(Collezione<? extends T> C) throws OperazioneNonSupportata {
		if((C.dim() + dim) > maxSpazio)
			throw new OperazioneNonSupportata();
		
		super.aggTutta(C);
	}
	
	T at(int n) throws ArrayIndexOutOfBoundsException {
		if(n >= dim)
			throw new ArrayIndexOutOfBoundsException();
		
		return get(n);
	}

	public void aggiorna(int n, T t) throws ArrayIndexOutOfBoundsException {
		if(n >= dim)
			throw new ArrayIndexOutOfBoundsException();
		
		super.aggiorna(n, t);
	}
	
	public <S> Vettore<S> fai(Blocco<T, S> b) {
		Iterator<T> it = iteratore();
		Vettore<S> coll = new Vettore<S>(maxSpazio);
		T elem;
		
		while(it.hasNext()) {
			elem = it.next();
			if(elem != null) {
				try {
					coll.agg(b.valuta(elem));
				} catch (OperazioneNonSupportata e) {
					e.printStackTrace();
				}
			}
		}
		return coll;
	}
	
	public Vettore<T> seleziona(BloccoBool<T> b) {
		Iterator<T> it = iteratore();
		Vettore<T> new_class = new Vettore(maxSpazio);
		T elem;
		
		while(it.hasNext()) {
			elem = it.next();
			if(elem != null && b.valuta(elem))
				try {
					new_class.agg(elem);
				} catch (OperazioneNonSupportata e) {
					e.printStackTrace();
				}
		}
		return new_class;
	}
	
	public Vettore<T> rifiuta(BloccoBool<T> b) {
		Iterator<T> it = iteratore();
		Vettore<T> new_class = new Vettore<T>(maxSpazio);
		T elem;

		while(it.hasNext()) {
			elem = it.next();
			if(elem != null && !(b.valuta(elem)))
				try {
					new_class.agg(elem);
				} catch (OperazioneNonSupportata e) {
					e.printStackTrace();
				}
		}
		return new_class;
	}

	private Number molt(Number a, Number b) {
		if(a.getClass().getSimpleName().equals("Integer"))
			return a.intValue() * b.intValue();
		else if(a.getClass().getSimpleName().equals("Float"))
			return a.floatValue() * b.floatValue();
		else if(a.getClass().getSimpleName().equals("Long"))
			return a.longValue() * b.longValue();
		else
			return a.doubleValue() * b.doubleValue();
	}

	private Number sum(Number a, Number b) {
		if(a.getClass().getSimpleName().equals("Integer"))
			return a.intValue() + b.intValue();
		else if(a.getClass().getSimpleName().equals("Float"))
			return a.floatValue() + b.floatValue();
		else if(a.getClass().getSimpleName().equals("Long"))
			return a.longValue() + b.longValue();
		else
			return a.doubleValue() + b.doubleValue();
	}

	public Number moltiplicazione(Vettore<? extends Number> v) {
		Iterator<? extends Number> iter = (Iterator<? extends Number>) iteratore();
		Iterator<? extends Number> viter = (Iterator<? extends Number>) v.iteratore();
		Number val = 0; 
		
		while(iter.hasNext() && viter.hasNext()) {
			Number el = iter.next();
			Number el2 = viter.next();
			val = sum(val, molt(el, el2));
		}
		
		return val;
	}
	
	public boolean lessOrEqual(Vettore<T> l) {
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
	
	public void permutazioni(int start, int n) {
		if(start == n-1)
			System.out.println(this);
		else {
			for(int i = start; i < n; i++) {
				T tmp = at(i);
				aggiorna(i, at(start));
				aggiorna(start, tmp);
				permutazioni(start+1, n);
				aggiorna(start, at(i));
				aggiorna(i, tmp);
			}
		}
		
	}
}
