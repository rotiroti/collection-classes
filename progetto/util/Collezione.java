package progetto.util;

import java.util.Iterator;
import progetto.eccezioni.*;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public abstract class Collezione<T> {
	/**
	 * Numero degli elementi nella collezione
	 */
	protected int dim = 0;
	
	/**
	 * Controlla se la collezione e' vuota.
	 * <p>
	 * @return true, se la collezione e' vuota; false altrimenti.
	 */
	public boolean vuota() {
		return (this.dim == 0);
	}
	
	/**
	 * Ritorna il numero di elementi nella collezione
	 * <p>
	 * @return Numero elementi
	 */
	public int dim() {
		return this.dim;
	}
	
	/**
	 * Aggiunge un elemento alla collezione.
	 * <p>
	 * @param T Elemento da aggiungere
	 */
	abstract protected void agg(T c) throws OperazioneNonSupportata;
	
	/**
	 * Ritorna un iteratore per la collezione
	 * <p>
	 * @return	Iterator iteratore per la collezione
	 */
	abstract public Iterator<T> iteratore();
	
	/**
	 * Rimuove un elemento dalla collezione
	 * <p>
	 * @param Object Elemento da rimuovere
	 */
	public void rimuovi(Object c) {
		Iterator<T> it = iteratore();
		
		if(c == null) {
			while(it.hasNext()) {
				if(it.next() == null)
					it.remove();
			}
		} else {
			while(it.hasNext()) {
				if(equivale(c, it.next()))
					it.remove();
			}
		}
	}
	
	/**
	 * 
	 * @param c Collezione da confrontare.
	 * @return true, se tutti gli elementi sono contenuti in c;
	 *         false altrimenti
	 */
	public boolean contieneTutti(Collezione<T> c) {
        Iterator<T> e = c.iteratore();
        while (e.hasNext())
            if (!contiene(e.next()))
                return false;
        return true;
    }

	
	/**
	 * Funzione che controlla l'uguaglianza di due oggetti
	 * di una Collezione.
	 * <p>
	 * Utilizza equals in tutti i casi, tranne nell'<code>InsiemeOggetti</code>
	 * in cui viene "overridden" per utilizzare '=='
	 * <p>
	 * @param	Object	elemento da confrontare
	 * @param	Object	elemento da confrontare
	 * 
	 * @return	vero se sono uguali, falso altrimenti
	 */
	protected boolean equivale(Object t1, Object t2) {
		return t1.equals(t2);
	}
	
	/**
	 * Numero delle occorrenze di un elemento.
	 * <p>
	 * @param	T	elemento da cercare nella collezione
	 * 
	 * @return	numero di occorrenze dell'elemento
	 */
	public int occorrenzeDI(T t) {
		// Viene istanziato a run-time l'iteratore 
		// per la giusta struttura dati
		Iterator<T> it = iteratore();
		int n_occorrenze = 0;
		
		if(t == null) {
			while(it.hasNext()) {
				if(it.next() == null)
					n_occorrenze++;
			}
		} else {
			while(it.hasNext()) {
				if(equivale(t, it.next()))
					n_occorrenze++;
			}
		}
		return n_occorrenze;
	}
	
	/**
	 * Controlla se in una Collezione e' contenuto un dato elemento.
	 * <p>
	 * @param	T	elemento da cercare nella collezione
	 * 
	 * @return	vero se l'elemento e' presente, false altrimenti
	 */
	public boolean contiene(T t) {
		// Viene istanziato a run-time l'iteratore 
		// per la giusta struttura dati
		Iterator<T> it = iteratore();
		
		if(t == null) {
			while(it.hasNext()) {
				if(it.next() == null)
					return true;
			}
		} else {
			while(it.hasNext()) {
				if(equivale( t, it.next()))
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Aggiunge tutta la collezione passata come parametro
	 * alla collezione chiamante.
	 * <p>
	 * @param Collezione     Collezione da aggiungere. 
	 */
	public void aggTutta(Collezione<? extends T> C) throws OperazioneNonSupportata {
		Iterator<? extends T> it = C.iteratore();
		
		while(it.hasNext()) {
			agg(it.next());
		}
	}
	
	/**
	 * Esegue l'operazione {@code b }
	 */
	public void fai(BloccoVoid<T> b) {
		Iterator<T> it = iteratore();
		
		while(it.hasNext()) {
			b.valuta(it.next());
		}
	}
	
	/**
	 * Implementa il metodo toString per stampare la collezione.
	 * <p>
	 * @return	stringa formattata
	 */
	public String toString() {
		Iterator<T> it = iteratore();
		T x;
		String stringa = "[ ";
		
		while(it.hasNext()) {
			x = it.next();
			if(x != null) {
				stringa += x;
				stringa += ", ";
			}
		}
		return stringa + "]";
	}
	
	/**
	 * Esegue il la funzione all'interno del blocco {@code b}
	 * su tutta la {@code Collezione}
	 * <p>
	 * @param	Blocco	Blocco contenente il metodo da eseguire
	 * @return	Nuova {@code Collezione<S>} 
	 */
	abstract public <S> Collezione<S> fai(Blocco<T, S> b);
	
	/**
	 * Crea una nuova {@code Collezione<T>} dagli elementi della
	 * {@code Collezione<T>} chiamante che soddisfano {@code b}
	 * <p>
	 * @param	b	Condizione logica
	 * @return	Nuova {@code Collezione<T>}
	 */
	abstract public Collezione<T> seleziona(BloccoBool<T> b);
	
	/**
	 * Crea una nuova {@code Collezione<T>} dagli elementi della
	 * {@code Collezione<T>} chiamante che non soddisfano {@code b}
	 * <p>
	 * @param	b	Condizione logica
	 * @return	Nuova {@code Collezione<T>}
	 */
	abstract public Collezione<T> rifiuta(BloccoBool<T> b);
	
	/**
	 * Inietta una funzione da chiamare su ogni elemento ed
	 * un valore per la Collezione vuota.
	 * <p>
	 * @param f	Blocco contenente la funzione valuta
	 * @param g	valore base per la Collezione vuota
	 * @return	valore computato
	 */
	abstract public <S> S inietta(BloccoBinario<T, S> f, S g);

	/**
	 * Converte una Collezione in un Vettore.
	 * <p>
	 * @return Vettore<T> un nuovo Vettore contenente tutti
	 * 		   gli elementi della collezione chiamante.
	 */
	public Vettore<T> comeVettore() {
		Vettore<T> vec = new Vettore<T>(dim);
		
		try {
			vec.aggTutta(this);
		} catch (OperazioneNonSupportata e) {
			e.printStackTrace();
		}
		
		return vec;
	}
	
	/**
	 * Converte una Collezione in un Insieme.
	 * <p>
	 * @return Insieme<T> un nuovo Insieme contenente tutti
	 * 		   gli elementi della collezione chiamante
	 */
	public Insieme<T> comeInsieme() {
		Insieme<T> ins = new Insieme<T>();
		Iterator<T> iter = iteratore();
		
		while(iter.hasNext()) {
			ins.agg(iter.next());
		}
		
		return ins;
	}
	
	/**
	 * Converte una Collezione in un InsiemeOggetti.
	 * <p>
	 * @return InsiemeOggetti<T> un nuovo InsiemeOggetti contenente tutti
	 * 		   gli elementi della collezione chiamante
	 */
	public InsiemeOggetti<T> comeInsiemeOggetti() {
		InsiemeOggetti<T> insOgg = new InsiemeOggetti<T>();
		Iterator<T> iter = iteratore();
		
		while(iter.hasNext()) {
			insOgg.agg(iter.next());
		}
		
		return insOgg;
	}
	
	/**
	 * Converte una Collezione in un VettoreEstensibile.
	 * <p>
	 * @return VettoreEstensibile<T> un nuovo VettoreEstensibile contenente tutti
	 * 		   gli elementi della collezione chiamante
	 */
	public VettoreEstensibile<T> comeVettoreEstensibile() {
		VettoreEstensibile<T> vettExt = new VettoreEstensibile<T>(this.dim);
		try {
			vettExt.aggTutta(this);
		} catch (OperazioneNonSupportata e) {
			e.printStackTrace();
		}
		return vettExt;
	}
	
	/**
	 * Converte una Collezione in una CollezioneSortata.
	 * <p>
	 * @return CollezioneSortata<T> una nuova CollezioneSortata 
	 * 		   contenente tutti gli elementi della collezione chiamante
	 */
	public CollezioneSortata<T> comeCollezioneSortata(BloccoOrd<T> b) {
		CollezioneSortata<T> collSort = new CollezioneSortata<T>(b);
		
		collSort.aggTutta(this);
		return collSort;
	}
	
	/**
	 * Converte una Collezione in una CollezioneOrdinata.
	 * <p>
	 * @return CollezioneOrdinata<T> una nuova CollezioneOrdinata contenente 
	 * 		   tutti gli elementi della collezione chiamante.
	 */
	public CollezioneOrdinata<T> comeCollezioneOrdinata() {
		CollezioneOrdinata<T> collOrd =  new CollezioneOrdinata<T>();
		
		try {
			collOrd.aggTutta(this);
		} catch (OperazioneNonSupportata e) {
			e.printStackTrace();
		}
		
		return collOrd;
	}
	
	/**
	 * Converte una Collezione in una Busta.
	 * <p>
	 * @return Busta<T> una nuova busta contenente tutti
	 * 		   gli elementi della collezione chiamante
	 */
	public Busta<T> comeBusta() {
		Iterator<T> iter = iteratore();
		Busta<T> bu = new Busta<T>();
		
		while(iter.hasNext()) {
			bu.agg(iter.next());
		}
		
		return bu;
	}
}
