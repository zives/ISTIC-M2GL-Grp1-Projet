package group1.project.synthlab.module.piano;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.vco.IVCOModule;
import group1.project.synthlab.port.out.IOutPort;

/**
 * Interface du module VCO
 * @author Groupe 1
 *
 */
public interface IPianoModule extends IModule {
	
	public enum NOTE {DO, DOd, RE, REd, MI, FA, FAd, SOL, SOLd, LA, LAd, SI};
	
	/**
	 * @return le port de sortie pour savoir s'il y a un signal
	 */
	public IOutPort getOutGate();
	
	/**
	 * 
	 * @return sortie de l'amplitude à brancher le plus souvent à l'entree fm d'un vco
	 */
	public IOutPort getOut();


	
	/**
	 * Attribue une tension de sortie liée à la note
	 */
	public void play(NOTE note, int octave);
}
