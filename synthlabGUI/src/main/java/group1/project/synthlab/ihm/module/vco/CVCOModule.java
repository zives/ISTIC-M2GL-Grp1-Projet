package group1.project.synthlab.ihm.module.vco;

import java.awt.Component;

import group1.project.synthlab.module.vco.VCOModule;

public class CVCOModule extends VCOModule implements ICVCOModule {
	protected IPVCOModule presentation;

	public CVCOModule() {
		super();
		this.presentation = new PVCOModule(this);
	}

	public IPVCOModule getPresentation() {
		return presentation;
	}
	
	 
}
