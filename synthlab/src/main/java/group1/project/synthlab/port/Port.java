package group1.project.synthlab.port;


public abstract class Port implements IPort {

	private String label;	
		
	public Port(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	

}
