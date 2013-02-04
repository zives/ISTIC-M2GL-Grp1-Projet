package group1.project.synthlab.module;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.port.IPortObserver;

import com.jsyn.unitgen.Circuit;

/**
 * Interface du module 
 * @author Groupe 1
 * 
 */
public interface IModule extends IPortObserver {	
	/**
	 * Demarre le circuit
	 */
	public void start();
	/**
	 * Arrete le circuit
	 */
	public void stop();
	
	/**
	 * @return true si le circuit est en marche, faux sinon (le son est coupe)
	 */
	public boolean isStarted();
	
	/**
	 * @return le nom du module
	 */
	public String getName();
	
	/**
	 * @return le circuit JSyn interne au module
	 */
	public Circuit getCircuit(); 
	
	/**
	 * @return la factory
	 */
	public Factory getFactory();

	/**
	 * @param factory une factory
	 */
	public void setFactory(Factory factory);
	
	/**
	 * Detruit les dependences du module
	 */
	public void destruct();
}
