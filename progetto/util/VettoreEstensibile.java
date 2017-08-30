package progetto.util;

import java.util.Iterator;

import progetto.eccezioni.OperazioneNonSupportata;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public class VettoreEstensibile<T> extends ListaArray<T> {
	
	private int maxSpazio;

	/**
	 * @param dim
	 * @throws IllegalArgumentException
	 */
	public VettoreEstensibile(int dim) throws IllegalArgumentException {
		super(dim);
		maxSpazio = dim;
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
	
	public <S> VettoreEstensibile<S> fai(Blocco<T, S> b) {
		Iterator<T> it = iteratore();
		VettoreEstensibile<S> coll = new VettoreEstensibile<S>(maxSpazio);
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
	
	public VettoreEstensibile<T> seleziona(BloccoBool<T> b) {
		Iterator<T> it = iteratore();
		VettoreEstensibile<T> new_class = new VettoreEstensibile(maxSpazio);
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
	
	public VettoreEstensibile<T> rifiuta(BloccoBool<T> b) {
		Iterator<T> it = iteratore();
		VettoreEstensibile<T> new_class = new VettoreEstensibile<T>(maxSpazio);
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
	
	public boolean lessOrEqual(VettoreEstensibile<T> l) {
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
