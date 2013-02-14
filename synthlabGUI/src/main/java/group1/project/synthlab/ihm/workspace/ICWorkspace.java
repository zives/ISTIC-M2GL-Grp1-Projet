package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.ihm.cable.ICCable;
import group1.project.synthlab.workspace.IWorkspace;

public interface ICWorkspace extends IWorkspace {
	
	public ICCable getDrawingCable();
	public void setDrawingCable(ICCable drawingCable) ;	
	public boolean isDrawingCable();	
	public void removeCable(ICable cCable);
	
	public void addOneVCOModule();
	public void addOneOutModule();	
	public void addOneMultiplexer();
	public void addOneVCAModule();
	public void addOnePianoModule();
	public void addOneNoiseModule();
	public void addOneEGModule();
	public void addOneEQViewModule();
	public void addOneMicroModule();
	public void addOneEQModule();
	public void addOneOSCModule();
	public void addOneVCFLPModule();
	public void addOneVCFHPModule();
	public void addOneSequencer();
	
	public void saveConfiguration();
	public void loadConfiguration();
	
	public void clearAll();
	public void allModulesOn();
	public void allModulesOff();
	
	public void quitApp();
	public void giveFocus();
	

}
