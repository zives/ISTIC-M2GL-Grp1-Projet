package group1.project.synthlab.workspace;

import javax.management.InstanceAlreadyExistsException;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.out.OutModule;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;

public class Workspace implements IWorkspace {
	protected static IWorkspace instance;
	protected Synthesizer synthesizer;
	protected Factory factory;

	public Workspace(Factory factory) {
		this.factory = factory;
		synthesizer = JSyn.createSynthesizer();
		synthesizer.start();
		addModule(factory.createOutModule());
	}

	public void addModule(IModule module) {
		synthesizer.add(module.getCircuit());
	}

	public void removeModule(IModule module) {
		synthesizer.remove(module.getCircuit());
	}

	public static IWorkspace getInstance() {
		if (instance == null) {
			Factory f = new Factory();
			instance = f.createWorkSpace();

		}
		return instance;
	}

}
