package group1.project.synthlab.port.in;

import group1.project.synthlab.port.IPort;

import com.jsyn.ports.ConnectableInput;

public interface IInPort extends IPort {

	public ConnectableInput getJSynPort();
		
}
