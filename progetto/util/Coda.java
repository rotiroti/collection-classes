package progetto.util;

import progetto.eccezioni.*;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public class Coda<T> {

	private CollezioneOrdinata<T> queue = null;

	/**
	 * Costruttore base
	 */
	public Coda() {
		queue = new CollezioneOrdinata<T>();
	}
	
	/**
	 * Costruttore con dimensione variabile
	 * <p>
	 * @param dim
	 */
	public Coda(int dim) {
		queue = new CollezioneOrdinata<T>(dim);
	}
	
	/**
	 * Ritorna la dimensione della coda
	 * <p>
	 * @return	dimensione coda
	 */
	public int dim() {
		return queue.dim();
	}
	
	/**
	 * Accoda un elemento
	 * <p>
	 * @param t	Elemento da accodare
	 */
	public void enqueue(T t) {
		queue.aggUltimo(t);
	}
	
	/**
	 * Ritorna l'elemento in testa alla coda
	 * <p>
	 * @return elemento in testa
	 */
	public T dequeue() {
		return queue.rimPrimo();
	}
	
	/**
	 * Formatta adeguatamente la stringa che
	 * rappresenta la coda
	 */
	public String toString() {
		return queue.toString();
	}
	
	/**
	 * Valuta se la coda e' vuota
	 * <p>
	 * @return true se la coda e' vuota, else altimenti
	 */
	public boolean isEmpty() {
		return queue.vuota();
	}
	
	/**
	 * Ritorna il primo elemento della coda, senza rimuoverlo
	 * <p>
	 * @return	elemento in testa
	 */
	public T top() {
		try {
			return queue.primoValore();
		} catch (OperazioneNonSupportata ex) {
			System.out.println(ex);
			return null;
		}
	}
}
