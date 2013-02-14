package group1.project.synthlab.ihm.module.noise;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.noise.NoiseModule;
import group1.project.synthlab.port.IPort;

public class CNoiseModule extends NoiseModule implements ICNoiseModule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9009352581640038205L;
	protected IPNoiseModule presentation;

	public CNoiseModule(CFactory factory) {
		super(factory);
		this.presentation = new PNoiseModule(this);
	}

	public IPNoiseModule getPresentation() {
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
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}
	 
}
