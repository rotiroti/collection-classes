package progetto.util;

import java.util.Iterator;
import progetto.eccezioni.*;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public abstract class ListaAstratta<T> extends Collezione<T> implements Lista<T> {

	/* (non-Javadoc)
	 * @see progetto.util.Lista#agg(java.lang.Object)
	 */
	protected void agg(T c) throws OperazioneNonSupportata {
			throw new OperazioneNonSupportata();
	}

	/* (non-Javadoc)
	 * @see progetto.util.Lista#agg(int, java.lang.Object)
	 */
	protected void agg(int index, T o) throws OperazioneNonSupportata {
		throw new OperazioneNonSupportata();
	}

	/* (non-Javadoc)
	 * @see progetto.util.Lista#aggTutta(progetto.util.Collezione)
//	 */
//	public void aggTutta(Collezione<? extends T> c) throws OperazioneNonSupportata {
//		throw new OperazioneNonSupportata();
//	}

	/* (non-Javadoc)
	 * @see progetto.util.Lista#indiceDi(java.lang.Object)
	 */
	public int indiceDi(T o) throws OperazioneNonSupportata {
		if(dim == 0)
			throw new OperazioneNonSupportata("Collezione vuota!");
		
		int index = 0;
		boolean trovato = false;
		Iterator<T> it = iteratore();
		T el;
		
		while(it.hasNext()) {
			el = it.next();
			if(equivale(el, o)) {
				trovato = true;
				break;
			}
			index++;
		}
		
		if(trovato)
			return index;
		else
			return -1;
	}

	/* (non-Javadoc)
	 * @see progetto.util.Lista#iteratore()
	 */
	abstract public Iterator<T> iteratore();

	/* (non-Javadoc)
	 * @see progetto.util.Lista#primoValore()
	 */
	public T primoValore() throws OperazioneNonSupportata {
		if(dim == 0)
			throw new OperazioneNonSupportata("Collezione vuota!");
		
		return iteratore().next();
	}

	/* (non-Javadoc)
	 * @see progetto.util.Lista#rimuovi(int)
	 */
	public T rimuovi(int index) throws IndexOutOfBoundsException,
		OperazioneNonSupportata {
		throw new OperazioneNonSupportata();
	}

	/* (non-Javadoc)
	 * @see progetto.util.Lista#utlimoValore()
	 */
	public T ultimoValore() throws OperazioneNonSupportata {
		throw new OperazioneNonSupportata();
	}

}
