package group1.project.synthlab.ihm.module.vcf.hp;

import com.jsyn.unitgen.FilterHighPass;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.vcf.lp.VCFLPModule;
import group1.project.synthlab.port.IPort;

public class CVCFHPModule extends VCFLPModule implements ICVCFHPModule {

	protected IPVCFHPModule presentation;
	
	public CVCFHPModule(Factory factory) {
		super(factory);
		this.presentation = new PVCFHPModule(this);
	}

	public IPVCFHPModule getPresentation() {
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

	@Override
	public FilterHighPass getFilter() {
		// TODO Auto-generated method stub
		return null;
	}
	

	 
}
