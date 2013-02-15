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
 * @author Groupe 1 Un port
 */
public abstract class Port implements IPort {

	private static final long serialVersionUID = 2567126327917404741L;

	/** Les observateurs du port */
	protected List<IPortObserver> observers = new ArrayList<IPortObserver>();

	/** Le cable relié au port, s'il existe */
	protected ICable cable;

	/** Le nom du port */
	protected String label;

	/** La factory */
	protected Factory factory;

	/** Le module rattache au port */
	protected IModule module;

	/** Un filtre supervisant le signal circulant dans le cable */
	protected transient FilterAmplitude supervisor;

	/**
	 * Determine si le filtre superviseur a deja ete aujoute au synthetiseur
	 */
	protected boolean supervisorAddedToSyth;
	
	
	/**
	 * Le circuit contenant le filtre (il faut faire apparaitre la notion de circuit a tout les niveaux ou nul part sinon JSyn deforme le signal)
	 */
	protected Circuit circuit;

	/**
	 * @param label nom du port
	 * @param module le module rattache au port
	 * @param factory la factory
	 */
	public Port(String label, IModule module, Factory factory) {
		
		//Intialisation
		this.label = label;
		this.factory = factory;
		this.module = module;
		this.supervisor = new FilterAmplitude(Signal.AMAX, false);

		//Enregistrement des observateurs
		register(module);
		module.register(this);
		
		//Ajout du superviseur au circuit
		supervisorAddedToSyth = false;
		circuit = new Circuit();
		circuit.add(supervisor);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	public void finalize() throws Throwable {
		if (cable != null)
			cable.disconnect();
		super.finalize();
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPort#getSupervisor()
	 */
	public UnitGenerator getSupervisor() {
		return this.supervisor;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPort#getCable()
	 */
	public ICable getCable() {
		return cable;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPort#setCable(group1.project.synthlab.cable.ICable)
	 */
	public void setCable(ICable cable) {
		this.cable = cable;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPort#getLabel()
	 */
	public String getLabel() {
		return label;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPort#isUsed()
	 */
	public boolean isUsed() {
		return cable != null;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPortObservable#register(group1.project.synthlab.port.IPortObserver)
	 */
	public void register(IPortObserver observer) {
		if (!observers.contains(observer))
			observers.add(observer);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPortObservable#unregister(group1.project.synthlab.port.IPortObserver)
	 */
	public void unregister(IPortObserver observer) {
		observers.remove(observer);

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPortObservable#cableConnected()
	 */
	public void cableConnected() {
		for (IPortObserver observer : observers)
			observer.cableConnected(this);

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPortObservable#cableDisconnected()
	 */
	public void cableDisconnected() {
		for (IPortObserver observer : observers)
			observer.cableDisconnected(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPort#getModule()
	 */
	public IModule getModule() {
		return module;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPort#setMaxForAmplitudeSupervisor(double)
	 */
	public void setMaxForAmplitudeSupervisor(double amplitudeMax) {
		this.supervisor.setMax(amplitudeMax);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPort#detectSignalSaturated()
	 */
	public boolean detectSignalSaturated() {
		return supervisor.isSatured();
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPort#detectSignal()
	 */
	public boolean detectSignal() {
		return supervisor.hasSignal();
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModuleObserver#moduleIsOff(group1.project.synthlab.module.IModuleObservable)
	 */
	@Override
	public void moduleIsOff(IModuleObservable module) {
		//Indique au superviseur que le module est arrete et que la derniere tension de sortie doit etre null
		this.supervisor.generateNullVoltage();
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModuleObserver#moduleIsOn(group1.project.synthlab.module.IModuleObservable)
	 */
	@Override
	public void moduleIsOn(IModuleObservable module) {
		if (!supervisorAddedToSyth) {
			//Si le circuit n'a pas ete ajout, on le fait
			//Pourquoi ici et pas dans le constructeur
			//Car dans le constructeur, le synthetiseur n'est pas encore cree
			Workspace.getInstance().getSynthetizer().add(circuit);
			this.circuit.start();
			supervisorAddedToSyth = true;
		}
		//Demarre le superviseur
		this.supervisor.setToNormalState();

	}

}
