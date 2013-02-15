package group1.project.synthlab.module.osc;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.unitExtension.filter.filterInterception.IFilterInterceptionObserver;

/**
 * Interface du module oscilloscope
 * @author Groupe 1
 * 
 */
public interface IOSCModule extends IModule, IFilterInterceptionObserver {
	
	/**
	 * @return le port d'entree du module
	 */
	public IInPort getInPort();
	
	/**
	 * @return le port de sortie du module
	 */
	public IOutPort getOutPort();
	
	/**
	 * @return recupere une valeur du buffer du module et la supprime
	 */
	public double poll();
		
	/**
	 *  vide le buffer interne
	 */
	public void clearBuffer();

	/**
	 * @return derniere date du synthetiseur a laquelle le module a intercepte les valeurs
	 */
	public double getLastTime();

}
