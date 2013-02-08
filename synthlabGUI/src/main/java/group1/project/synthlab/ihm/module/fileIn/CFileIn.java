package group1.project.synthlab.ihm.module.fileIn;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.fileIn.FileInModule;
import group1.project.synthlab.port.IPort;

public class CFileIn extends FileInModule implements ICFileIn{
	protected IPFileIn presentation;

	public CFileIn(Factory factory) {
		super(factory);
		this.presentation = new PFileIn(this);
	}

	public IPFileIn getPresentation() {
		return presentation;
	}
	
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
		
	}

	@Override
	public void cableDisconnected(IPort port) {			
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}
	
}
