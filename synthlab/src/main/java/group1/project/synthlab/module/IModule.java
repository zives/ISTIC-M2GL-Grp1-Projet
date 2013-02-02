package group1.project.synthlab.module;

import com.jsyn.unitgen.Circuit;


public interface IModule {	
	/**
	 * Démarre le circuit
	 */
	public void start();
	/**
	 * Arrête le circuit
	 */
	public void stop();
	
	/**
	 * @return true si le circuit est en marche, faux sinon (le son est coupé)
	 */
	public boolean isStarted();
	
	/**
	 * 
	 * @return le nom du module
	 */
	public String getName();
	
	
	/**
	 * @return le circuit JSyn interne au module
	 */
	public Circuit getCircuit(); 
}
