package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.module.vco.VCOModule;
import group1.project.synthlab.port.IPort;

public class CVCOModule extends VCOModule implements ICVCOModule {
	protected IPVCOModule presentation;

	public CVCOModule(CFactory factory) {
		super(factory);
		this.presentation = new PVCOModule(this);
	}

	public IPVCOModule getPresentation() {
		return presentation;
	}

	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.reregisterCables();
		presentation.setSlidersEnabled(false);
	}

	@Override
	public void cableDisconnected(IPort port) {
		super.cableDisconnected(port);
		presentation.reregisterCables();
		presentation.setSlidersEnabled(true);
	}
	
	
	
	 
}
