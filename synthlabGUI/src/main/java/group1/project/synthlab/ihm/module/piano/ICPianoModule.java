package group1.project.synthlab.ihm.module.piano;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.piano.IPianoModule;

/**
 * @author Groupe 1
 * Interface de controle pour le module piano
 */
public interface ICPianoModule extends IPianoModule, ICModule {
	
	/**
	 * Joue une note par l'appui d'une touche venant de la presentation
	 * @param note l'index de la note (max 7 pour les normales, 5 pour les dieses)
	 * @param octave l'octave de la note
	 * @param sharp si la note est diese
	 */
	public void playFromPresentation(int note, int octave, boolean sharp);
	
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.piano.IPianoModule#stopPlay()
	 */
	public void stopPlay();
	
	
	/**
	 * Change la valeur de debut d'octave (la presentation ne pouvant pas afficher la gamme complete de tous les octaves a la fois)
	 * @param value
	 */
	public void changeOctaveStart(int value);
	
	/**
	 * @return l'indice de debut d'octave
	 */
	public int getOctaveStart();
}
