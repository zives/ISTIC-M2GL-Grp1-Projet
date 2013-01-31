package group1.project.synthlab.control.module;

import com.jsyn.unitgen.Circuit;


public abstract class Module implements IModule {
	protected static int moduleCount = 0;
	
	protected Circuit circuit;
	
	protected String name;
	
	
	public Module(String name) {
		this.circuit = new Circuit();
		this.name = name;
		++moduleCount;
	}
	
	public String getName() {
		return name;
	}
	
	public Circuit getCircuit() {
		return circuit;
	}
}
