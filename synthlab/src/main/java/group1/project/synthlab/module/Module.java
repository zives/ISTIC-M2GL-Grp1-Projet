package group1.project.synthlab.module;

import group1.project.synthlab.factory.Factory;

import com.jsyn.unitgen.Circuit;


public abstract class Module implements IModule {
	
	
	protected Circuit circuit;
	
	protected String name;
	
	protected Factory factory;
	
	
	public Module(String name, Factory factory) {
		this.circuit = new Circuit();
		this.name = name;
		this.factory = factory;
	}
	
	public String getName() {
		return name;
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
	
	
}
