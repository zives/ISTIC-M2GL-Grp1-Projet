package group1.project.synthlab.port.out;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.port.Port;
import group1.project.synthlab.port.in.InPort;

import com.jsyn.ports.ConnectableOutput;

public class OutPort extends Port implements IOutPort {
	protected ConnectableOutput jSynPort;
	private ICable cable;

	public OutPort(String label, ConnectableOutput jSynPort) {
		super(label);
		this.jSynPort = jSynPort;
	}

	public ConnectableOutput getJSynPort() {
		return jSynPort;
	}
	
}
