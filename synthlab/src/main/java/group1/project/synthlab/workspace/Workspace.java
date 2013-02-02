package group1.project.synthlab.workspace;

import javax.management.InstanceAlreadyExistsException;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.out.OutModule;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;

public class Workspace implements IWorkspace {
	protected static IWorkspace instance;
	protected Synthesizer synthesizer;
	
	public Workspace(){
		synthesizer = JSyn.createSynthesizer();
		synthesizer.start();
		addModule(new OutModule());
	}
	
	public void addModule(IModule module) {
		synthesizer.add(module.getCircuit());
	}

	public void removeModule(IModule module) {
		synthesizer.remove(module.getCircuit());
	}

	public static IWorkspace getInstance() {
		if (instance == null)
			instance = new Workspace();
		return instance;
	}

}
