package group1.project.synthlab.ihm.module.piano;

import group1.project.synthlab.ihm.module.IPModule;

import java.awt.event.MouseEvent;

/**
 * @author Groupe 1
 * L'interface de presentation du module PIANO
 */
public interface IPPianoModule extends IPModule {
		
	/**
	 * Gere les evenements de la souris sur le piano
	 * @param me l'evenement de la souris
	 */
	public void mouseEvent(MouseEvent me);

}
