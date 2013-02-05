package group1.project.synthlab.port.out;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.Port;

import com.jsyn.ports.ConnectableOutput;

public class OutPort extends Port implements IOutPort {
	protected ConnectableOutput jSynPort;
	

	public OutPort(String label, ConnectableOutput jSynPort, IModule module,  Factory factory){
		super(label, module, factory);
		this.jSynPort = jSynPort;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.out.IOutPort#getJSynPort()
	 */
	public ConnectableOutput getJSynPort() {
		return jSynPort;
	}


	
}
