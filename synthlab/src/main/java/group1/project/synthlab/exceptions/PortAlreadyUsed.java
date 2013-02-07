package group1.project.synthlab.exceptions;
 
public class PortAlreadyUsed extends Exception {
	private static final long serialVersionUID = 1L;
	
	public PortAlreadyUsed(String message) {
		super(message);
	}
}
