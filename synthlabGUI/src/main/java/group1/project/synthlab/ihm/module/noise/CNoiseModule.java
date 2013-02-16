package group1.project.synthlab.ihm.module.noise;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.noise.NoiseModule;
import group1.project.synthlab.port.IPort;

/**
 * @author Groupe 1
 * Controleur du module Noise
 */
public class CNoiseModule extends NoiseModule implements ICNoiseModule {

	private static final long serialVersionUID = 9009352581640038205L;
	
	protected IPNoiseModule presentation;

	public CNoiseModule(CFactory factory) {
		super(factory);
		this.presentation = new PNoiseModule(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.noise.ICNoiseModule#getPresentation()
	 */
	public IPNoiseModule getPresentation() {
		return presentation;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.noise.NoiseModule#cableConnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.noise.NoiseModule#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableDisconnected(IPort port) {			
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.noise.NoiseModule#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}
	 
}
