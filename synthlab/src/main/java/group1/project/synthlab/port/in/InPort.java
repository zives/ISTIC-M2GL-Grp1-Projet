package group1.project.synthlab.port.in;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.Port;

import com.jsyn.ports.ConnectableInput;

/**
 * @author Groupe 1
 * Un port d'entree
 */
public class InPort extends Port implements IInPort {

	private static final long serialVersionUID = 8298376994052663233L;
	
	
	/**
	 * Le port JSyn
	 */
	protected transient ConnectableInput jSynPort;

	/**
	 * @param label le nom du port
	 * @param jSynPort le port JSyn
	 * @param module le module rattache
	 * @param factory la factory
	 */
	public InPort(String label, ConnectableInput jSynPort, IModule module, Factory factory)  {
		super(label, module,  factory);
		this.jSynPort = jSynPort;
		
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.in.IInPort#getJSynPort()
	 */
	public ConnectableInput getJSynPort() {		
		return jSynPort;
	}

	


 
}
