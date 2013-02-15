package group1.project.synthlab.port.out;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.Port;

import com.jsyn.ports.ConnectableOutput;

public class OutPort extends Port implements IOutPort {

	private static final long serialVersionUID = -60247523687967765L;
	
	
	/**
	 * Le port JSyn
	 */
	protected transient ConnectableOutput jSynPort;
	

	/**
	 * @param label le nom du port
	 * @param jSynPort le port JSyn
	 * @param module le module rattache
	 * @param factory la factory
	 */
	public OutPort(String label, ConnectableOutput jSynPort, IModule module,  Factory factory){
		super(label, module, factory);
		this.jSynPort = jSynPort;
		this.supervisor.input.connect(jSynPort);

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.out.IOutPort#getJSynPort()
	 */
	public ConnectableOutput getJSynPort() {		
		return this.supervisor.output;
	}



}
