package group1.project.synthlab.port.out;

import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.port.Port;
import group1.project.synthlab.port.in.InPort;

import com.jsyn.ports.ConnectableOutput;

public class OutPort extends Port implements IOutPort {
	protected ConnectableOutput jSynPort;
	private InPort targetPort;

	public OutPort(String label, ConnectableOutput jSynPort) {
		super(label);
		this.jSynPort = jSynPort;
	}

	public void connectTo(InPort targetPort) throws PortAlreadyUsed {
		if (isUsed())
			throw new PortAlreadyUsed("Port " + this.getLabel()
					+ " déjà utilisé, déconnectez le avant.");
		this.targetPort = targetPort;
		jSynPort.connect(targetPort.getJSynPort());
	}

	public void disconnect() {
		if (targetPort != null)
			jSynPort.disconnect(targetPort.getjSynPort());
		targetPort = null;
	}

	public ConnectableOutput getJSynPort() {
		return jSynPort;
	}
	public boolean isUsed() {
		return targetPort != null;
	}

}
