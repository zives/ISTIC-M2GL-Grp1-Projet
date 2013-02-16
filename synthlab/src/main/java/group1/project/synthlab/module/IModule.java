package group1.project.synthlab.module;

import java.io.Serializable;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.port.IPortObserver;

import com.jsyn.unitgen.Circuit;

/**
 * Interface générale des modules
 * @author Groupe 1
 * 
 */
public interface IModule extends IPortObserver, IModuleObservable, Serializable {	
	/**
	 * Demarre le module
	 */
	public void start();
	/**
	 * Arrete le module
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
	 * change le nom du module (peu recommande)
	 */
	public void setName(String name);
	
	/**
	 * remet le compteur d'instances de classe a 0
	 */
	public void resetCounterInstance();
	
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
	
	/**
	 * redonne les valeurs des attributs aux composants internes JSYN et filtres personnalisÃ© (pour le chargement depuis un fichier)
	 */
	public void refresh();
}
