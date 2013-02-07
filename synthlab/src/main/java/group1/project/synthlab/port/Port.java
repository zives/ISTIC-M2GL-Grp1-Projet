package group1.project.synthlab.port;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.unitExtensions.FilterAmplitude.FilterAmplitude;
import group1.project.synthlab.unitExtensions.FilterAmplitude.IFilterAmplitudeObservable;
import group1.project.synthlab.unitExtensions.FilterAmplitude.IFilterAmplitudeObserver;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Groupe 1
 * Un port
 */
public abstract class Port implements IPort,  IFilterAmplitudeObserver {
	protected List<IPortObserver> observers = new ArrayList<IPortObserver>();
	protected ICable cable;
	protected String label;	
	protected Factory factory;
	protected IModule module;
	protected FilterAmplitude supervisor;

	public Port(String label, IModule module, Factory factory){
		this.label = label;
		this.factory = factory;
		this.module = module;
		this.supervisor = new FilterAmplitude(Signal.AMAX, false);
		
		this.supervisor.register(this);
		register(module);
	}
	
	@Override
	public void finalize() throws Throwable{
		if (cable != null)
			cable.disconnect();
		super.finalize();		
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
		if (!observers.contains(observer))
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

	public IModule getModule() {
		return module;
	}
	
	
	public void setMaxForAmplitudeSupervisor(double amplitudeMax) {
		this.supervisor.setMax(amplitudeMax);
	}

	public void hasSignal(IFilterAmplitudeObservable subject, boolean hasSignal) {
		
		if(isUsed())
			this.cable.setSignalNull(!hasSignal);
	}

	public void warn(IFilterAmplitudeObservable subject, boolean tooHigh) {
		if(isUsed())
			this.cable.setSignalSaturated(tooHigh);		
		
	}
	

}
