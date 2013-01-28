package group1.project.synthlab.module;

import com.jsyn.unitgen.Circuit;


public interface IModule {	
	public void start();
	public void stop();
	public boolean isStarted();
	public String getName();
	public Circuit getCircuit(); 
}
