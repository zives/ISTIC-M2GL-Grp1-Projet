package group1.project.synthlab.ihm.module.out;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.out.OutModule;
import group1.project.synthlab.port.IPort;

public class COutModule extends OutModule implements ICOutModule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3863164152877921361L;
	protected IPOutModule presentation;

	public COutModule(CFactory factory) {
		super(factory);
		this.presentation = new POutModule(this);
	}

	public IPOutModule getPresentation() {
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

	public static void resetModuleCount() {
		// TODO Auto-generated method stub
		moduleCount = 0;
	}
	
	
}
