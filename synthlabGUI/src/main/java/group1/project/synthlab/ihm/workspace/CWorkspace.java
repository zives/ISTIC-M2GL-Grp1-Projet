package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.ihm.cable.CCable;
import group1.project.synthlab.workspace.IWorkspace;
import group1.project.synthlab.workspace.Workspace;

public class CWorkspace extends Workspace implements ICWorkspace {
	protected CCable drawingCable;
	protected IPWorkspace presentation;

	public CWorkspace() {
		this.presentation = new PWorkspace(this);
	}

	public IPWorkspace getPresentation() {
		return presentation;
	}

	public CCable getDrawingCable() {
		return drawingCable;
	}

	public void setDrawingCable(CCable drawingCable) {

		if (drawingCable != null && this.drawingCable == null) {
			this.drawingCable = drawingCable;
			presentation.addCable(drawingCable.getPresentation());
		} 
		else
			this.drawingCable = null;

	}

	public boolean isDrawingCable() {
		return drawingCable != null;
	}

	public static ICWorkspace getInstance() {
		if (instance == null)
			instance = new CWorkspace();
		return (ICWorkspace) instance;
	}

}
