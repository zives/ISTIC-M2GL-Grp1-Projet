package group1.project.synthlab.port.in;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.port.Port;

import com.jsyn.ports.ConnectableInput;

public class InPort extends Port implements IInPort {
	protected ConnectableInput jSynPort;
	
	public InPort(String label, ConnectableInput jSynPort, Factory factory)  {
		super(label, factory);
		this.jSynPort = jSynPort;
	}

	public ConnectableInput getJSynPort() {
		return jSynPort;
	}
 
}
