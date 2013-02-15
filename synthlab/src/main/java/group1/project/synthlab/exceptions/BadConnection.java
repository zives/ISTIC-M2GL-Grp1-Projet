package group1.project.synthlab.exceptions;

/**
 * Exception levee lors d'une connexion de cable non autorisee
 * @author Groupe 1
 *
 */
public class BadConnection extends Exception {

	private static final long serialVersionUID = 1L;

	public BadConnection(String message) {
		super(message);
	}

}
