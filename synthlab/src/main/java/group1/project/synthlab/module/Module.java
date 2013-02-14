package group1.project.synthlab.module;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.port.IPort;

import com.jsyn.unitgen.Circuit;

/**
 * Module
 * @author Groupe 1
 *
 */
public abstract class Module implements IModule {
	
	protected transient Circuit circuit;
	
	protected String name;
	
	protected Factory factory;
	
	protected boolean isOn;
	
	/**
	 * Constructeur du module
	 * @param name nom du module
	 * @param factory
	 */
	public Module(String name, Factory factory) {
		this.circuit = new Circuit();
		this.name = name;
		this.factory = factory;
		this.isOn = false;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Circuit getCircuit() {
		return circuit;
	}

	public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}


	public void start(){
		circuit.start();
		isOn = true;
	}
	 
	public void stop() {
		circuit.stop();
		isOn = false;
	}
	
	public boolean isStarted() {
		return isOn;
	}
	
}
