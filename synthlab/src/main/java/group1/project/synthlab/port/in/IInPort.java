package group1.project.synthlab.port.in;

import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.out.OutPort;

import com.jsyn.ports.ConnectableInput;

public interface IInPort extends IPort {

	public void connectTo(OutPort targetPort) throws PortAlreadyUsed;
	public void disconnect();
	public ConnectableInput getJSynPort();
		
}
