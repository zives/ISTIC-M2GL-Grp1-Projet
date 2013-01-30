package group1.project.synthlab.port.out;

import group1.project.synthlab.port.IPort;

import com.jsyn.ports.ConnectableOutput;

public interface IOutPort extends IPort {

	public ConnectableOutput getJSynPort();
}
