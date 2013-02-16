package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.vco.VCOModule;
import group1.project.synthlab.port.IPort;

/**
 * @author Groupe 1
 * Controleur du module VCO
 */
public class CVCOModule extends VCOModule implements ICVCOModule {

	private static final long serialVersionUID = 7813118016635810873L;
	
	protected IPVCOModule presentation;

	public CVCOModule(CFactory factory) {
		super(factory);
		this.presentation = new PVCOModule(this);
		this.filterAmplitude.register(this.presentation);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.vco.ICVCOModule#getPresentation()
	 */
	public IPVCOModule getPresentation() {
		return presentation;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.vco.VCOModule#cableConnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.vco.VCOModule#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableDisconnected(IPort port) {		
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}


	public static void resetModuleCount() {
		moduleCount = 0;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.vco.VCOModule#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}
	
	
	
}
