package progetto.util;

import java.util.Iterator;

import progetto.eccezioni.OperazioneNonSupportata;

/**
 *
 * @author Marco Rotiroti
 * 
 */

public class Busta<T> extends ListaArray<T> {
	
	private class Elemento<X extends T> {
		private T elemento = null;
		private int molt = 0;
		
		public Elemento(T t) {
			elemento = t;
			molt++;
		}
		
		public Elemento(T t, int m) {
			elemento = t;
			molt = m;
		}
		
		public T getElem() {
			return this.elemento;
		}
		
		public int getMolt() {
			return this.molt;
		}
		
		public void aumentaMolt(int m) {
			molt += m;
		}
		
		public void diminuisciMolt(int m) {
			if (m >= molt)
				throw new IllegalArgumentException();
			
			molt -= m;
		}
		
		public boolean equals(Object t) {
			if (t == this)
				return true;
			if(!(t instanceof Elemento))
				return false;
			
			return (this.getElem().equals(((Elemento)t).getElem()));
		}
		
		public String toString() {
			String str = "";
			
			for(int i = 0; i < getMolt(); i++)
				str += (getElem() + ", ");
			
			return str;
		}
	}
	
	
	/**
	 * Costruttore della classe Busta<T>
	 */
	public Busta() {
		super();
	}
	
	/**
	 * Costruttore della Busta<T> parametrizzato.
	 * 
	 * @param dim dimensione iniziale della Busta 
	 */
	public Busta(int dim) throws IllegalArgumentException {
		super(dim);
	}
	
	
	/**
	 * Aggiunge l'elemento t passato come parametro
	 * alla busta chiamante chiamante.
	 * 
	 * @param t elemento da aggiungere. 
	 */	
	public void agg(T t) {
		agg(t, 1);
	}
	
	/**
	 *  Aggiunge l'elemento t passato come parametro
	 *  alla busta chiamante.
	 * 
	 * @param t elemento da aggiungere. 
	 * @param n numero di volte che l'elemento t deve essere aggiunto. 
	 */
	public void aggNCopie(T t, int n) {
		agg(t, n);
	}

	/**
	 *  Ritorna la dimensione di della busta.
	 *  <p>
	 *  
	 *  @return restituisce il numero di elementi
	 *          presenti nella busta.
	 *  
	 */
	public int dim() {
		int temp = 0;
		Iterator<T> iter = iteratore();
		
				
		while (iter.hasNext())
		{
			Elemento<T> el = (Elemento<T>) iter.next();
			temp += el.getMolt();
		}
		
		return temp;
	}
	
