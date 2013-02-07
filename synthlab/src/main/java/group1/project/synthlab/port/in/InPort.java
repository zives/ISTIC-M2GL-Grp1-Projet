package group1.project.synthlab.port.in;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.Port;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.unitExtensions.FilterAmplitude.FilterAmplitude;
import group1.project.synthlab.unitExtensions.FilterAmplitude.IFilterAmplitudeObservable;
import group1.project.synthlab.unitExtensions.FilterAmplitude.IFilterAmplitudeObserver;

import com.jsyn.ports.ConnectableInput;

/**
 * @author Groupe 1
 * Un port d'entree
 */
public class InPort extends Port implements IInPort, IFilterAmplitudeObserver {
	protected ConnectableInput jSynPort;
	protected FilterAmplitude supervisor;
	
	public InPort(String label, ConnectableInput jSynPort, IModule module, Factory factory)  {
		super(label, module,  factory);
		this.jSynPort = jSynPort;
		this.supervisor = new FilterAmplitude(Signal.AMAX, false);
		this.supervisor.output.connect(jSynPort);
		this.supervisor.register(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.in.IInPort#getJSynPort()
	 */
	public ConnectableInput getJSynPort() {
		return this.supervisor.input;
	}

	public void warn(IFilterAmplitudeObservable subject, boolean tooHigh) {
		if(isUsed())
			this.cable.setSignalSaturated(tooHigh);		
	}

	public void setMaxForAmplitudeSupervisor(double amplitudeMax) {
		this.supervisor.setMax(amplitudeMax);
	}
 
}
