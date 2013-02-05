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


	/**
	 * @return le port d'entree 1.
	 */
	public IInPort getInPort1();

	/**
	 * @return le port d'entree 2.
	 */
	public IInPort getInPort2();
	
	/**
	 * @return le port d'entree 3.
	 */
	public IInPort getInPort3();
	
	/**
	 * @return le port d'entree 4.
	 */
	public IInPort getInPort4();
	
	
	/**
	 * @return le port de sortie 1.
	 */
	public IOutPort getOutPort1();

	/**
	 * @return le port de sortie 2.
	 */
	public IOutPort getOutPort2();
	
	/**
	 * @return le port de sortie 3.
	 */
	public IOutPort getOutPort3();
	
	/**
	 * @return le port de sortie 4.
	 */
	public IOutPort getOutPort4();



}
