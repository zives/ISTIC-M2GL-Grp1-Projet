package group1.project.synthlab.module;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.port.IPort;

import java.util.ArrayList;
import java.util.List;

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
	
	protected List<IModuleObserver> observers;
	
	
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
		this.observers = new ArrayList<>();
		
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
		warnModuleOn();
		
	}
	 
	public void stop() {
		circuit.stop();
		isOn = false;
		warnModuleOff();
		
	}
	
	public boolean isStarted() {
		return isOn;
	}



	@Override
	public void register(IModuleObserver observer) {
		observers.add(observer);
		
	}


	@Override
	public void unregister(IModuleObserver observer) {
		observers.remove(observer);
		
	}


	@Override
	public void warnModuleOn() {
		for(IModuleObserver observer: observers)
			observer.moduleIsOn(this);
		
	}


	@Override
	public void warnModuleOff() {
		for(IModuleObserver observer: observers)
			observer.moduleIsOff(this);
		
	}





	
}
