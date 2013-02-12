package group1.project.synthlab.module.sequencer;

import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;

public interface ISequencerModule extends IModule {
	public void resetSteps();
	public void update();
	public IInPort getGate();
	public IOutPort getOut();
}
