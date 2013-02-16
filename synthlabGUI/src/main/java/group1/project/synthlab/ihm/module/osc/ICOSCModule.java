package group1.project.synthlab.ihm.module.osc;

import group1.project.synthlab.ihm.module.ICModule;
import group1.project.synthlab.module.osc.IOSCModule;

import java.util.List;

/**
 * @author Groupe 1
 * Interface pour le controleur du module OSC
 */
public interface ICOSCModule extends ICModule, IOSCModule {

	/**
	 * @return les valeurs à afficher
	 */
	public List<Double> getValuesToDraw();

	/**
	 * @param interval l'interval minimum pour mettre a jour la presentation
	 */
	public void setInterval(double interval);
	
	
	/**
	 * @return l'interval minimum pour mettre a jour la presentation
	 */
	public double getInterval() ;
}
