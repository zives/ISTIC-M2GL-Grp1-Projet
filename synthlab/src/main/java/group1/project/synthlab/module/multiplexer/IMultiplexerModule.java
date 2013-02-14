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


	public IInPort getInPort(int i) ;

	public IOutPort getOutPort(int i);
	
	public void setAttenuation(double db, int port);
	public double getAttenuation(int port);

}
