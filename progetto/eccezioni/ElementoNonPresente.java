package progetto.eccezioni;

/**
 *
 * @author Marco Rotiroti
 *
 */
public class ElementoNonPresente extends Exception {

	private static final long serialVersionUID = 3241086989967790434L;

	public ElementoNonPresente() {
		super();
	}

	public ElementoNonPresente(String arg0) {
		super(arg0);
	}

	public ElementoNonPresente(Throwable arg0) {
		super(arg0);
	}

	public ElementoNonPresente(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
