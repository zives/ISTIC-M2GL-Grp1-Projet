package group1.project.synthlab.module.noise;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.out.IOutPort;

/**
 * Interface du module Noise
 * @author Groupe 1
 *
 */
public interface INoiseModule extends IModule {

	/**
	 * @return le port de sortie du bruit blanc
	 */
	public IOutPort getOutWhite();
	
	/**
	 * @return le port de sortie du bruit rouge/brun
	 */
	public IOutPort getOutBrownian();
	
	/**
	 * @return le port de sortie du bruit rose
	 */
	public IOutPort getOutPink();

}
