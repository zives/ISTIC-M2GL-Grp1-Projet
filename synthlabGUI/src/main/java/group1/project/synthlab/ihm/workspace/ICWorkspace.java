package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.ihm.cable.ICCable;
import group1.project.synthlab.workspace.IWorkspace;

public interface ICWorkspace extends IWorkspace {
	
	/** Retourne la presentation */
	public IPWorkspace getPresentation(); 
	
	/**
	 * @return le cable en cours de creation
	 */
	public ICCable getDrawingCable();
	
	
	/**
	 * @param drawingCable le cable en cours de creation
	 */
	public void setDrawingCable(ICCable drawingCable) ;	
	
	
	/**
	 * @return si il y a un cable en cours de creation
	 */
	public boolean isDrawingCable();
	
	
	/**
	 * Supprime un cable du WS
	 * @param cCable
	 */
	public void removeCable(ICable cCable);
	
	/**
	 * Ajoute un module VCO
	 */
	public void addOneVCOModule();
	
	
	/**
	 * Ajoute un module de sortie
	 */
	public void addOneOutModule();	
	
	/**
	 * Ajoute un multiplexer
	 */
	public void addOneMultiplexer();
	
	/**
	 * Ajoute un module VCA
	 */
	public void addOneVCAModule();
	
	/**
	 * Ajoute un piano
	 */
	public void addOnePianoModule();
	
	
	/**
	 * Ajoute un generateur de bruit
	 */
	public void addOneNoiseModule();
	
	
	/**
	 * Ajoute un module permettant de creer une enveloppe
	 */
	public void addOneEGModule();
	
	
	/**
	 * Ajoute un egaliseur graphique
	 */
	public void addOneEQViewModule();
	
	
	/**
	 * Ajoute un module prenant en charge un microphone
	 */
	public void addOneMicroModule();
	
	
	/**
	 * Ajoute un egaliseur
	 */
	public void addOneEQModule();
	
	
	/**
	 * Ajoute un oscilloscope
	 */
	public void addOneOSCModule();
	
	
	/**
	 * Ajoute un module VCF low pass
	 */
	public void addOneVCFLPModule();
	
	
	/**
	 * Ajoute un module VCF high pass
	 */
	public void addOneVCFHPModule();
	
	
	/**
	 * Ajoute un sequenceur
	 */
	public void addOneSequencer();
	
	/**
	 * Sauvegarde la configuration courante du WS
	 */
	public void saveConfiguration();
	
	/**
	 * Charge la configuration courante
	 */
	public void loadConfiguration();
	
	/**
	 * Efface tous les modules du WS
	 */
	public void clearAll();
	
	
	/**
	 * Demarre tous les modules present sur le WS
	 */
	public void allModulesOn();
	
	
	/**
	 * Arrete tous les modules present sur le WS
	 */
	public void allModulesOff();
	
	/**
	 * Quitte l'application
	 */
	public void quitApp();
	
	
	/**
	 * Donne le focus au WS
	 */
	public void giveFocus();
	

}
