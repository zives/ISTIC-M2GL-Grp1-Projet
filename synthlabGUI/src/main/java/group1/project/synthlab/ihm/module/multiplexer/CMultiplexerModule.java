package group1.project.synthlab.ihm.module.multiplexer;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.multiplexer.MultiplexerModule;
import group1.project.synthlab.port.IPort;

public class CMultiplexerModule extends MultiplexerModule implements ICMultiplexerModule {
	protected IPMultiplexerModule presentation;

	public CMultiplexerModule(CFactory factory) {
		super(factory);
		this.presentation = new PMultiplexerModule(this);
	}

	public IPMultiplexerModule getPresentation() {
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
