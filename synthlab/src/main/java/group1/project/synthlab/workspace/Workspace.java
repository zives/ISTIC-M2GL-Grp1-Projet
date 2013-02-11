package group1.project.synthlab.workspace;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;

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
		
		boolean microphoneConnected = false;
		
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		for (Mixer.Info info: mixerInfos){
			Mixer m = AudioSystem.getMixer(info);
			Line.Info[] lineInfos = m.getSourceLineInfo();
			for (Line.Info lineInfo:lineInfos){
				if(lineInfo.toString().contains("source port"))
				microphoneConnected = true;
			}
		}
		
		if(microphoneConnected){
			System.out.println("Un microphone est connect�");
			synthesizer.start(41000, AudioDeviceManager.USE_DEFAULT_DEVICE, 2, AudioDeviceManager.USE_DEFAULT_DEVICE, 2);
		}
		else{
			System.out.println("Aucun microphone de connect�");
			synthesizer.start();
		}	
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
