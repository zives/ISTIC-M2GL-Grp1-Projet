package group1.project.synthlab.port.out;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.Port;
import group1.project.synthlab.workspace.Workspace;

import com.jsyn.ports.ConnectableOutput;

public class OutPort extends Port implements IOutPort {

	private static final long serialVersionUID = -60247523687967765L;
	protected transient ConnectableOutput jSynPort;
	

	public OutPort(String label, ConnectableOutput jSynPort, IModule module,  Factory factory){
		super(label, module, factory);
		this.jSynPort = jSynPort;
		this.supervisor.input.connect(jSynPort);
		module.getCircuit().add(this.supervisor);

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.out.IOutPort#getJSynPort()
	 */
	public ConnectableOutput getJSynPort() {		
		return this.supervisor.output;
	}



}
