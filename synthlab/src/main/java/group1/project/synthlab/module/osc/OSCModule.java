package group1.project.synthlab.module.osc;

import group1.project.synthlab.exceptions.BufferTooBig;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.unitExtension.filter.filterInterception.FilterInterception;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Module oscilloscope
 * Surveille des valeurs passant dans un filtre et les ajoute dans un buffer qui doit etre vide regulierement 
 * @author Groupe 1
 * 
 */
public class OSCModule extends Module implements IOSCModule {

	private static final long serialVersionUID = -1135623254087663378L;

	protected static int moduleCount = 0;

	/** Port d'entree */
	protected IInPort inPort;
	
	/** Port de sortie */
	protected IOutPort outPort;

	/** Filtre d'interception des amplitudes */
	protected transient FilterInterception filter;
	
	/** Le module lui meme */
	protected transient IOSCModule self;
	
	/** Le buffer representee comme une queue fifo */
	protected Queue<Double> buffer;
	
	/** Derniere date du synthetiseur a laquelle le buffer a ete modifie */
	protected double lastTime;


	public OSCModule(Factory factory) {
		super("OSC-" + ++moduleCount, factory);
		
		/* Initialise les filtres et ports */
		self = this;
		lastTime = 0;
		filter = new FilterInterception();
		inPort = factory.createInPort("in", filter.input, this);
		outPort = factory.createOutPort("out", filter.output, this);
		buffer = new ConcurrentLinkedQueue<>(); //On va effectuer des operations dans des threads different, ce type de liste est indispensable
		filter.register(this);
		circuit.add(filter);		
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#refresh()
	 */
	@Override
	public void refresh() {
		lastTime = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#destruct()
	 */
	public void destruct() {
		if (inPort.isUsed())
			inPort.getCable().disconnect();
		if (outPort.isUsed())
			outPort.getCable().disconnect();

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.osc.IOSCModule#getInPort()
	 */
	public IInPort getInPort() {
		return inPort;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.osc.IOSCModule#getOutPort()
	 */
	public IOutPort getOutPort() {
		return outPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#stop()
	 */
	public void stop() {
		super.stop();
		buffer.clear();
	
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPortObserver#cableConnected(group1.project.synthlab.port.IPort)
	 */
	public void cableConnected(IPort port) {
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPortObserver#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	public void cableDisconnected(IPort port) {

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.osc.IOSCModule#poll()
	 */
	public double poll() {
		return buffer.poll();
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.osc.IOSCModule#clearBuffer()
	 */
	public void clearBuffer() {
		buffer.clear();
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.osc.IOSCModule#getLastTime()
	 */
	public double getLastTime() {
		return lastTime;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.unitExtension.filter.filterInterception.IFilterInterceptionObserver#interceptionResult(java.util.List, double)
	 */
	@Override
	public void interceptionResult(final List<Double> buffer, double time)
			throws BufferTooBig {
		if (this.buffer.size() + buffer.size() > 100000)
			throw new BufferTooBig(
					"Le buffer du module OSC est trop rempli, pensez a le vider regulierement de l'ordre de la ms...");
		this.buffer.addAll(buffer);
		this.lastTime = time;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#resetCounterInstance()
	 */
	@Override
	public void resetCounterInstance() {
		OSCModule.moduleCount = 0;		
	}

}
