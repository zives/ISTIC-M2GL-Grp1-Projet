package group1.project.synthlab.module.osc;

import group1.project.synthlab.exceptions.BufferTooBig;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.module.vco.VCOModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.unitExtension.filter.filterInterception.FilterInterception;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.Multiply;

/**
 * Module de sortie
 * 
 * @author Groupe 1
 * 
 */
public class OSCModule extends Module implements IOSCModule {

	protected static int moduleCount = 0;

	/* Defintion des ports */
	protected IInPort inPort;
	protected IOutPort outPort;

	/* Variables internes */
	protected FilterInterception filter;
	protected IOSCModule self;
	protected Queue<Double> buffer;
	protected double lastTime;
	//protected AudioScope scope;
	//protected PassThrough passThrough;

	/**
	 * Initialise le circuit (attenuateur, port, ...)
	 */
	public OSCModule(Factory factory) {
		super("OSC-" + ++moduleCount, factory);
		self = this;
		//passThrough = new PassThrough();
		filter = new FilterInterception();
		inPort = factory.createInPort("in", filter.input, this);
		outPort = factory.createOutPort("out", filter.output, this);
		buffer = new ConcurrentLinkedQueue<>();
		//scope = new AudioScope(Workspace.getInstance().getSynthetizer());
		filter.register(this);
		//circuit.add(passThrough);
		circuit.add(filter);		
		//scope.addProbe( filter.output);
		//passThrough.output.connect(filter.input);
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

	public IInPort getInPort() {
		return inPort;
	}

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
	
	public void cableConnected(IPort port) {
	}

	public void cableDisconnected(IPort port) {

	}

	public double poll() {
		return buffer.poll();
	}

	public void clearBuffer() {
		buffer.clear();
	}

	public double getLastTime() {
		return lastTime;
	}

	@Override
	public void interceptionResult(final List<Double> buffer, double time)
			throws BufferTooBig {
		if (this.buffer.size() + buffer.size() > 100000)
			throw new BufferTooBig(
					"Le buffer du module OSC est trop rempli, pensez � le vider r�gulierement de l'ordre de la ms...");
		this.buffer.addAll(buffer);
		this.lastTime = time;
	}
	
	@Override
	public void resetCounterInstance() {
		OSCModule.moduleCount = 0;		
	}
	


}
