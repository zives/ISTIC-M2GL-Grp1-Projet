package group1.project.synthlab.port.in;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.Port;

import com.jsyn.ports.ConnectableInput;

public class InPort extends Port implements IInPort {
	protected ConnectableInput jSynPort;
	
	public InPort(String label, ConnectableInput jSynPort, IModule module, Factory factory)  {
		super(label, module,  factory);
		this.jSynPort = jSynPort;
	}

	public ConnectableInput getJSynPort() {
		return jSynPort;
	}
 
}
