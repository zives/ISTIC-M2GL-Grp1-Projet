package group1.project.synthlab.exceptions;
 
/**
 * Exception levee lorsqu'une action n'est pas autorisee sur un port deja utilise
 * @author Groupe 1
 *
 */
public class PortAlreadyUsed extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PortAlreadyUsed(String message) {
		super(message);
	}
}
