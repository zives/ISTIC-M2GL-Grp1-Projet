package group1.project.synthlab.ihm.module.out;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.out.OutModule;
import group1.project.synthlab.port.IPort;

/**
 * @author Groupe 1
 * Controleur du module de sortie
 */
public class COutModule extends OutModule implements ICOutModule {

	private static final long serialVersionUID = 3863164152877921361L;
	protected IPOutModule presentation;

	public COutModule(CFactory factory) {
		super(factory);
		this.presentation = new POutModule(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.out.ICOutModule#getPresentation()
	 */
	public IPOutModule getPresentation() {
		return presentation;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.out.OutModule#cableConnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.out.OutModule#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableDisconnected(IPort port) {			
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}

	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.out.OutModule#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}
	
	
}
