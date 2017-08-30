package progetto.util;

import java.util.Iterator;

import progetto.eccezioni.OperazioneNonSupportata;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public class MatriceSparsa<T> extends Matrice<T> {
	
	/**
	 * Classe privata che rappresenta il singolo elemento
	 * della {@code MatriceSparsa}
	 *
	 *
	 * @param <X>
	 */
	private class Elemento<X extends T> {
		private T elem = null;
		private int r = 0;
		private int c = 0;
		
		public Elemento(T t, int x, int y) {
			elem = t;
			r = x;
			c = y;
		}
		
		public T getElem() {
			return elem;
		}
		
		public void setPos(int newx, int newy) {
			r = newx;
			c = newy;
		}
		
		public int getRow() {
			return r;
		}
		
		public int getCol() {
			return c;
		}
		
		public String toString() {
			return elem.toString();
		}
		
		public boolean equals(Object o) {
			if(o == this)
				return true;
			if(!(o instanceof Elemento))
				return false;
			
			return this.equals(o);
		}
	}
	
	/**
	 * Classe MatriceSparsa
	 */
	private int dimRighe = 0;
	private int dimColonne = 0;
	private T emptyEl = null;
	private Vettore<Elemento<T>> elemVec = null;

	/**
	 * 
	 */
	public MatriceSparsa(int x, int y, T empty) {
		dimRighe = x;
		dimColonne = y;
		emptyEl = empty;
		elemVec = new Vettore<Elemento<T>>(x*y);
	}
	
	public int dim() {
		return dimRighe*dimColonne;
	}

	/* (non-Javadoc)
	 * @see progetto.util.Matrice#aggiorna(int, int, java.lang.Object)
	 */
	@Override
	public void aggiorna(int n, int m, T t) {
		if( n < 0 || n > dimRighe || m < 0 || m > dimColonne)
			throw new IllegalArgumentException();
		
		Elemento<T> el = new Elemento<T>(t, n, m);
		try {
			elemVec.agg(el);
		} catch (OperazioneNonSupportata e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see progetto.util.Matrice#at(int, int)
	 */
	@Override
	public T at(int n, int m) {
		if(n < 0 || n > dimRighe || m < 0 || m > dimColonne)
			throw new IllegalArgumentException();
		
		T el = contieneElemento(n, m);
		return (el != null)? el : emptyEl;
	}

	/* (non-Javadoc)
	 * @see progetto.util.Matrice#atDA(int)
	 */
	@Override
	public Vettore<T> atDA(int n) {
		int dimRC = (dimRighe + dimColonne - 2);
		if(n < 0 || n > dimRC )
			throw new IllegalArgumentException();
		
		Vettore<T> vec = new Vettore<T>(dimRighe);
		int c = 0;
		int r = 0;
		
		if (n > dimColonne-1) {
			c = dimColonne-1;
			r = n - c;
		} else {
			c = n;
			r = 0;
		}
		
		for(; r < dimRighe && c >= 0; r++, c--) {
			try {
				vec.agg(at(r, c));
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			} catch (OperazioneNonSupportata e) {
				e.printStackTrace();
			}
		}
		
		return vec;
	}

	/* (non-Javadoc)
	 * @see progetto.util.Matrice#atDD(int)
	 */
	@Override
	public Vettore<T> atDD(int n) {
		if(n < (-dimRighe+1) || n > (dimColonne-1))
			throw new IllegalArgumentException();
		
		Vettore<T> vec = new Vettore<T>(dimRighe);
		int r;
		int c;
		if(n == 0) {
			r = 0;
			c = 0;
		} else if(n > 0) {
			c = n;
			r = 0;
		} else {
			c = 0;
			r = -n;
		}
		
		for(; c < dimColonne && r < dimRighe; c++, r++) {
			try {
				vec.agg(at(r, c));
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			} catch (OperazioneNonSupportata e) {
				e.printStackTrace();
			}
		}
		return vec;
	}

	/* (non-Javadoc)
	 * @see progetto.util.Matrice#col(int)
	 */
	@Override
	public Vettore<T> col(int n) {
		if(n < 0 || n > dimColonne)
			throw new IllegalArgumentException();
		
		Vettore<T> vec = new Vettore<T>(dimRighe);
		
		for(int j = 0; j < dimRighe; j++) {
			T el = contieneElemento(j, n);
			if(el != null) {
				try {
					vec.agg(el);
				} catch (OperazioneNonSupportata e) {
					e.printStackTrace();
				}
			} else {
				try {
					vec.agg(emptyEl);
				} catch (OperazioneNonSupportata e) {
					e.printStackTrace();
				}
			}
		}
		
		return vec;
	}

	/* (non-Javadoc)
	 * @see progetto.util.Matrice#dimC()
	 */
	@Override
	public int dimC() {
		return dimColonne;
	}

	/* (non-Javadoc)
	 * @see progetto.util.Matrice#dimR()
	 */
	@Override
	public int dimR() {
		return dimRighe;
	}

	/* (non-Javadoc)
	 * @see progetto.util.Matrice#riga(int)
	 */
	@Override
	public Vettore<T> riga(int n) {
		if (n < 0 || n >= dimColonne)
			throw new IllegalArgumentException();
		
		Vettore<T> vec = new Vettore<T>(dimColonne);
		
		for(int j = 0; j < dimColonne; j++) {
			T el = contieneElemento(n, j);
			if(el != null) {
				try {
					vec.agg(el);
				} catch (OperazioneNonSupportata e) {
					e.printStackTrace();
				}
			} else {
				try {
					vec.agg(emptyEl);
				} catch (OperazioneNonSupportata e) {
					e.printStackTrace();
				}
			}
		}
		
		return vec;
	}
	
	public String toString() {
		String str = "";
		for(int i = 0; i < dimRighe; i++) {
			str += "[ ";
			for(int j = 0; j < dimColonne; j++) {
				T el = contieneElemento(i, j);
				if(el != null)
					str += el;
				else
					str += emptyEl.toString();
				
				str += ", ";
			}
			str += "]\n";
		}
		return str;
	}

	
	private T contieneElemento(int r, int c) {
		Iterator<Elemento<T>> iter = elemVec.iteratore();
		
		while(iter.hasNext()) {
			Elemento<T> el = iter.next();
			if(el.getRow() == r && el.getCol() == c)
				return el.getElem();
		}
		
		return emptyEl;
	}
	
	public Iterator<T> iteratore() {
		return new IteratoreMatrice();
	}
	
	private class IteratoreMatrice<X extends T> implements Iterator<T> {
		
		private int posRighe = 0;
		private int posColonne = 0;
		
		public boolean hasNext() {
			return (posRighe < dimRighe && posColonne < dimColonne);
		}
		
		public T next() {
			T el = at(posRighe, posColonne++);
			
			if(posColonne >= dimColonne) {
				if(posRighe < dimRighe)
					posColonne = 0;
				posRighe++;
			}
			
			return el;
		}
		
		public void remove() {
			if(posRighe < dimRighe && posColonne < dimColonne)
				aggiorna(posRighe, posColonne, emptyEl);
		}
		
		public boolean hasNextRow() {
			return (posRighe < dimRighe);
		}
		
		public Vettore<T> nextRow() {
			Vettore<T> vec = new Vettore<T>(100);
			for(int i = posRighe, j = 0; j < dimColonne; j++) {
				try {
					vec.agg(contieneElemento(i, j));
				} catch (OperazioneNonSupportata e) {
					e.printStackTrace();
				}
			}
			posRighe++;
			return vec;
		}
		
		public int getR() {
			return posRighe;
		}
		
		public int getC() {
			return posColonne;
		}
	}
	
	public <S> MatriceSparsa<S> fai(Blocco<T, S> b) {
		IteratoreMatrice<T> it = new IteratoreMatrice<T>();
		MatriceSparsa<S> coll = new MatriceSparsa<S>(dimRighe, dimColonne, (S)emptyEl);
		T elem;
		
		while(it.hasNext()) {
			int r = it.getR();
			int c = it.getC();
			elem = it.next();
			if(elem != null) {
				coll.aggiorna(r, c, b.valuta(elem));
			}
		}
		return coll;
	}
	
	public MatriceSparsa<T> moltiplicazione(Matrice<T> m) {
		if(dimR() != m.dimC())
			throw new IllegalArgumentException();
		
		MatriceSparsa<T> retMat	= new MatriceSparsa<T>(dimRighe, m.dimC(), emptyEl);
		
		IteratoreMatrice<T> iter = new IteratoreMatrice();
		
		int i = 0, j = 0;
		while(iter.hasNextRow()) {
			Vettore<T> row = iter.nextRow();
			int c = 0;
			while(j < dimRighe) {
				Vettore<? extends Number> mCol = (Vettore<? extends Number>) m.col(j);
				retMat.aggiorna(i, c++, (T) row.moltiplicazione(mCol));
				j++;
			}
			c = 0;
			j = 0;
			i++;
		}
		return retMat;
	}	
}
