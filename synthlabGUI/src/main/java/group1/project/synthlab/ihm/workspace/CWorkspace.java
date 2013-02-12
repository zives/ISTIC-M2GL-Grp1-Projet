package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.ihm.cable.ICCable;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.workspace.Workspace;

public class CWorkspace extends Workspace implements ICWorkspace {
	protected ICCable drawingCable;
	protected IPWorkspace presentation;

	public CWorkspace(CFactory factory) {
		super(factory);
		this.presentation = new PWorkspace(this);
		addModule(factory.createOutModule());
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
	
	public void removeCable(ICable cCable) {
		presentation.removeCable(((ICCable)cCable).getPresentation());		
	}

	@Override
	public void addModule(IModule module) {
		super.addModule(module);
		presentation.addModule(((ICModule) module).getPresentation());
	}

	@Override
	public void removeModule(IModule module) {
		presentation.removeModule(((ICModule) module).getPresentation());
		super.removeModule(module);		
	}

	public void addOneVCOModule() {
		addModule(factory.createVCOModule());
		
	}

	public void addOneOutModule() {
		addModule(factory.createOutModule());
		
	}
	
	public void addOneMultiplexer() {
		addModule(factory.createMultiplexerModule());
	}
	
	@Override
	public void addOneSequencer() {
		addModule(factory.createSequencerModule());
	}

	public void addOneVCAModule() {
		addModule(factory.createVCAModule());
	}

	@Override
	public void addOneVCFModule() {
		addModule(factory.createVCFModule());	
	}
	
	@Override
	public void addOneVCOPianoModule() {
		addModule(factory.createVCOPianoModule());
		
	}

	@Override
	public void saveConfiguration() {
		// TODO Auto-generated method stub
		//on demande le nom de fichier
		String name = presentation.askFileName();
		if(name!=null){
			//bouton cancel non appuyé
			if(name.length()>0){
				//sauvegarde
				//Workspace
				
				//cable
			}
			else{
				presentation.showError("Erreur lors de la sauvegarde : le nom de fichier doit contenir au moins un caractère");
			}
		}
	}
	public void addFileInModule() {
		addModule(factory.createFileInModule());
		
	}

	@Override
	public void addOneEGModule() {
		addModule(factory.createEGModule());
		
	}

	@Override
	public void addEQViewModule() {
		addModule(factory.createEQViewModule());
		
	}

	@Override
	public void addMicroModule() {
		addModule(factory.createMicroModule());
		
	}

	@Override
	public void addEQModule() {
		addModule(factory.createEQModule());
		
	}


	@Override
	public void addOSCModule() {
		addModule(factory.createOSCModule());
		
	}

	@Override
	public void quitApp() {
		System.exit(0);
		
	}

	

	
	
	

}
