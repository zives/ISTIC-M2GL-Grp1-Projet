package group1.project.synthlab.ihm.module.vcf;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.vcf.VCFModule;
import group1.project.synthlab.port.IPort;

public class CVCFModule extends VCFModule implements ICVCFModule {

	protected IPVCFModule presentation;
	
	public CVCFModule(Factory factory) {
		super(factory);
		this.presentation = new PVCFModule(this);
	}

	public IPVCFModule getPresentation() {
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
	

	 
}
