package group1.project.synthlab.module.osc;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.unitExtension.filter.filterInterception.IFilterInterceptionObserver;

/**
 * Interface de creation du module de sortie.
 * 
 * @author Groupe 1
 * 
 */
public interface IOSCModule extends IModule, IFilterInterceptionObserver {
	
	public IInPort getInPort();
	public IOutPort getOutPort();
	public double poll();
	public void clearBuffer();

	public double getLastTime();

}
