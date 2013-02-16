package group1.project.synthlab.ihm.module;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.module.Module;

/**
 * @author Groupe 1
 * Un controleur de module
 */
public abstract class CModule extends Module implements ICModule {

	private static final long serialVersionUID = 8080047203854707487L;

	public CModule(String name, CFactory factory) {
		super(name, factory);
	}

	

}
