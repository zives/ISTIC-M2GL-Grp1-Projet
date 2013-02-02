package group1.project.synthlab.module.vco;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.port.out.OutPort;

public interface IVCOModule extends IModule {
	public InPort getFm();

	public OutPort getOutSine();

	public OutPort getOutSquare();

	public OutPort getOutTriangle();
	
	public int getOctave();

	public void setOctave(int reglageoctave);

	public double getFineAdjustment();

	public void setFineAdjustment(double reglagefin);

}
