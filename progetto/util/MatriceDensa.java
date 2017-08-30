/**
 * 
 */
package progetto.util;

import java.util.Iterator;
import progetto.eccezioni.OperazioneNonSupportata;

/**
 *
 * @author Marco Rotiroti
 * 
 */
public class MatriceDensa<T> extends Matrice<T> {

	private Vettore<Vettore<T>> matrix = null;
	private int dimRighe = 0;
	private int dimColonne = 0;
	private T emptyEl = null;
	
	public MatriceDensa(int righe, int colonne, T empty) {
		Vettore<Vettore<T>> rows = new Vettore<Vettore<T>>(righe);
		for(int i = 0; i < righe; i++) {
			Vettore<T> vec = new Vettore<T>(colonne);
			for (int j = 0; j < colonne; j++) {
				try {
					vec.agg(null);
				} catch (OperazioneNonSupportata e) {
					e.printStackTrace();
				}
			}
				
			try {
				rows.agg(vec); 
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (OperazioneNonSupportata e) {
				e.printStackTrace();
			}
		}
		
		dimRighe = righe;
		dimColonne = colonne;
		matrix = rows;
		emptyEl = empty;
	}
	
	public int dim() {
		return dimRighe*dimColonne;
	}

	public String toString() {
		String output = "";
		IteratoreMatrice<T> iter = new IteratoreMatrice<T>();
		
		while(iter.hasNextRow()) {
			Vettore<T> v = iter.nextRow();
			output += v.toString();
			output += "\n";
		}
		return output;
	}

	/* (non-Javadoc)
	 * @see progetto.util.Matrice#aggiorna(int, int, java.lang.Object)
	 */
	@Override
	public void aggiorna(int n, int m, T t) {
		matrix.at(n).aggiorna(m, t);
	}
	
	public void aggiorna(int n, T t) {
		if(n >= dimRighe)
			throw new IllegalArgumentException();
		
		aggiorna(n, 0, t);
	}

	/* (non-Javadoc)
	 * @see progetto.util.Matrice#at(int, int)
	 */
	@Override
	public T at(int n, int m) {
		return matrix.at(n).at(m);
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
				vec.agg(matrix.at(r).at(c));
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
				vec.agg(matrix.at(r).at(c));
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			} catch (OperazioneNonSupportata e) {
				e.printStackTrace();
			}
		}
		return vec;
	}

	/* (non-Javadoc)
	 * @see progetto.util.Matrice#colonna(int)
	 */
	@Override
	public Vettore<T> col(int n) {
		Vettore<T> vec = new Vettore<T>(dimRighe);
		for(int i = 0; i < dimRighe; i++) {
			try {
				vec.agg(matrix.at(i).at(n));
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			} catch (OperazioneNonSupportata e) {
				e.printStackTrace();
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
		return matrix.at(n);
	}

	/* (non-Javadoc)
	 * @see progetto.util.Collezione#agg(java.lang.Object)
	 */
	@Override
	public void agg(T c) throws OperazioneNonSupportata {
		IteratoreMatrice<T> iter = new IteratoreMatrice<T>();
		while(iter.hasNext() && (iter.next() != null)) {
			System.out.println("indici: " + iter.getR() + ", " + iter.getC());
			aggiorna(iter.getR(), iter.getC(), c);
		}
	}
	
	
	private class IteratoreMatrice<X extends T> implements Iterator<T> {
		
		private int posRighe = 0;
		private int posColonne = 0;
		
		public boolean hasNext() {
			return (posRighe < dimRighe && posColonne < dimColonne);
		}
		
		public T next() {
			T el = matrix.at(posRighe).at(posColonne++);
			
			if(posColonne >= dimColonne) {
				if(posRighe < dimRighe)
					posColonne = 0;
				posRighe++;
			}
			
			return el;
		}
		
		public void remove() {
			if(posRighe < dimRighe && posColonne < dimColonne)
				matrix.at(posRighe).aggiorna(posColonne, null);
		}
		
		public boolean hasNextRow() {
			return (posRighe < dimRighe);
		}
		
		public Vettore<T> nextRow() {
			return matrix.at(posRighe++);
		}
		
		public int getR() {
			return posRighe;
		}
		
		public int getC() {
			return posColonne;
		}
	}

	/* (non-Javadoc)
	 * @see progetto.util.Collezione#iteratore()
	 */
	@Override
	public Iterator<T> iteratore() {
		return new IteratoreMatrice<T>();
	}
	
	public <S> MatriceDensa<S> fai(Blocco<T, S> b) {
		Iterator<T> it = iteratore();
		MatriceDensa<S> coll = new MatriceDensa<S>(dimRighe, dimColonne, (S)emptyEl);
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

	/* (non-Javadoc)
	 * @see progetto.util.Collezione#rifiuta(progetto.util.BloccoBool)
	 */
	@Override
	public MatriceDensa<T> rifiuta(BloccoBool<T> b) {
		IteratoreMatrice<T> it = new IteratoreMatrice();
		MatriceDensa<T> new_class = new MatriceDensa<T>(dimRighe, dimColonne, emptyEl);
		T elem;
		
		while(it.hasNext()) {
			int r = it.getR();
			int c = it.getC();
			elem = it.next();
			if(elem != null && !(b.valuta(elem))) {
				new_class.aggiorna(r, c, elem);
			} else {
				new_class.aggiorna(r, c, emptyEl);
			}
		}
		return new_class;
	}

	/* (non-Javadoc)
	 * @see progetto.util.Collezione#seleziona(progetto.util.BloccoBool)
	 */
	@Override
	public MatriceDensa<T> seleziona(BloccoBool<T> b) {
		IteratoreMatrice<T> it = new IteratoreMatrice();
		MatriceDensa<T> new_class = new MatriceDensa<T>(dimRighe, dimColonne, emptyEl);
		T elem;
		
		while(it.hasNext()) {
			int r = it.getR();
			int c = it.getC();
			elem = it.next();
			if(elem != null && b.valuta(elem)) {
				new_class.aggiorna(r, c, elem);
			} else {
				new_class.aggiorna(r, c, emptyEl);
			}
		}
		return new_class;
	}
	
	public MatriceDensa<T> moltiplicazione(Matrice<T> m) {
		if(dimR() != m.dimC())
			throw new IllegalArgumentException();
		
		MatriceDensa<T> retMat	= new MatriceDensa<T>(dimRighe, m.dimC(), emptyEl);
		
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
