package group1.project.synthlab.port;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.factory.Factory;

import java.util.ArrayList;
import java.util.List;


public abstract class Port implements IPort {
	protected List<IPortObserver> observers = new ArrayList<IPortObserver>();
	protected ICable cable;
	protected String label;	
	protected Factory factory;

	public Port(String label, Factory factory){
		this.label = label;
		this.factory = factory;
	}
		
	public ICable getCable() {
		return cable;
	}

	public void setCable(ICable cable) {
		this.cable = cable;
	}
	
	
	
	public String getLabel() {
		return label;
	}
	
	public boolean isUsed() {
		return cable != null;
	}

	public void register(IPortObserver observer) {
		observers.add(observer);
	}

	public void unregister(IPortObserver observer) {
		observers.remove(observer);
		
	}

	public void cableConnected() {
		for(IPortObserver observer: observers)
			observer.cableConnected(this);
		
	}
	public void cableDisconnected() {
		for(IPortObserver observer: observers)
			observer.cableDisconnected(this);
	}
	

}
