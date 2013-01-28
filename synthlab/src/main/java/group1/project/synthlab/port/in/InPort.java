package group1.project.synthlab.port.in;

import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.port.Port;
import group1.project.synthlab.port.out.OutPort;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

public class InPort extends Port implements IInPort {
	protected ConnectableInput jSynPort;
	private OutPort targetPort;

	public InPort(String label, ConnectableInput jSynPort) {
		super(label);
		this.jSynPort = jSynPort;
	}

	public void connectTo(OutPort targetPort) throws PortAlreadyUsed {
		if (isUsed())
			throw new PortAlreadyUsed("Port " + this.getLabel()
					+ " déjà utilisé, déconnectez le avant.");
		this.targetPort = targetPort;
		jSynPort.connect(targetPort.getJSynPort());
	}

	public void disconnect() {
		if (targetPort != null)
			jSynPort.disconnect(targetPort.getJSynPort());
		targetPort = null;
	}

	public ConnectableInput getjSynPort() {
		return jSynPort;
	}

	public ConnectableInput getJSynPort() {
		return jSynPort;
	}

	public boolean isUsed() {
		return targetPort != null;
	}
}
