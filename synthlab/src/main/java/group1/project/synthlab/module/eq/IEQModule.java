package group1.project.synthlab.module.eq;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;

/**
 * Interface de creation du module de sortie.
 * 
 * @author Groupe 1
 * 
 */
public interface IEQModule extends IModule {

	
	/**
	 * @return la liste des frequences a attenuer ou amplifier
	 */
	public double[] getFrequencies();
	
	/**
	 * Pour un indice de frequence correspondant au tableau, attribue une attenuation en dB
	 * @param i l'indice
	 * @param dB l'attenuation
	 */
	public void setAttenuation(int i, double dB);
	
	/**
	 * Obtient l'attenuation en dB de la frequence d'indice
	 * @param i l'indice
	 * @return l'attenuation
	 */
	public double getAttenuation(int i);
	
	/**
	 * @return le port d'entree
	 */
	public IInPort getInPort();
		
	/**
	 * @return le port de sortie
	 */
	public IOutPort getOutPort();

}