	/**
	 * Ha lo stesso funzionamento di contieneTutti, 
	 * eseguendo il confronto sia sugli elementi che sulla
	 * loro molteplicita'.
	 * 
	 * @param bu Busta da confrontare
	 * 
	 * @return true, se bu contiene tutti gli elementi della busta chiamante;
	 * 		   false altriementi.
	 */
	private boolean tuttiElem(Busta<T> bu) {
		Iterator<T> e = bu.iteratore();
        while (e.hasNext())
            if(!esisteElem(((Elemento<T>)e.next())))
                return false;
        return true;
	}

	
	/**
	 * Controlla che l'elemento t sia contenuto nella Collezione
	 * chiamante. Devono corrispondere l'elemento e la sua molteplicita'
	 * <p>
	 * @param t
	 * @return true se esiste
	 */
	private boolean esisteElem(Elemento<T> t) {
		Iterator<T> it = iteratore();
		
		if(t == null) {
			while(it.hasNext()) {
				if(it.next() == null)
					return true;
			}
		} else {
			while(it.hasNext()) {
				Elemento<T> el = (Elemento<T>) it.next();
				if((el.getElem().equals(t.getElem())) &&
						el.getMolt() == t.getMolt())
					return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * Aggiunge l'oggetto passato come parametro
	 * alla busta chiamante, n volte.
	 * 
	 * @param t
	 * @param n
	 */
	public void agg(T t, int n) {
		if (t == null || n <= 0)
			throw new IllegalArgumentException();
		
		Elemento<T> el = new Elemento<T>(t, n);

		int index = -1;
		if(this.contiene(el.getElem())) {
			/* Se contiene gia' l'elemento aumenta la molteplicita'
			 * di quello gia' presente */
			try {
				index = this.indiceDi(((T)el));
			} catch (OperazioneNonSupportata e1) { e1.printStackTrace(); }
			((Elemento)get(index)).aumentaMolt(n);
		} 
		else {
			/* Elemento non presente, quindi aggiunge */
			try {
				super.agg(((T) el));
			} catch (OperazioneNonSupportata e) { e.printStackTrace();}
		}
	}
	
	
	/**
	 * Implementa il metodo toString per stampare la Busta. 
	 */
	public String toString() {
		Iterator<T> iter = iteratore();
		String str = "[";
		while(iter.hasNext()) {
			T el = iter.next();
			Elemento<T> eel = (Elemento<T>) el;

			str += eel.toString();
		}
		
		return str + "]";
	}
	

	/**
	 * Esegue l'unione fra due buste.
	 */
	public Busta<T> unione(Busta<T> t)
	{
		Busta<T> bustaUnione = new Busta<T>();
		
		if (this.dim == 0)
		{
			try {
				bustaUnione.aggTutta(t);								
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
			return bustaUnione;
		}
		
		else if (t.dim == 0)
		{
			try {
				bustaUnione.aggTutta(this);
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
			return bustaUnione;
		}	
		else
		{
			Iterator<T> iter = iteratore();
			Elemento<T> elem;
			
			try {
				bustaUnione.aggTutta(t);
			
				while (iter.hasNext())
				{
					elem = (Elemento) iter.next();
					bustaUnione.agg(elem.getElem(), elem.getMolt());
				}
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
			return bustaUnione;
		}
	}

	
	/**
	 * Esegue l'intersezione fra due buste.
	 */
	public Busta<T> intersezione(Busta<T> t)
	{
		Busta<T> bustaInter = new Busta<T>();
		
		if (this.dim == 0 || t.dim == 0)
		{
			return bustaInter;
		}
		
		else
		{
			Iterator<T> iter = iteratore();
			Elemento<T> elem;
						
			try {
								
				while (iter.hasNext())
				{
					elem = (Elemento) iter.next();					
					
					if ((t.contiene(elem.getElem()) == true))
					{
						int index = t.indiceDi((T)elem);
						Elemento<T> elem2 = (Elemento<T>) t.get(index);
						
						bustaInter.agg(elem.getElem(), min(elem.getMolt(), elem2.getMolt()));
					}
				}
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
		}
		return bustaInter;
	}
	
	
	/**
	 * Esegue la differenza tra due buste.
	 */
	public Busta<T> differenza(Busta<T> t)
	{
		Busta<T> bustaDiff = new Busta<T>();
		
		if (this.dim == 0)
			return bustaDiff;
		
		else if (t.dim == 0)
		{
			try {
				bustaDiff.aggTutta(this);
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
			return bustaDiff;
		}
		
		else
		{
			Iterator<T> iter = iteratore();
			Elemento<T> elem;
			
			while (iter.hasNext())
			{
				elem = (Elemento<T>) iter.next();
				
				if (t.contiene(elem.getElem()) == false)
					bustaDiff.agg(elem.getElem());					
			}
			return bustaDiff;
		}			
	}
	
	/**
	 * Esegue la differenza simmetrica tra due buste.
	 */
	public Busta<T> differenzaSimmetrica(Busta<T> t) {
        Busta<T> insDiffSimm = new Busta<T>();
        Busta<T> insAunitoB = new Busta<T>();
        Busta<T> insAintersecatoB = new Busta<T>();
        
        if (this.dim == 0)
        {
            try {
				insDiffSimm.aggTutta(t);
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
			
            return insDiffSimm;
        }
        else if (t.dim == 0)
        {
            try {
				insDiffSimm.aggTutta(this);
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
            
			return insDiffSimm;
        }
        else
        {
            insAunitoB = this.unione(t);        
            insAintersecatoB = this.intersezione(t);
            insDiffSimm = insAunitoB.differenza(insAintersecatoB);
            
            return insDiffSimm;
        }
	}
	
	
	/**
	 * Calcolo del minimo tra due interi.
	 */
	private int min(int a, int b)
	{
		return (a < b ? a : b);
	}
	
	
	/**
	 * Verifica che l'elemento t
	 * sia contenuto nella busta.
	 * 
	 * @return true se l'elemento appartiene alla busta
	 * 		   false altrimenti 
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
				Elemento<T> el = (Elemento) it.next();
				if(equivale(t, el.getElem()))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 */
	public int indiceDi(T o) throws OperazioneNonSupportata {
		if(dim == 0)
			throw new OperazioneNonSupportata("Collezione vuota!");
		
		int index = 0;
		boolean trovato = false;
		Iterator<T> it = iteratore();
		Elemento<T> el;
		
		while(it.hasNext()) {
			el = (Elemento) it.next();
			if(equivale(el, ((Elemento)o))) {
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
	
	/**
	 * 
	 */
	public <S> S inietta(BloccoBinario<T, S> f, S g) {
		Iterator<T> it = iteratore();
		S return_value = g;
		Elemento<T> elem;

		while(it.hasNext()) {
			elem = (Elemento)it.next();
			if(elem != null) {
				for(int i = 0; i < elem.getMolt(); i++)
					return_value = f.valuta(elem.getElem() , return_value);
			}
		}
		return return_value;
	}
	
	/**
	 * Esegue l'operazione {@code}
	 */
	public void fai(BloccoVoid<T> b) {
		Iterator<T> it = iteratore();
		
		while(it.hasNext()) {
			Elemento<T> el = (Elemento)it.next();
			for(int i = 0; i < el.getMolt(); i++)
				b.valuta(el.getElem());
		}
	}
	
	/**
	 * 
	 */
	@Override
	public <S> Busta<S> fai(Blocco<T, S> b) {
		Iterator<T> it = iteratore();
		Busta<S> coll = new Busta<S>();
		Elemento<T> elem;
		
		while(it.hasNext()) {
			elem = (Elemento)it.next();
			if(elem != null) {
				for(int i = 0; i < elem.getMolt(); i++) {
					coll.agg(b.valuta(elem.getElem()));
				}
			}
		}
		return coll;
	}
	
	/**
	 * 
	 */
	@Override
	public Busta<T> rifiuta(BloccoBool<T> b) {
		Iterator<T> it = iteratore();
		Busta<T> new_class = new Busta<T>();
		Elemento<T> elem;
		
		while(it.hasNext()) {
			elem = (Elemento) it.next();
				if(elem != null && !(b.valuta(elem.getElem())))
					new_class.agg(elem.getElem(), elem.getMolt());
		}
		return new_class;
	}
	
	/**
	 * 
	 */
	@Override
	public Busta<T> seleziona(BloccoBool<T> b) {
		Iterator<T> it = iteratore();
		Busta<T> new_class = new Busta<T>();
		Elemento<T> elem;
		
		while(it.hasNext()) {
			elem = (Elemento) it.next();
				if(elem != null && (b.valuta(elem.getElem())))
					new_class.agg(elem.getElem(), elem.getMolt());
		}
		return new_class;
	}
	
	/**
	 * 
	 */
	public void aggTutta(Collezione<? extends T> C) throws OperazioneNonSupportata {
		if(!(C instanceof Busta))
			throw new OperazioneNonSupportata();
		
		Iterator<? extends T> it = C.iteratore();
		
		while(it.hasNext()) {
			Elemento<T> el = (Elemento) it.next();
			agg(el.getElem(), el.getMolt());
		}
	}
	
	
	/**
	 * 
	 */
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if(!(o instanceof Busta))
			return false;
		
		if(((Busta<T>)o).dim() != dim())
			return false;
		
		else
			return tuttiElem(((Busta<T>)o));
	}
	
	
	/**
	 * Converte una Busta in un Insieme.
	 */
	public Insieme<T> comeInsieme() {
		Insieme<T> ins = new Insieme<T>();
		Iterator<T> iter = iteratore();
		
		while(iter.hasNext()) {
			ins.agg(((Elemento<T>)iter.next()).getElem());
		}
		
		return ins;
	}
	
	/**
	 * Converte una Busta in un InsiemeOggetti.
	 */
	public InsiemeOggetti<T> comeInsiemeOggetti() {
		InsiemeOggetti<T> ins = new InsiemeOggetti<T>();
		Iterator<T> iter = iteratore();
		
		while(iter.hasNext()) {
			ins.agg(((Elemento<T>)iter.next()).getElem());
		}
		
		return ins;
	}
	
	/**
	 * Converte una Busta in un Vettore.
	 */
	public Vettore<T> comeVettore() {
		Vettore<T> vet = new Vettore(this.dim());
		Iterator<T> iter = iteratore();
		
		while (iter.hasNext()) {
			try {
				Elemento<T> el = (Elemento<T>) iter.next();
				
				for (int i = 0; i < el.getMolt(); i++)
					vet.agg(((Elemento<T>)el).getElem());
					
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
		}
		return vet;
	}
	
	/**
	 * Converte una Busta in un VettoreEstensibile.
	 */
	public VettoreEstensibile<T> comeVettoreEstensibile() {		
		VettoreEstensibile<T> vet = new VettoreEstensibile<T>(this.dim);
		Iterator<T> iter = iteratore();
		
		while (iter.hasNext()) {
			try {
				Elemento<T> el = (Elemento<T>) iter.next();
				
				for (int i = 0; i < el.getMolt(); i++)
					vet.agg(((Elemento<T>)el).getElem());
					
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
		}
		return vet;
	}
	

	public CollezioneOrdinata<T> comeCollezioneOrdinata() {
		CollezioneOrdinata<T> collOrd = new CollezioneOrdinata<T>();
		Iterator<T> iter = iteratore();
		
		while(iter.hasNext()) {
			Elemento<T> el = (Elemento<T>) iter.next();
			
			for (int i = 0; i < el.getMolt(); i++) {
				try {
					collOrd.agg(((Elemento<T>)el).getElem());
				} catch (OperazioneNonSupportata e) {
					e.printStackTrace();
				}
			}
		}
		
		return collOrd;
	}
	

	public CollezioneSortata<T> comeCollezioneSortata(BloccoOrd<T> b) {
		CollezioneSortata<T> collSort = new CollezioneSortata<T>(b);
		Iterator<T> iter = iteratore();
		
		while(iter.hasNext()) {
			Elemento<T> el = (Elemento<T>) iter.next();
			
			for (int i = 0; i < el.getMolt(); i++) {
				collSort.agg(((Elemento<T>)el).getElem());
			}
		}
		
		return collSort;
	}
	
	/**
	 * 
	 * @param b
	 * @return
	 */
	public boolean lessOrEqual(Busta<T> b) {
		if(dim() < b.dim())
			return true;
		else if(dim() > b.dim())
			return false;
		else {
			Iterator<T> iter = iteratore();
			
			while(iter.hasNext()) {
				Elemento<T> el = (Elemento<T>)iter.next();
				if(!(b.contiene(el.getElem())))
					return false;
				else {
					int index = -1;
					try {
						index = b.indiceDi((T)el);
					} catch (OperazioneNonSupportata e) {
						e.printStackTrace();
					}
					Elemento<T> elem2 = (Elemento<T>) b.get(index);
					if(el.getMolt() > elem2.getMolt())
						return false;
				}
			}
			return true;
		}
	}
}
