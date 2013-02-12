package group1.project.synthlab.ihm.module.micro;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.micro.MicroModule;
import group1.project.synthlab.port.IPort;

public class CMicroModule extends MicroModule implements ICMicroModule {
	protected IPMicroModule presentation;

	public CMicroModule(CFactory factory) {
		super(factory);
		this.presentation = new PMicroModule(this);
	}

	public IPMicroModule getPresentation() {
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
