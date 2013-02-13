package group1.project.synthlab.workspace;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;

/**
 * @author Groupe 1
 * Le workspace (singleton)
 */
public class Workspace implements IWorkspace {
	protected static IWorkspace instance;
	protected Synthesizer synthesizer;
	protected Factory factory;
	protected List<IModule> modules;
	protected boolean microphoneSupported;
	

	public Workspace(Factory factory) {
		this.factory = factory;
		this.modules = new CopyOnWriteArrayList<IModule>();
		synthesizer = JSyn.createSynthesizer();
		
		
		if(synthesizer.getAudioDeviceManager().getDefaultInputDeviceID() != -1){
			System.out.println("Un microphone est connecté");
			synthesizer.start(41000, AudioDeviceManager.USE_DEFAULT_DEVICE, 2, AudioDeviceManager.USE_DEFAULT_DEVICE, 2);
			microphoneSupported = true;
		}
		else{
			System.out.println("Aucun microphone de connecté");
			synthesizer.start();
			microphoneSupported = false;
		}	
		
		
		
		 
		
	}

	public boolean isMicrophoneSupported() {
		return microphoneSupported;
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
	
	public enum LALA {
		TOTO, TATA
	}
	
		
	public static void main(String[] a) {
		System.err.println(LALA.class.getName());
	}

}
