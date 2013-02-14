package group1.project.synthlab.ihm.module.vcf.lp;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.vcf.lp.VCFLPModule;
import group1.project.synthlab.port.IPort;

public class CVCFLPModule extends VCFLPModule implements ICVCFLPModule {

	protected IPVCFLPModule presentation;
	
	public CVCFLPModule(Factory factory) {
		super(factory);
		this.presentation = new PVCFLPModule(this);
	}

	public IPVCFLPModule getPresentation() {
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
	public String saveConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	 
}
