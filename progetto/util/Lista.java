package progetto.util;

import java.util.Iterator;
import progetto.eccezioni.*;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public interface Lista<T> {
	
	/**
	 * Aggiunge tutti gli elementi di un collezione a quella
	 * chiamante
	 * <p>
	 * @param c	collezione da aggiungere
	 */
	void aggTutta(Collezione<? extends T> c) throws OperazioneNonSupportata;
	
	/**
	 * Ritorna la posizione di un elemento
	 * <p>
	 * @param o elemento da ricercare
	 * @return indice dell'emento cercato, -1 se non presente
	 */
	int indiceDi(T o) throws OperazioneNonSupportata;
	
	/**
	 * Ritorna la dimensione della Collezione
	 * <p>
	 * @return dimensione della collezione
	 */
	int dim();
	
	/**
	 * Ritorna il primo elemento della lista
	 * <p>
	 * @return primo elemento
	 */
	T primoValore() throws OperazioneNonSupportata;
	
	/**
	 * Rimuove un elemento dalla lista
	 * <p> 
	 * @param o elemento da rimuovere
	 * @return true se l'elemento e' stato rimosso
	 */
	void rimuovi(Object o) throws OperazioneNonSupportata;
	
	/**
	 * Rimuove da una lista un elemento alla posizione specificata
	 * ritornandolo
	 * <p>
	 * @param index posizione dell'elemento da rimuovere
	 * @return elemento rimosso
	 */
	T rimuovi(int index) throws IndexOutOfBoundsException, OperazioneNonSupportata;
	
	/**
	 * Ritorna l'ultimo elemento di una lista
	 * <p>
	 * @return ultimo elemento
	 */
	T ultimoValore() throws OperazioneNonSupportata;
	
	/**
	 * Ritorna un iteratore valido per la lista
	 * <p>
	 * @return iteratore per la lista
	 */
	Iterator<T> iteratore();
}
