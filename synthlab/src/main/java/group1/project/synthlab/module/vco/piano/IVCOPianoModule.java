package group1.project.synthlab.module.vco.piano;

import group1.project.synthlab.module.vco.IVCOModule;
import group1.project.synthlab.port.out.IOutPort;

/**
 * Interface du module VCO
 * @author Groupe 1
 *
 */
public interface IVCOPianoModule extends IVCOModule {
	
	/**
	 * @return le port de sortie pour savoir s'il y a un signal
	 */
	public IOutPort getOutSignalOn();

	

}
