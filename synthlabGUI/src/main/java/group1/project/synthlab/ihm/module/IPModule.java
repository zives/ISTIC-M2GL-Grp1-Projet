package group1.project.synthlab.ihm.module;

import java.awt.Point;
import java.io.Serializable;

public interface IPModule extends IPModuleObservable, Serializable {

	/**
	 * Indique a la presentation d'afficher que le module est en marche
	 */
	public void start();
	
	
	/**
	 * Indique a la presentation d'afficher que le module est arrete
	 */
	public void stop();
	
	
	/**
	 * @return retourne la location du module sur le WS
	 */
	public Point getLocation();
	
	
	/**
	 * Indique a la presentation de rafraichir ses composants par rapports aux valeurs definies dans le controlleur
	 */
	public void updatePresentation();
	
	
	/**
	 * Demande a la presentation de deplacer le module a l'endroit indique
	 * @param x
	 * @param y
	 */
	public void updateLocation(int x, int y);
}
