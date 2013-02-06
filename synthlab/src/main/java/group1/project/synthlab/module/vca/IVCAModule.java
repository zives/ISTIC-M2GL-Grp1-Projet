package group1.project.synthlab.module.vca;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;

public interface IVCAModule extends IModule {
	
	public void changeGain();	
	public double geta0() ;
	public void seta0(double a0) ;		
	public IInPort getIn();	
	public IInPort getAm() ;
	public IOutPort getOut() ;
}