package group1.project.synthlab.workspace;

import java.util.ArrayList;
import java.util.List;

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
	protected List<IModule> modules;

	public Workspace(Factory factory) {
		this.factory = factory;
		this.modules = new ArrayList<IModule>();
		synthesizer = JSyn.createSynthesizer();
		synthesizer.start();
				
	}

	public void addModule(IModule module) {
		modules.add(module);
		synthesizer.add(module.getCircuit());
	}

	public void removeModule(IModule module) {
		modules.remove(module);
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
