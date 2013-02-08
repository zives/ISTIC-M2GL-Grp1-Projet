package group1.project.synthlab.ihm.module.eg;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.eg.EGModule;
import group1.project.synthlab.port.IPort;

public class CEGModule extends EGModule implements ICEGModule {
	protected IPEGModule presentation;

	public CEGModule(CFactory factory) {
		super(factory);
		this.presentation = new PEGModule(this);
		//this.filterAmplitude.register(this.presentation);
	}

	public IPEGModule getPresentation() {
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
