package group1.project.synthlab.module.multiplexer;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.unitExtensions.FilterAttenuator.FilterAttenuator;

import javax.swing.JFrame;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.TriangleOscillator;

/**
 * Module de sortie
 * 
 * @author Groupe 1
 * 
 */
public class MultiplexerModule extends Module implements IMultiplexerModule {

	public enum Distribution {
		NORMAL, DISTRIBUTED
	}

	protected static int moduleCount = 0;


	/* Defintion des ports */
	protected IInPort inPort1;
	protected IInPort inPort2;
	protected IInPort inPort3;
	protected IInPort inPort4;
	protected IOutPort outPort1;
	protected IOutPort outPort2;
	protected IOutPort outPort3;
	protected IOutPort outPort4;

	/* Variables internes */
	private PassThrough passThrough;
	private boolean isOn;



	/**
	 * Initialise le circuit (attenuateur, port, ...)
	 */
	public MultiplexerModule(Factory factory) {
		super("Multiplexer-" + ++moduleCount, factory);


		passThrough = new PassThrough();
		circuit.add(passThrough);

		
		inPort1 = factory.createInPort("in port 1", passThrough.input,
				this);
		inPort2 = factory.createInPort("in port 2", passThrough.input,
				this);
		inPort3 = factory.createInPort("in port 3", passThrough.input,
				this);
		inPort4 = factory.createInPort("in port 4", passThrough.input,
				this);
		outPort1 = factory.createOutPort("out port 1", passThrough.output,
				this);
		outPort2 = factory.createOutPort("out port 2", passThrough.output,
				this);
		outPort3 = factory.createOutPort("out port 3", passThrough.output,
				this);
		outPort4 = factory.createOutPort("out port 4", passThrough.output,
				this);


		isOn = true;

	}

	
	public void destruct() {
		if (inPort1.isUsed())
			inPort1.getCable().disconnect();
		
		if (inPort2.isUsed())
			inPort2.getCable().disconnect();
		
		if (inPort3.isUsed())
			inPort3.getCable().disconnect();
		
		if (inPort4.isUsed())
			inPort4.getCable().disconnect();
		
		if (outPort1.isUsed())
			outPort1.getCable().disconnect();
		
		if (outPort2.isUsed())
			outPort2.getCable().disconnect();
		
		if (outPort3.isUsed())
			outPort3.getCable().disconnect();
		
		if (outPort4.isUsed())
			outPort4.getCable().disconnect();
		
		
	}

	
	public IInPort getInPort1() {
		return inPort1;
	}


	public IInPort getInPort2() {
		return inPort2;
	}


	public IInPort getInPort3() {
		return inPort3;
	}


	public IInPort getInPort4() {
		return inPort4;
	}


	public IOutPort getOutPort1() {
		return outPort1;
	}


	public IOutPort getOutPort2() {
		return outPort2;
	}


	public IOutPort getOutPort3() {
		return outPort3;
	}


	public IOutPort getOutPort4() {
		return outPort4;
	}


	public void start() {
		circuit.start();
		passThrough.start();
		passThrough.setEnabled(true);
		isOn = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#stop()
	 */
	public void stop() {
		circuit.stop();
		passThrough.stop();
		passThrough.setEnabled(false);
		isOn = false;
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

	

}
