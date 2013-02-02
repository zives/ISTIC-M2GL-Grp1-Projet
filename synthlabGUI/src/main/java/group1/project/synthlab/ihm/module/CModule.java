package group1.project.synthlab.ihm.module;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.module.Module;

public abstract class CModule extends Module implements ICModule {

	public CModule(String name, CFactory factory) {
		super(name, factory);
	}



}
