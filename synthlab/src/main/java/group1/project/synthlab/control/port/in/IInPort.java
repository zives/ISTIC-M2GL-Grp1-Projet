package group1.project.synthlab.control.port.in;

import group1.project.synthlab.control.port.IPort;

import com.jsyn.ports.ConnectableInput;

public interface IInPort extends IPort {

	public ConnectableInput getJSynPort();
		
}
