package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.vco.VCOModule;
import group1.project.synthlab.port.IPort;

public class CVCOModule extends VCOModule implements ICVCOModule {
	protected IPVCOModule presentation;

	public CVCOModule(CFactory factory) {
		super(factory);
		this.presentation = new PVCOModule(this);
		this.filterAmplitude.register(this.presentation);
	}

	public IPVCOModule getPresentation() {
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
		String save = "<VCOModule>\n";
		save+="<CoarseAdjustment>"+this.coarseAdjustment+"</CoarseAdjustment>\n";
		save+="<FineAdjustment>"+this.fineAdjustment+"</FineAdjustment>\n";
		save+="<Location x=\""+presentation.getLocation().getX()+
				"\" y=\""+presentation.getLocation().getY()+"\" />\n";
		save+="</VCOModule>\n";
		return save;
	}

	public void updateCoarseAdjustment(int parseInt) {
		super.setCoarseAdjustment(parseInt);
		presentation.updateCoarseAdjustment(this.coarseAdjustment);
	}

	public void updateFineAdjustment(Double d) {
		super.setFineAdjustment(d);
		presentation.updateFineAdjustment(this.fineAdjustment);
		
	}

	public static void resetModuleCount() {
		moduleCount = 0;
	}
	
	
}
