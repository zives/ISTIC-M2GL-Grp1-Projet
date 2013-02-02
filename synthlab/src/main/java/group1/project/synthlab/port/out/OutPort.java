package group1.project.synthlab.port.out;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.port.Port;

import com.jsyn.ports.ConnectableOutput;

public class OutPort extends Port implements IOutPort {
	protected ConnectableOutput jSynPort;
	

	public OutPort(String label, ConnectableOutput jSynPort, Factory factory){
		super(label, factory);
		this.jSynPort = jSynPort;
	}

	public ConnectableOutput getJSynPort() {
		return jSynPort;
	}


	
}
