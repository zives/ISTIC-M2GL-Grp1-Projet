package group1.project.synthlab.module;

import com.jsyn.unitgen.Circuit;

public abstract class Module implements IModule {
	protected Circuit circuit;
	
	public Module() {
		this.circuit = new Circuit();
	}
}
