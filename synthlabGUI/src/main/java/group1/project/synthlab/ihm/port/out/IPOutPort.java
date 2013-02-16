package group1.project.synthlab.ihm.port.out;

import group1.project.synthlab.ihm.port.IPPort;

/**
 * @author Groupe 1
 * Interface pour presentation de port de sortier
 */
public interface IPOutPort extends IPPort {
	/**
	 * Raffraichit la presentation du port
	 */
	public void refresh();
}
