package group1.project.synthlab.port;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.module.IModuleObservable;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.unitExtension.filter.filterSupervisor.FilterAmplitude;
import group1.project.synthlab.workspace.Workspace;

import java.util.ArrayList;
import java.util.List;

import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.UnitGenerator;


/**
 * @author Groupe 1
 * Un port
 */
public abstract class Port implements IPort {
	protected List<IPortObserver> observers = new ArrayList<IPortObserver>();
	protected ICable cable;
	protected String label;	
	protected Factory factory;
	protected IModule module;
	protected transient FilterAmplitude supervisor;
	protected int countModuleStarted;
	protected boolean supervisorAddedToSyth;
	protected Circuit circuit;

	public Port(String label, IModule module, Factory factory){
		this.label = label;
		this.factory = factory;
		this.module = module;
		this.supervisor = new FilterAmplitude(Signal.AMAX, false);	
		this.countModuleStarted = 0;
		register(module);
		module.register(this);
		supervisorAddedToSyth = false;
		circuit = new Circuit();
		circuit.add(supervisor);
	}
	
	@Override
	public void finalize() throws Throwable{
		if (cable != null)
			cable.disconnect();
		super.finalize();		
	}
	
	public UnitGenerator getSupervisor() {
		return this.supervisor;
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

	
	public boolean detectSignalSaturated() {
		return supervisor.isSatured();
	}

	public boolean detectSignal() {
		return supervisor.hasSignal() ;
	}

	@Override
	public void moduleIsOff(IModuleObservable module) {
		this.supervisor.turnOff();		
		
	}

	@Override
	public void moduleIsOn(IModuleObservable module) {
		if (!supervisorAddedToSyth) {
			Workspace.getInstance().getSynthetizer().add(circuit);
			this.circuit.start();
			supervisorAddedToSyth = true;
		}
		this.supervisor.turnOn();
		
		
		
	}



}
