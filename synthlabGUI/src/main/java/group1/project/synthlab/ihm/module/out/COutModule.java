package group1.project.synthlab.ihm.module.out;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.module.out.OutModule;
import group1.project.synthlab.port.IPort;

public class COutModule extends OutModule implements ICOutModule {
	protected IPOutModule presentation;

	public COutModule(CFactory factory) {
		super(factory);
		this.presentation = new POutModule(this);
	}

	public IPOutModule getPresentation() {
		return presentation;
	}
	
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.reregisterModuleToCables();
		
	}

	@Override
	public void cableDisconnected(IPort port) {
		super.cableDisconnected(port);
		presentation.reregisterModuleToCables();
	}
	
	 
}
