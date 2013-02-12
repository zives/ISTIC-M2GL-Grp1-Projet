package group1.project.synthlab.module.osc;

import group1.project.synthlab.exceptions.BufferTooBig;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.unitExtensions.filterInterception.FilterInterception;
import group1.project.synthlab.workspace.Workspace;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JFrame;

import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.util.AutoCorrelator;

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
	protected boolean isOn;
	protected IOSCModule self;
	protected Queue<Double> buffer;
	protected double lastTime;
	protected AudioScope scope;
	protected PassThrough passThrough;

	/**
	 * Initialise le circuit (attenuateur, port, ...)
	 */
	public OSCModule(Factory factory) {
		super("OSC-" + ++moduleCount, factory);
		self = this;
		passThrough = new PassThrough();
		filter = new FilterInterception();
		inPort = factory.createInPort("in", passThrough.input, this);
		outPort = factory.createOutPort("out", filter.output, this);
		buffer = new ConcurrentLinkedQueue<>();
		isOn = false;
		scope = new AudioScope(Workspace.getInstance().getSynthetizer());
		filter.register(this);
		circuit.add(filter);		
		scope.addProbe( filter.output);
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
	 * @see group1.project.synthlab.module.IModule#start()
	 */
	public void start() {
		passThrough.output.connect(filter.input);
		filter.start();
		scope.start();
		isOn = true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#stop()
	 */
	public void stop() {
		isOn = false;		
		passThrough.output.disconnect(filter.input);
		scope.stop();
		buffer.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#isStarted()
	 */
	public boolean isStarted() {
		return isOn;
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
					"Le buffer du module OSC est trop rempli, pensez à le vider régulierement de l'ordre de la ms...");
		this.buffer.addAll(buffer);
		this.lastTime = time;
	}

}
