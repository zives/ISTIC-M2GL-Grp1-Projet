package group1.project.synthlab.port;

import group1.project.synthlab.cable.ICable;


public abstract class Port implements IPort {

	protected ICable cable;
	private String label;	
		
	public ICable getCable() {
		return cable;
	}

	public void setCable(ICable cable) {
		this.cable = cable;
	}
	
	public Port(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return label;
	}
	
	public boolean isUsed() {
		return cable != null;
	}
	

}
