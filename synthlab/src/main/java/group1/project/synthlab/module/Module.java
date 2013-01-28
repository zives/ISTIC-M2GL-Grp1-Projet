package group1.project.synthlab.module;

import com.jsyn.unitgen.Circuit;


public abstract class Module implements IModule {
	protected static int moduleCount = 0;
	
	protected Circuit circuit;
	
	protected boolean started;
	protected String name;
	
	
	public Module(String name) {
		this.circuit = new Circuit();
		this.started = false;
		this.name = name;
		++moduleCount;
	}
	
	

	public boolean isStarted() {
		return started;
	}
	
	public String getName() {
		return name;
	}
	
	public Circuit getCircuit() {
		return circuit;
	}
}
