package group1.project.synthlab.module.multiplexer;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.signal.Tools;
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
	protected IInPort[] inPorts;
	protected IOutPort[] outPorts;

	// Filtres
	protected FilterAttenuator[] attenuators;

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

		attenuators = new FilterAttenuator[4];
		inPorts = new IInPort[4];
		outPorts = new IOutPort[4];
		for (int i = 0; i < 4; ++i) {
			attenuators[i] = new FilterAttenuator();
			inPorts[i] = factory.createInPort("in port " + String.valueOf(i),
					attenuators[i].input, this);
			outPorts[i] = factory.createOutPort(
					"out port " + String.valueOf(i), passThrough.output, this);

			attenuators[i].output.connect(passThrough.input);
		}

		isOn = true;

	}

	public void destruct() {
		for (int i = 0; i < 4; ++i) {
			if (inPorts[i].isUsed())
				inPorts[i].getCable().disconnect();
			if (outPorts[i].isUsed())
				outPorts[i].getCable().disconnect();
		}
	

	}

	public IInPort getInPort(int i) {
		if (i > inPorts.length)
			return null;
		return inPorts[i];
	}

	public IOutPort getOutPort(int i) {
		if (i > outPorts.length)
			return null;
		return outPorts[i];
	}
	
	public IInPort[] getInPorts() {
		return inPorts;
	}
	
	public IInPort[] getOutPorts() {
		return inPorts;
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

	@Override
	public void setAttenuation(double db, int port) {
		if (port > attenuators.length)
			return;
		attenuators[port].setAttenuation(Tools.dBToV(db) - 1);		
	}

}
