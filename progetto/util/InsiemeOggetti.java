package progetto.util;

import java.util.Iterator;

import progetto.eccezioni.OperazioneNonSupportata;


/**
 *
 * @author Marco Rotiroti
 * 
 */
public class InsiemeOggetti<T> extends Insieme<T> {

	/**
	 * 
	 */
	public InsiemeOggetti() {
		super();
	}

	/**
	 * @param dim
	 * @throws IllegalArgumentException
	 */
	public InsiemeOggetti(int dim) throws IllegalArgumentException {
		super(dim);
	}
	
	public boolean equivale(Object t1, Object t2) {
		return (t1 == t2);
	}
	
	/**
	 * Esegue l'unione di due insiemi.
	 * 
	 * @param t
	 * @return un nuovo insieme contenente l'unione.
	 */
	public InsiemeOggetti<T> unione(Insieme<T> t) {
		InsiemeOggetti<T> insOggUnione = new InsiemeOggetti<T>();
		
		if (t.dim == 0)
		{
			try {
				insOggUnione.aggTutta(this);
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
			
			return insOggUnione;
		}
		else if (this.dim == 0)
		{
			try {
				insOggUnione.aggTutta(t);
			} catch (OperazioneNonSupportata e) { e.printStackTrace(); }
			
			return insOggUnione;
		}
		else
		{
			Iterator<T> iter = iteratore();
			T elem;
			
			try {
				insOggUnione.aggTutta(t);
				while (iter.hasNext()) {
					elem = iter.next();
					if (insOggUnione.contiene(elem) == false) 
						insOggUnione.agg(elem);
				}
			}catch(OperazioneNonSupportata excp) { excp.printStackTrace(); }
			
			return insOggUnione;
		}
	}	
	
	public InsiemeOggetti<T> intersezione(Insieme<T> t) {
		InsiemeOggetti<T> inters = new InsiemeOggetti<T>();
		
		if (t.dim == 0 || this.dim == 0)
			return inters;
		else
		{
			Iterator<T> iter = iteratore();			
			T elem;
			
			while(iter.hasNext()) {
				elem = iter.next();
				if (t.contiene(elem) == true)
					inters.agg(elem);
			}
			
			return inters;	
		}
	}
	
	/**
	 * Esegue la differenza fra due insieme.
	 * 
	 * @param t
	 * @return ritorna un nuovo insieme contenente la differenza.
	 */
	public InsiemeOggetti<T> differenza(Insieme<T> t) {
		InsiemeOggetti<T> insiemeDifferenza = new InsiemeOggetti<T>();
		
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
	public InsiemeOggetti<T> differenzaSimmetrica(Insieme<T> t) {
        // Differenza Simmetrica A "delta" B = (A-B) union (B-A) = (A union B) diff (A inters B)
		
        InsiemeOggetti<T> insDiffSimm = new InsiemeOggetti<T>();
        InsiemeOggetti<T> insAunitoB = new InsiemeOggetti<T>();
        InsiemeOggetti<T> insAintersecatoB = new InsiemeOggetti<T>();
        
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
	public InsiemeOggetti<Insieme<T>> parti() {
		InsiemeOggetti<Insieme<T>> sottoinsiemi = new InsiemeOggetti<Insieme<T>>();
		InsiemeOggetti<T> insTemp = null;
		String binString;
		Character c = null;
		
		for(int i = 0; i < (power(2,dim)); i++) {
			binString = Integer.toBinaryString(i);
			insTemp = new InsiemeOggetti<T>(i+1);
			
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
	public InsiemeOggetti<Insieme<T>> parti(int n) {
		if(n > dim)
			throw new IllegalArgumentException();
		
		InsiemeOggetti<Insieme<T>> sottoinsiemi = new InsiemeOggetti<Insieme<T>>();
		InsiemeOggetti<T> insTemp = null;
		String binString;
		Character c = null;
		Integer pos[] = new Integer[dim];
		
		if(n == 0) {
			insTemp = new InsiemeOggetti<T>();
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
				insTemp = new InsiemeOggetti<T>(n);
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
}
