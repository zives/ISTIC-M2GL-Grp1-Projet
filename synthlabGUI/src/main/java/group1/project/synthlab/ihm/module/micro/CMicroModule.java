package group1.project.synthlab.ihm.module.micro;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.micro.MicroModule;
import group1.project.synthlab.port.IPort;

/**
 * @author Groupe 1
 * Controleur pour le module micro
 */
public class CMicroModule extends MicroModule implements ICMicroModule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2971691029208156802L;
	protected IPMicroModule presentation;

	public CMicroModule(CFactory factory) {
		super(factory);
		this.presentation = new PMicroModule(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.micro.ICMicroModule#getPresentation()
	 */
	public IPMicroModule getPresentation() {
		return presentation;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.micro.MicroModule#cableConnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.micro.MicroModule#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableDisconnected(IPort port) {			
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.micro.MicroModule#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}
	 
}
