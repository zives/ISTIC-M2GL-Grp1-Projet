package group1.project.synthlab.ihm.module.eq;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.eq.EQModule;
import group1.project.synthlab.port.IPort;

/**
 * @author Groupe 1
 * Controleur du module EQ
 */
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

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.eq.ICEQModule#getPresentation()
	 */
	public IPEQModule getPresentation() {
		return presentation;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eq.EQModule#cableConnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eq.EQModule#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableDisconnected(IPort port) {			
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eq.EQModule#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}
	
	


	 
}
