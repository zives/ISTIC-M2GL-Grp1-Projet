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
	public void addOnePianoModule();
	public void saveConfiguration();
	public void addFileInModule();
	public void addOneEGModule();
	public void addEQViewModule();
	public void addMicroModule();
	public void addEQModule();
	public void addOSCModule();
	public void addOneVCFLPModule();
	public void addOneVCFHPModule();
	public void quitApp();
	public void addOneSequencer();
	public void loadConfiguration();

}
