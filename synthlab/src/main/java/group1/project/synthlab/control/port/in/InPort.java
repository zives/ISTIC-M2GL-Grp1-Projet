package group1.project.synthlab.control.port.in;

import group1.project.synthlab.control.port.Port;

import com.jsyn.ports.ConnectableInput;

public class InPort extends Port implements IInPort {
	protected ConnectableInput jSynPort;
	
	public InPort(String label, ConnectableInput jSynPort) {
		super(label);
		this.jSynPort = jSynPort;
	}

	public ConnectableInput getJSynPort() {
		return jSynPort;
	}

}
