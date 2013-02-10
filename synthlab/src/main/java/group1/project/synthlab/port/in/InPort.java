package group1.project.synthlab.port.in;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.Port;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.unitExtensions.filterSupervisor.FilterAmplitude;
import group1.project.synthlab.unitExtensions.filterSupervisor.IFilterAmplitudeObservable;
import group1.project.synthlab.unitExtensions.filterSupervisor.IFilterAmplitudeObserver;

import com.jsyn.ports.ConnectableInput;

/**
 * @author Groupe 1
 * Un port d'entree
 */
public class InPort extends Port implements IInPort {
	protected ConnectableInput jSynPort;

	
	public InPort(String label, ConnectableInput jSynPort, IModule module, Factory factory)  {
		super(label, module,  factory);
		this.jSynPort = jSynPort;
				
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.in.IInPort#getJSynPort()
	 */
	public ConnectableInput getJSynPort() {
		return jSynPort;
	}


 
}
