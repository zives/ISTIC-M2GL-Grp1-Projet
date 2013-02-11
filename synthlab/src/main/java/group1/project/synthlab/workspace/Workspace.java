package group1.project.synthlab.workspace;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;
import com.softsynth.jsyn.AudioDevice;

/**
 * @author Groupe 1
 *
 */
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
		/*
		try{
			synthesizer.start(41000, AudioDeviceManager.USE_DEFAULT_DEVICE, 2, AudioDeviceManager.USE_DEFAULT_DEVICE, 2);
		}
		catch(Exception e){
			System.out.println("exception");
			synthesizer.start();
		}*/
	
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.workspace.IWorkspace#addModule(group1.project.synthlab.module.IModule)
	 */
	public void addModule(IModule module) {
		modules.add(module);
		synthesizer.add(module.getCircuit());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.workspace.IWorkspace#removeModule(group1.project.synthlab.module.IModule)
	 */
	public void removeModule(IModule module) {	
		module.destruct();
		synthesizer.remove(module.getCircuit());
		modules.remove(module);
		
	}

	/**
	 * @return l'instance du WS
	 */
	public static IWorkspace getInstance() {
		if (instance == null) {
			Factory f = new Factory();
			instance = f.createWorkSpace();

		}
		return instance;
	}

	@Override
	public Synthesizer getSynthetizer() {
		return synthesizer;
	}

}
