package group1.project.synthlab.port;

import group1.project.synthlab.cable.ICable;

import java.util.ArrayList;
import java.util.List;


public abstract class Port implements IPort, IPortObservable {
	protected List<IPortObserver> observers = new ArrayList<IPortObserver>();
	protected ICable cable;
	private String label;	
	
	public Port() {
	}
		
	public ICable getCable() {
		return cable;
	}

	public void setCable(ICable cable) {
		this.cable = cable;
	}
	
	public Port(String label) {
		this.label = label;
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
