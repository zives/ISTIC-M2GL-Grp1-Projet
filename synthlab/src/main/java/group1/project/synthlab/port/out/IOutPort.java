package group1.project.synthlab.port.out;

import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.InPort;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.ConnectableOutput;

public interface IOutPort extends IPort {
	
	public void connectTo(InPort targetPort) throws PortAlreadyUsed;
	public void disconnect();
	public ConnectableOutput getJSynPort();
}
