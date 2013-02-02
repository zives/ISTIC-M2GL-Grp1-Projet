package group1.project.synthlab.ihm.module.vco;

import group1.project.synthlab.module.VCOModule;

public class CVCOModule extends VCOModule implements ICVCOModule {
	protected IPVCOModule presentation;

	public CVCOModule() {
		super();
		this.presentation = new PVCOModule(this);
	}
	
	 
}
