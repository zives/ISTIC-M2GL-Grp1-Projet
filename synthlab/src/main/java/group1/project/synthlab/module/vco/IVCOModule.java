package group1.project.synthlab.module.vco;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;

public interface IVCOModule extends IModule {
	public IInPort getFm();

	public IOutPort getOutSine();

	public IOutPort getOutSquare();

	public IOutPort getOutTriangle();
	
	public int getOctave();

	public void setOctave(int reglageoctave);

	public double getFineAdjustment();

	public void setFineAdjustment(double reglagefin);

}
