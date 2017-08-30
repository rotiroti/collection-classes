package progetto.util;

import java.util.Iterator;
import progetto.eccezioni.*;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public class Insieme<T> extends ListaArray<T> {
	
	public Insieme() {
		super();
	}
	
	public Insieme(int dim) throws IllegalArgumentException {
		super(dim);
	}
	
	/**
     * Aggiunge l'elemento passato come parametro
     * all'insieme chiamante. No duplicati !!!
     * 
     * @param T
     *
     */	
    public void agg(T t) {
        
        if ((this.test_appartenenza(t) == false)) {
           try {
			super.agg(t);
           } catch (OperazioneNonSupportata e) {
        	   e.printStackTrace();
           }
        }
        return;
    }

	protected int power(int base, int exp) {
		if(base == 0)
			return 0;
		else if(base == 1 || exp == 0)
			return 1;
		else {
			int res = base;
			while(exp > 1) {
				res *= base;
				exp--;
			}
			return res;
		}
	}    
    
    /**
	 * Equivalenza tra Insiemi
	 * <p>
	 * @param	t	Insieme da confrontare
	 * @return	true se equivalenti, false altrimenti
	 */
	public boolean equals(Object t) {		
		if(t == this)
			return true;
		if(!(t instanceof Insieme))
			return false;
		
		if(((Insieme)t).dim() != dim)
			return false;
		
		return contieneTutti(((Insieme)t));
	}

		
	/**
	 * Verifica che un elemento appartenga all'insieme
	 * che riceve il metodo.
	 * 
	 * @param t
	 * @return true se l'elemento appartine all'insieme,
	 *         false in caso contrario.
	 */
	public boolean test_appartenenza(T t) {
		return contiene(t);
	}
	
	
	/**
	 * Esegue l'unione di due insiemi.
	 * 
	 * @param t
	 * @return un nuovo insieme contenente l'unione.
	 */
	public Insieme<T> unione(Insieme<T> t) {
		Insieme<T> insiemeUnione = new Insieme<T>();
		
		if (t.dim == 0)
		{
			try {
				insiemeUnione.aggTutta(this);
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
			
			return insiemeUnione;
		}
		else if (this.dim == 0)
		{
			try {
				insiemeUnione.aggTutta(t);
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
			
			return insiemeUnione;
		}
		else
		{
			Iterator<T> iter = iteratore();
			T elem;
			
			try {
				insiemeUnione.aggTutta(t);
				while (iter.hasNext()) {
					elem = iter.next();
					if (insiemeUnione.contiene(elem) == false) 
						insiemeUnione.agg(elem);
				}
			}catch(OperazioneNonSupportata excp) { excp.printStackTrace(); }
			
			return insiemeUnione;
		}
	}
	
	/**
	 * Esegue l'intersezione di due insiemi.
	 * 
	 * @param t
	 * @return un nuovo insieme contenente l'intersezione.
	 */
	public Insieme<T> intersezione(Insieme<T> t) {
		Insieme<T> insiemeIntersezione = new Insieme<T>();
		
		if (t.dim == 0 || this.dim == 0)
			return insiemeIntersezione;
		else
		{
			Iterator<T> iter = iteratore();			
			T elem;
			
			while(iter.hasNext()) {
				elem = iter.next();
				if (t.contiene(elem) == true)
					insiemeIntersezione.agg(elem);
			}
			
			return insiemeIntersezione;	
		}		
	}
	
	
	/**
	 * Esegue la differenza fra due insieme.
	 * 
	 * @param t
	 * @return ritorna un nuovo insieme contenente la differenza.
	 */
	public Insieme<T> differenza(Insieme<T> t) {
		Insieme<T> insiemeDifferenza = new Insieme<T>();
		
		if (this.dim == 0)
			return insiemeDifferenza;
		else if (t.dim == 0)
		{
			try {
				insiemeDifferenza.aggTutta(this);
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
			return insiemeDifferenza;
		}
		else
		{
			Iterator<T> iter = iteratore();			
			T elem;
			
			while (iter.hasNext()) {
				elem = iter.next();
				if (t.contiene(elem) == false)
					insiemeDifferenza.agg(elem);				
			}
						
			return insiemeDifferenza;
		}
	}
	
	/**
	 * Esegue la differenza simmetrica fra due insieme.
	 * 
	 * @param t
	 * @return ritorna un nuovo insieme contenente la differenza simmetrica.
	 */
	public Insieme<T> differenzaSimmetrica(Insieme<T> t) {
        // Differenza Simmetrica A "delta" B = (A-B) union (B-A) = (A union B) diff (A inters B)
		
        Insieme<T> insDiffSimm = new Insieme<T>();
        Insieme<T> insAunitoB = new Insieme<T>();
        Insieme<T> insAintersecatoB = new Insieme<T>();
        
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
	 * Calcola l'insieme delle parti di un insieme.
	 * 
	 * @return un nuovo insieme contenente l'insieme delle parti 
	 *         dell'insieme che riceve il metodo. 
	 */
	public Insieme<Insieme<T>> parti() {
		Insieme<Insieme<T>> sottoinsiemi = new Insieme<Insieme<T>>();
		Insieme<T> insTemp = null;
		String binString;
		Character c = null;
		
		for(int i = 0; i < (power(2,dim)); i++) {
			binString = Integer.toBinaryString(i);
			insTemp = new Insieme<T>(i+1);
			
			String zeroStr = new String();
			for(int x = 0; x < (dim -binString.length()); x++)
				zeroStr += "0";
			
			binString = zeroStr.concat(binString);	

			for(int j = 0; j < binString.length(); j++) {
				c = binString.charAt(j);
				if(c.equals('1')) {
					try {
						insTemp.agg(this.get(j));
					} catch (ArrayIndexOutOfBoundsException e) {
						e.printStackTrace();
					}
				}
			}
			
			sottoinsiemi.agg(insTemp);
		}
		
		return sottoinsiemi;
	}


	
	/**
	 * @param n
	 * @return un nuovo insieme contenente tutti i sottoinsiemi 
	 *         di cardinalità n.
	 */
	public Insieme<Insieme<T>> parti(int n) {
		if(n > dim)
			throw new IllegalArgumentException();
		
		Insieme<Insieme<T>> sottoinsiemi = new Insieme<Insieme<T>>();
		Insieme<T> insTemp = null;
		String binString;
		Character c = null;
		Integer pos[] = new Integer[dim];
		
		if(n == 0) {
			insTemp = new Insieme<T>();
			sottoinsiemi.agg(insTemp);
			return sottoinsiemi;
		}
		
		for(int i = 0; i < (power(2,dim)); i++) {
			binString = Integer.toBinaryString(i);
			
			String zeroStr = new String();
			// aggiungo zeri alla stringa di bit
			for(int x = 0; x < (dim - binString.length()); x++)
				zeroStr += "0";
			
			binString = zeroStr.concat(binString);
			
			int index = 0;
			
			for(int j = 0; j < binString.length(); j++) {
				c = binString.charAt(j);
				if(c.equals('1')) {
					pos[index++] = j;
				}
			}
			
			if(index == n) {
				insTemp = new Insieme<T>(n);
				for(int k = 0; k < n; k++) {
					try {
						insTemp.agg(get(pos[k]));
					} catch (ArrayIndexOutOfBoundsException e) {
						e.printStackTrace();
					}
				}
			}
				
			sottoinsiemi.agg(insTemp);
			insTemp = null;
		}
		
		return sottoinsiemi;
	}
	
	public boolean lessOrEqual(Insieme<T> ins) {
		if(dim < ins.dim())
			return true;
		else if(dim > ins.dim())
			return false;
		else {
			Iterator<T> iter = iteratore();
			
			while(iter.hasNext()) {
				if(!(ins.contiene(iter.next())))
					return false;
			}
			return true;
		}
	}
}