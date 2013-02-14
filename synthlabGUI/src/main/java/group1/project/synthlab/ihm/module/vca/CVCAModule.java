package group1.project.synthlab.ihm.module.vca;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.vca.VCAModule;
import group1.project.synthlab.port.IPort;

public class CVCAModule extends VCAModule implements ICVCAModule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1271584611229817300L;
	protected IPVCAModule presentation;

	public CVCAModule(CFactory factory) {
		super(factory);
		this.presentation = new PVCAModule(this);
		//this.filterAmplitude.register(this.presentation);
	}

	public IPVCAModule getPresentation() {
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
