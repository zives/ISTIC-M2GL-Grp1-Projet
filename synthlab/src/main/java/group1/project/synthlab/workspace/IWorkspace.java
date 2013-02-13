package group1.project.synthlab.workspace;

import com.jsyn.Synthesizer;

import group1.project.synthlab.module.IModule;

/**
 * @author Groupe 1
 * Le workspace qui contient les differents modules et cables
 */
public interface IWorkspace {
	/**
	 * @param module le module a ajouter
	 */
	public void addModule(IModule module);
	/**
	 * @param module le module a supprimer
	 */
	public void removeModule(IModule module);
	
	public Synthesizer getSynthetizer();
	
	/**
	 * @return si un microphone a �t� branch� lors de la creation du workspace
	 */
	public boolean isMicrophoneSupported();
	

}
