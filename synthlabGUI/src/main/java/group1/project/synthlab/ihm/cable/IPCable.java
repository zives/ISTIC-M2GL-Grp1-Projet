package group1.project.synthlab.ihm.cable;

import group1.project.synthlab.ihm.port.IPPort;

/**
 * @author Groupe 1
 * Interface de presentation du cable
 */
public interface IPCable {
	/**
	 * Defini la position de l'extremite 1 du cable
	 * @param x
	 * @param y
	 */
	public void setP1(int x, int y);

	/**
	 * Defini la position de l'extremite1 du cable
	 * @param presentation
	 */
	public void setP1(IPPort presentation);

	/**
	 * Defini la position du point 2 du cable
	 * @param x
	 * @param y
	 */
	public void setP2(int x, int y);

	/**
	 * Defini la position du point 2 du cable
	 * @param presentation
	 */
	public void setP2(IPPort presentation);

	/**
	 * Libere les ressources graphiques avant destruction du controleur (observers etc)
	 */
	public void destruct();
	
	/** Change la couleur du cable */
	public void nextColor();
	
	/** Obtient la couleur du cable */
	public int getColorPosition();
	
	/** Obtient la couleur du cable */
	public void setColorPosition(int pos);
	

	
}
