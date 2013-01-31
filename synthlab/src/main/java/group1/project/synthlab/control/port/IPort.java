package group1.project.synthlab.control.port;

import group1.project.synthlab.cable.ICable;

public interface IPort {
	public String getLabel();
	public ICable getCable();
	public void setCable(ICable cable);
	public boolean isUsed();
}
