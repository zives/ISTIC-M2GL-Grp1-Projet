package group1.project.synthlab.module.piano;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.out.IOutPort;

/**
 * Interface du module VCO
 * @author Groupe 1
 *
 */
public interface IPianoModule extends IModule {
	
	/**
	 * @author groupe
	 * Les notes de musique du piano, le petit d correspond au diese
	 */
	public enum NOTE {DO, DOd, RE, REd, MI, FA, FAd, SOL, SOLd, LA, LAd, SI};
	
	/**
	 * @return le port de sortie pour savoir s'il y a un signal
	 */
	public IOutPort getOutGate();
	
	/**
	 * 
	 * @return sortie de l'amplitude a brancher le plus souvent  a  l'entree fm d'un vco
	 */
	public IOutPort getOut();

	
	/**
	 * Attribue une tension de sortie liee a la note au port out et une tension nominale au port gate
	 */
	public void play(NOTE note, int octave);
	
	/**
	 * Donne 0 à la tension au port gate 
	 */
	public void stopPlay();
}
