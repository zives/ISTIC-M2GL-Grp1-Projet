package group1.project.synthlab.module;

import group1.project.synthlab.factory.Factory;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.unitgen.Circuit;

/**
 * Module
 * @author Groupe 1
 *
 */
public abstract class Module implements IModule {

	private static final long serialVersionUID = 8802333776761919224L;

	/**
	 * Chaque module est consitué d'un circuit, un circuit est la notion de module pour JSyn
	 * Celui est-ci est encapsule dans notre implementation comme adaptateur
	 */
	protected transient Circuit circuit;
	
	/**
	 * Le nom du module
	 */
	protected String name;
	
	/**
	 * La factory qui a servi a creer le module
	 */
	protected Factory factory;
	
	/**
	 * Determine si le module est en marche
	 */
	protected boolean isOn;
	
	/**
	 * Liste des observateurs
	 */
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
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#getName()
	 */
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#getCircuit()
	 */
	public Circuit getCircuit() {
		return circuit;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#getFactory()
	 */
	public Factory getFactory() {
		return factory;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#setFactory(group1.project.synthlab.factory.Factory)
	 */
	public void setFactory(Factory factory) {
		this.factory = factory;
	}


	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#start()
	 */
	public void start(){
		circuit.start();
		isOn = true;
		warnModuleOn();
		
	}
	 
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#stop()
	 */
	public void stop() {
		circuit.stop();
		isOn = false;
		warnModuleOff();
		
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#isStarted()
	 */
	public boolean isStarted() {
		return isOn;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModuleObservable#register(group1.project.synthlab.module.IModuleObserver)
	 */
	@Override
	public void register(IModuleObserver observer) {
		observers.add(observer);
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModuleObservable#unregister(group1.project.synthlab.module.IModuleObserver)
	 */
	@Override
	public void unregister(IModuleObserver observer) {
		observers.remove(observer);
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModuleObservable#warnModuleOn()
	 */
	@Override
	public void warnModuleOn() {
		for(IModuleObserver observer: observers)
			observer.moduleIsOn(this);
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModuleObservable#warnModuleOff()
	 */
	@Override
	public void warnModuleOff() {
		for(IModuleObserver observer: observers)
			observer.moduleIsOff(this);
		
	}
}
