package group1.project.synthlab.exceptions;

/**
 * Exception levee lorsqu'un buffer explicte d'un module depasse une certaine quantitee de memoire
 * @author Groupe 1
 *
 */
public class BufferTooBig extends Exception {

	private static final long serialVersionUID = 1L;

	public BufferTooBig(String message) {
		super(message);
	}

}
