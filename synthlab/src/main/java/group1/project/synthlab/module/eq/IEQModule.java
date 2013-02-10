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

	
	public double[] getFrequencies();
	
	public void setAttenuation(int i, double dB);
	
	public double getAttenuation(int i);

	

	
	public IInPort getInPort();
	public IOutPort getOutPort();

}
