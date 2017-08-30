package progetto.util;

import java.util.Iterator;

import progetto.eccezioni.*;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public class ListaArray<T> extends ListaAstratta<T> implements Lista<T> {
	
	/**
	 * Array in cui salvare gli elementi della lista
	 */
	private Object[] elementi;
	
	/**
	 * Spazio disponibile nell'array elementi
	 */
	private int spazioDisponibile;
	
	/**
	 * Costruttore senza parametri, crea un array con 10 elementi
	 */
	public ListaArray() {
		elementi = new Object[10];
		spazioDisponibile = 10;
	}
	
	/**
	 * Costruttore con la dimensione della lista
	 * <p>
	 * @param	dim dimensione della lista
	 */
	public ListaArray(int dim) throws IllegalArgumentException {
		if (dim <= 0)
			throw new IllegalArgumentException();
		
		elementi = new Object[dim];
		spazioDisponibile = dim;
	}
	
	/**
	 * Classe Iteratore
	 */
	private class iteratoreLista<E extends T> implements Iterator<T> {
		/**
		 * posizione dell'elemento da tornare
		 */
		private int position = 0;
		
		/**
		 * posizione ultimo elemento ritornato
		 */
		private int ultimo = -1;
		
		public boolean hasNext() {
			return (position < dim);
		}
		
		public T next() {
			if(position >= dim) {
				throw new ArrayIndexOutOfBoundsException();
			}
			
			ultimo = position;
			return (T)elementi[position++];
		}
		
		public void remove() {
			if(position < 0 || position > dim)
				throw new IndexOutOfBoundsException();
			
			if(ultimo == -1)
				// cancellazione appena effettuata, non si puo'
				// cancellare lo stesso elemento.
				throw new IndexOutOfBoundsException();
			
			int elementiDaSpostare = dim - position;
			if(elementiDaSpostare > 0) {
				// Sposta tutti gli elementi nell'array, perche' ha
				// effettuato una cancellazione nel mezzo
				System.arraycopy(elementi, position, elementi, ultimo, elementiDaSpostare);
			}
			
			elementi[--dim] = null; // eliminimo il duplicato all'ultima pos.
			ultimo = -1;
		}
	}

	@Override
	public Iterator<T> iteratore() {
		return new iteratoreLista<T>();
	}
	
	/**
	 * Controlla che l'array abbia ancora spazio a disposizione,
	 * altrimenti lo rialloca
	 */
	private void controllaSpazio() {
		if(spazioDisponibile == dim) {
			Object vecchiElementi[] = elementi;
			spazioDisponibile *= 2;
			elementi = new Object[spazioDisponibile];
			System.arraycopy(vecchiElementi, 0, elementi, 0, dim);
			vecchiElementi = null;
		}
	}
	
	/**
	 * Controlla che l'array abbia ancora spazio a disposizione,
	 * altrimenti lo rialloca
	 * <p>
	 * @param	dimNecessaria	Spazio richiesto per l'aggiunta
	 */	
	private void controllaSpazio(int dimNecessaria) {
		if(spazioDisponibile < dimNecessaria) {
			while(spazioDisponibile < dimNecessaria)
				spazioDisponibile *=2;
			
			Object	vecchiElementi[] = elementi;
			elementi = new Object[spazioDisponibile];
			System.arraycopy(vecchiElementi, 0, elementi, 0, dim);
			vecchiElementi = null;
		}
	}

	@Override
	protected void agg(T c) throws OperazioneNonSupportata {
		controllaSpazio();
		elementi[dim++] = c;
	}

	@Override
	public <S> Collezione<S> fai(Blocco<T, S> b) {
		Iterator<T> it = iteratore();
		Collezione<S> coll = new ListaArray<S>();
		T elem;
		
		while(it.hasNext()) {
			elem = it.next();
			if(elem != null) {
				try {
					coll.agg(b.valuta(elem));
				} catch (OperazioneNonSupportata exc) {
					System.out.println(exc);
				}
			}
		}
		return coll;
	}

	@Override
	public Collezione<T> rifiuta(BloccoBool<T> b) {
		Iterator<T> it = iteratore();
		Collezione<T> new_class = new ListaArray<T>();
		T elem;
		
		while(it.hasNext()) {
			elem = it.next();
			if(elem != null && !(b.valuta(elem)))
				try {
					new_class.agg(elem);
				} catch(OperazioneNonSupportata ex) {
					System.out.println(ex);
				}
		}
		return new_class;
	}

	@Override
	public Collezione<T> seleziona(BloccoBool<T> b) {
		Iterator<T> it = iteratore();
		Collezione<T> new_class = new ListaArray<T>();
		T elem;
		
		while(it.hasNext()) {
			elem = it.next();
			if(elem != null && b.valuta(elem))
				try {
					new_class.agg(elem);
				} catch(OperazioneNonSupportata ex) {
					System.out.println(ex);
				}
		}
		return new_class;
	}

	protected void agg(int index, T o) throws OperazioneNonSupportata {
		if(index < 0 || index > dim)
			throw new ArrayIndexOutOfBoundsException();
		
		controllaSpazio(dim+1);
		if(index < dim)
			System.arraycopy(elementi, index, elementi, index+1, dim - index);
		elementi[index] = o;
		dim++;
	}
	
	private void dimezzaSpazio() {
		Object vecchiElementi[] = elementi;
		spazioDisponibile = spazioDisponibile/2+1;
		elementi = new Object[spazioDisponibile];
		System.arraycopy(vecchiElementi, 0, elementi, 0, dim);
		vecchiElementi = null;
	}
	
	public T rimuovi(int index) throws OperazioneNonSupportata {
		if(index < 0 || index >= dim)
			throw new ArrayIndexOutOfBoundsException();
		
		T el = (T)elementi[index];
		
		int elementiDaSpostare = dim - index;
		if(elementiDaSpostare > 0) {
			// Sposta tutti gli elementi nell'array, perche' ha
			// effettuato una cancellazione nel mezzo
			System.arraycopy(elementi, index+1, elementi, index, elementiDaSpostare);
		}
		
		elementi[--dim] = null; // eliminimo il duplicato all'ultima pos.
		
		if((dim > 10) && ((spazioDisponibile/2) >= dim))
			dimezzaSpazio();
		
		return el;
	}

	@Override
	public T ultimoValore() throws OperazioneNonSupportata {
		if(dim == 0)
			throw new OperazioneNonSupportata();
		
		return (T)elementi[dim-1];
	}
	
	protected T get(int index) throws ArrayIndexOutOfBoundsException {
		if(index < 0 || index >= dim)
			throw new ArrayIndexOutOfBoundsException();
		
		return (T)elementi[index];
	}
	
	protected void aggiorna(int n, T t) {
		elementi[n] = t;
	}
	
	private void merge(Object[] temp, int start, int mid, int end, BloccoOrd ord) {
		int left = start;
		int right = mid + 1;
		int tmpPos = start;
		
		while((left<= mid) && (right <= end)) {
			if(ord.sort(elementi[left], elementi[right]))
				temp[tmpPos] = elementi[left++];
			else
				temp[tmpPos] = elementi[right++];
			
			tmpPos++;		
		}
		
		if(left <= mid) {
			/* restano elementi nella prima meta' */
			do {
				temp[tmpPos++] = elementi[left++];
			} while(tmpPos <= end);
		}
		/*
		 * Se esce per right <= end non devo ricopiare gli elementi.
		 * Nella seconda metà sono già ordinati nel vettore originale.
		 */
		
		/* ricopio i dati ordinati nel vettore originale */
		for(left = start; left < tmpPos; left++)
			elementi[left] = temp[left];
	}

	private void mergeSort(Object[] temp, int start, int end, BloccoOrd ord) {
		int middle;
		if(start < end) {
			middle = start + ((end - start) / 2); // punto medio
			
			mergeSort(temp, start, middle, ord);
			mergeSort(temp, middle+1, end, ord);
			merge(temp, start, middle, end, ord);
		}
	}
	
	protected void insertionSort(BloccoOrd ord) {
		Object temp;
		int i = 0;
		int j = 0;
		for(i = 0; i < dim; i++) {
			temp = elementi[i];
			for(j = i; (j>0) && ord.sort(temp, elementi[j-1]); j--)
				elementi[j] = elementi[j-1];
			elementi[j] = temp;
		}
	}
	
	protected void sort(BloccoOrd ord) {
		Object temp[] = new Object[dim];
		mergeSort(temp, 0, dim-1, ord);
	}
	
	
	public boolean equals(Object t) {
		
        if (t == this)
            return true;
        if (!(t instanceof ListaArray))
            return false;

        Iterator<T> iter = iteratore();
        Iterator iter_t = ((ListaArray) t).iteratore();
        while(iter.hasNext() && iter_t.hasNext()) {
        	T el_iter = iter.next();
            Object el_iter_t = iter_t.next();
            if(el_iter == null) {
            	return el_iter_t == null;
            } else {
            	if(!(el_iter.equals(el_iter_t)))
            		return false;
            }
        }
        
        // Se ci sono altri elementi in una delle due collezioni
        // non possono essere uguali
        if(iter.hasNext() || iter_t.hasNext())
        	return false;
        else
        	return true;
    }

	public <S> S inietta(BloccoBinario<T, S> f, S g) {
		Iterator<T> it = iteratore();
		S return_value = g;
		T elem;

		while(it.hasNext()) {
			elem = it.next();
			if(elem != null)
				return_value = f.valuta(elem , return_value);
		}
		return return_value;
	}
}
