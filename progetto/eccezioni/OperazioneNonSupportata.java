package progetto.eccezioni;

/**
 *
 * @author Marco Rotiroti
 *
 */
public class OperazioneNonSupportata extends Exception {

	private static final long serialVersionUID = 8541916433159337126L;

	public OperazioneNonSupportata() {
		super();
	}

	public OperazioneNonSupportata(String arg0) {
		super(arg0);
	}

	public OperazioneNonSupportata(Throwable arg0) {
		super(arg0);
	}

	public OperazioneNonSupportata(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
