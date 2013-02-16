package group1.project.synthlab.ihm.module.vca;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.vca.VCAModule;
import group1.project.synthlab.port.IPort;

/**
 * @author Groupe 1
 * Controleur pour le module VCA
 */
public class CVCAModule extends VCAModule implements ICVCAModule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1271584611229817300L;
	protected IPVCAModule presentation;

	public CVCAModule(CFactory factory) {
		super(factory);
		this.presentation = new PVCAModule(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.vca.ICVCAModule#getPresentation()
	 */
	public IPVCAModule getPresentation() {
		return presentation;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.vca.VCAModule#cableConnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.vca.VCAModule#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableDisconnected(IPort port) {		
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.vca.VCAModule#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}
	
	 
}
