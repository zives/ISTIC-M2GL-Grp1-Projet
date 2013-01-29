package group1.project.synthlab.port.in;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.port.Port;
import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

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