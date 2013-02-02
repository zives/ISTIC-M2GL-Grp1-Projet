package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.ihm.cable.CCable;
import group1.project.synthlab.ihm.cable.ICCable;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.workspace.Workspace;

public class CWorkspace extends Workspace implements ICWorkspace {
	protected ICCable drawingCable;
	protected IPWorkspace presentation;

	public CWorkspace(CFactory factory) {
		super(factory);
		this.presentation = new PWorkspace(this);
	}

	public IPWorkspace getPresentation() {
		return presentation;
	}

	public ICCable getDrawingCable() {
		return drawingCable;
	}

	public void setDrawingCable(ICCable drawingCable) {

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
		if (instance == null)  {
			CFactory factory = new CFactory();
			instance = factory.createWorkSpace();
		}
		return (ICWorkspace) instance;
	}

}
