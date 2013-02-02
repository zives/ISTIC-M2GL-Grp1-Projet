package group1.project.synthlab.ihm.module.out;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.module.out.OutModule;

public class COutModule extends OutModule implements ICOutModule {
	protected IPOutModule presentation;

	public COutModule(CFactory factory) {
		super(factory);
		this.presentation = new POutModule(this);
	}

	public IPOutModule getPresentation() {
		return presentation;
	}
	
	 
}
