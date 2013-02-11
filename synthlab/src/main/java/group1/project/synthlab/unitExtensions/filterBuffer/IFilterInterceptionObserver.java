package group1.project.synthlab.unitExtensions.filterBuffer;

import group1.project.synthlab.exceptions.BufferTooBig;

import java.util.List;

public interface IFilterInterceptionObserver {
	/**
	 * Resultat du snapshot
	 * @param buffer le buffer contenant les valeurs interceptees
	 * @param time dernier temps
	 * @throws BufferTooBig 
	 */
	public void interceptionResult(final List<Double> buffer, double time) throws BufferTooBig;
}
