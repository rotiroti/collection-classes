package progetto.util;

import progetto.eccezioni.*;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public class Pila<T> {
	
	CollezioneOrdinata<T> stack = null;

	/**
	 * Costruttore base
	 * <p>
	 * @see	progetto.util.CollezioneOrdinata#CollezioneOrdinata()
	 */
	public Pila() {
		stack = new CollezioneOrdinata<T>();
	}

	/**
	 * Costruttore con dimensione variabile
	 * <p>
	 * @param dim
	 * @throws IllegalArgumentException
	 */
	public Pila(int dim) throws IllegalArgumentException {
		stack = new CollezioneOrdinata<T>(dim);
	}
	
	public int dim() {
		return stack.dim();
	}
	
	/**
	 * Aggiunge in testa alla pila
	 * <p>
	 * Le aggiunte sono fatte con {@code aggiungiUltimo} per minimizzare
	 * le chiamate a {@code System.arracopy}
	 * <p>
	 * @param t	elemento da inserire nella coda
	 */
	public void push(T t) {
		stack.aggUltimo(t);
	}
	
	/**
	 * Ritorna l'elemento dalla testa della pila
	 * <p>
	 * Le rimozioni sono fatte in coda alla lista con {@code rimUltimo}
	 * per minimizzare le chiamate a {@code System.arracopy}
	 * <p>
	 * @return elemento in testa
	 */
	public T pop() {
		return stack.rimUltimo();
	}
	
	/**
	 * Ritorna l'elemento in testa alla pila, senza rimuoverlo
	 * <p>
	 * @return elemento in testa alla pila
	 */
	public T top() {
		try {
			return stack.ultimoValore();
		} catch (OperazioneNonSupportata ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	/**
	 * Torna la stringa che rappresenta lo stack
	 * <p>
	 * @return	stringa formattata
	 */
	public String toString() {
		return stack.toString();
	}
	
	/**
	 * Controlla che la pila sia vuota
	 * <p>
	 * @return true se la pila e' vuota, false altrimenti
	 */
	public boolean isEmpty() {
		return stack.vuota();
	}
}
