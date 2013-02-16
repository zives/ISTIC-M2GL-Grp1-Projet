package group1.project.synthlab.ihm.module.eg;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.eg.EGModule;
import group1.project.synthlab.port.IPort;

/**
 * @author Groupe 1
 * Controleur du module EG
 */
public class CEGModule extends EGModule implements ICEGModule {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2429714010599959397L;
	protected IPEGModule presentation;

	public CEGModule(CFactory factory) {
		super(factory);
		this.presentation = new PEGModule(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.eg.ICEGModule#getPresentation()
	 */
	public IPEGModule getPresentation() {
		return presentation;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eg.EGModule#cableConnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eg.EGModule#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableDisconnected(IPort port) {		
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eg.EGModule#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}
	
	
}
