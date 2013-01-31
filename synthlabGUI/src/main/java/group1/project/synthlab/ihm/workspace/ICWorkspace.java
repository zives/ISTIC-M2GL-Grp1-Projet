package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.ihm.cable.CCable;
import group1.project.synthlab.workspace.IWorkspace;

public interface ICWorkspace extends IWorkspace {
	public CCable getDrawingCable();
	public void setDrawingCable(CCable drawingCable) ;	
	public boolean isDrawingCable();

}
