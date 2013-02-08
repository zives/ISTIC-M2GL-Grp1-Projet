package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.ihm.cable.ICCable;
import group1.project.synthlab.workspace.IWorkspace;

public interface ICWorkspace extends IWorkspace {
	public ICCable getDrawingCable();
	public void setDrawingCable(ICCable drawingCable) ;	
	public boolean isDrawingCable();
	public void addOneVCOModule();
	public void addOneOutModule();
	public void removeCable(ICable cCable);
	public void addOneMultiplexer();
	public void addOneVCAModule();
	public void addOneVCOPianoModule();
	public void addFileInModule();

}
