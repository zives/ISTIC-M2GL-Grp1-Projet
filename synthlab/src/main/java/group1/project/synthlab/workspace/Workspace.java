package group1.project.synthlab.workspace;

import group1.project.synthlab.exceptions.NameException;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceManager;

/**
 * @author Groupe 1 Le workspace (singleton)
 */
public class Workspace implements IWorkspace {

	/**
	 * L'instance singleton
	 */
	protected static IWorkspace instance;

	/**
	 * Le synthetiseur
	 */
	protected Synthesizer synthesizer;

	/**
	 * La factory
	 */
	protected Factory factory;

	/**
	 * Liste des modules
	 */
	protected List<IModule> modules;

	/**
	 * Si un micro est branch�
	 */
	protected boolean microphoneSupported;

	public Workspace(Factory factory) {

		// Initialisation
		this.factory = factory;
		this.modules = new CopyOnWriteArrayList<IModule>();
		synthesizer = JSyn.createSynthesizer();

		// Verification du microphone
		if (synthesizer.getAudioDeviceManager().getDefaultInputDeviceID() != -1) {
			System.out.println("Un microphone est connecte");
			synthesizer.start(41000, AudioDeviceManager.USE_DEFAULT_DEVICE, 2,
					AudioDeviceManager.USE_DEFAULT_DEVICE, 2);
			microphoneSupported = true;
		} else {
			System.out.println("Aucun microphone de connecte");
			synthesizer.start();
			microphoneSupported = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.workspace.IWorkspace#isMicrophoneSupported()
	 */
	public boolean isMicrophoneSupported() {
		return microphoneSupported;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * group1.project.synthlab.workspace.IWorkspace#addModule(group1.project
	 * .synthlab.module.IModule)
	 */
	public void addModule(IModule module) {
		for (IModule m : modules) {
			if (m.getFactory().equals(module.getName())) {
				try {
					throw new NameException(
							"Un module de nom identique existe d�j�");
				} catch (NameException e) {
					e.printStackTrace();
					return;
				}
			}

		}
		modules.add(module);
		synthesizer.add(module.getCircuit());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * group1.project.synthlab.workspace.IWorkspace#removeModule(group1.project
	 * .synthlab.module.IModule)
	 */
	public void removeModule(IModule module) {
		//Supprime les dependances
		module.destruct();
		synthesizer.remove(module.getCircuit());

		//Cherche si le workspace contient encore des modules du meme type
		boolean found = false;
		for (IModule m : modules) {
			if (m.getClass().isInstance(module.getClass())) {
				found = true;
				break;
			}
		}
		
		//Si non, remettre le compteur a zero
		if (!found)
			module.resetCounterInstance();
		
		//Supprimer enfin le module
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.workspace.IWorkspace#getSynthetizer()
	 */
	public Synthesizer getSynthetizer() {
		return synthesizer;
	}

}
