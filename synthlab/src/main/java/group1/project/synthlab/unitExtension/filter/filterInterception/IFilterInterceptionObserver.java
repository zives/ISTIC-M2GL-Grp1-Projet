package group1.project.synthlab.unitExtension.filter.filterInterception;

import group1.project.synthlab.exceptions.BufferTooBig;

import java.util.List;

/**
 * @author Groupe 1
 * Un observateur
 */
public interface IFilterInterceptionObserver {
	/**
	 * Resultat du snapshot
	 * @param buffer le buffer contenant les valeurs interceptees
	 * @param time dernier temps
	 * @throws BufferTooBig 
	 */
	public void interceptionResult(final List<Double> buffer, double time) throws BufferTooBig;
}
