package group1.project.synthlab.ihm.workspace;

import group1.project.synthlab.ihm.cable.IPCable;
import group1.project.synthlab.ihm.module.IPModule;
import group1.project.synthlab.ihm.module.IPModuleObserver;

import java.io.File;

import javax.swing.JLayeredPane;

/**
 * @author Groupe 1
 * Interface pour la presentation du WS
 */
public interface IPWorkspace extends IPModuleObserver {
	
	/**
	 * Initialise le workspace
	 */
	public void initialize();
	
	
	/**
	 * Ajout une presentation de cable au WS
	 * @param cable
	 */
	public void addCable(IPCable cable);
		
	/**
	 * Supprime une presentation de cable au WS
	 * @param cable
	 */
	public void removeCable (IPCable cable);
	
	
	/**
	 * Ajout une presentation de module au WS
	 * @param module
	 */
	public void addModule(IPModule module); 
	
	
	/**
	 * Supprime une presentation de module au WS
	 * @param module
	 */
	public void removeModule (IPModule module); 
	
	
	/**
	 * @return le plan de travail
	 */
	public JLayeredPane getWorkspacePanel();
	
	
	/**
	 * Ouvre une boite de dialogue pour la sauvegarde
	 * @return un fichier
	 */
	public File saveFileDialog();
	
	
	/**
	 * Affiche une erreur
	 * @param s texte de l'erreur
	 */
	public void showError(String s);
	
	
	/**
	 * Ouvre une boite de dialoguie pour ouvrir un fichier
	 * @return un fichier
	 */
	public File openFileDialog();
}
