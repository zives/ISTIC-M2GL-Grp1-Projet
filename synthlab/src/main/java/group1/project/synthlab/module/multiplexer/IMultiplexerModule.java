package group1.project.synthlab.module.multiplexer;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;

/**
 * Interface de creation du module de multiplexage (réplicateur, mixeur).
 * @author Groupe 1
 *
 */
public interface IMultiplexerModule extends IModule {
	
	public enum Distribution {
		NORMAL, DISTRIBUTED
	}

	/**
	 * Obtient un port d'entree
	 * @param i l'indice du port d'entree
	 * @return le port d'entree
	 */
	public IInPort getInPort(int i) ;

	/**
	 * Obtient un port de sortie
	 * @param i l'indice du port de sortie
	 * @return le port de sortie
	 */
	public IOutPort getOutPort(int i);
	
	/**
	 * Definit une attenuation sur un port donne
	 * @param db attenuation en dB
	 * @param port indice du port d'entree
	 */
	public void setAttenuation(double db, int port);
	
	
	/**
	 * Obtient l'attenuation du port d'entre
	 * @param port l'indice du port d'entree
	 * @return l'attenuation en dB
	 */
	public double getAttenuation(int port);

}
