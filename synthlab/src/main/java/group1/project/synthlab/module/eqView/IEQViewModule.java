package group1.project.synthlab.module.eqView;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;

/**
 * Interface du visualisateur graphique par fréquences 
 * @author Groupe 1
 * 
 */
public interface IEQViewModule extends IModule {

	/**
	 * Obtient l'amplitude max sur une frequence d'indice i
	 * @param i indice du tableau de la gamme de frequence
	 * @return l'amplitude maximal du signal vers la frequence d'indice id
	 */
	public double getMax(int i);			

	/**
	 * @return la gamme de frequence surveillee
	 */
	public double[] getFrequencies();

	/**
	 * @return le port d'entree
	 */
	public IInPort getInPort();
	/**
	 * @return le port de sortie
	 */
	public IOutPort getOutPort();

}
