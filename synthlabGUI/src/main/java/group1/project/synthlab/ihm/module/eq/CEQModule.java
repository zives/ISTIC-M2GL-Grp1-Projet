package group1.project.synthlab.ihm.module.eq;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.eq.EQModule;
import group1.project.synthlab.port.IPort;

public class CEQModule extends EQModule implements ICEQModule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1869876693800502718L;
	protected IPEQModule presentation;

	public CEQModule(CFactory factory) {
		super(factory);
		this.presentation = new PEQModule(this);
	}

	public IPEQModule getPresentation() {
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
	
	@Override
	public String saveConfiguration() {
		// TODO Auto-generated method stub
		return "";
	}
	 
}
