package group1.project.synthlab.module.multiplexer;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.module.vco.VCOModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Tools;
import group1.project.synthlab.unitExtension.filter.filterAttenuator.FilterAttenuator;

import com.jsyn.unitgen.PassThrough;

/**
 * Module de sortie
 * 
 * @author Groupe 1
 * 
 */
public class MultiplexerModule extends Module implements IMultiplexerModule {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5543487526179911346L;

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

	// Filtres
	protected transient FilterAttenuator[] attenuators;
	
	//Valeurs des controles
	protected double[] attenuationsDB;

	/* Variables internes */
	private PassThrough passThrough;
	/**
	 * Initialise le circuit (attenuateur, port, ...)
	 */
	public MultiplexerModule(Factory factory) {
		super("Multiplexer-" + ++moduleCount, factory);

		passThrough = new PassThrough();
		circuit.add(passThrough);

		attenuators = new FilterAttenuator[4];
		attenuationsDB = new double[4];
		
		for (int i = 0; i < 4; ++i) {
			attenuators[i] = new FilterAttenuator();
			attenuators[i].output.connect(passThrough.input);
			circuit.add(attenuators[i]);
		}
		inPort1 = factory.createInPort("in port 1",
				attenuators[0].input, this);
		outPort1 = factory.createOutPort(
				"out port 1", passThrough.output, this);
		inPort2 = factory.createInPort("in port 2",
				attenuators[1].input, this);
		outPort2 = factory.createOutPort(
				"out port 2", passThrough.output, this);
		inPort3 = factory.createInPort("in port 3",
				attenuators[2].input, this);
		outPort3 = factory.createOutPort(
				"out port 3", passThrough.output, this);
		inPort4 = factory.createInPort("in port4",
				attenuators[3].input, this);
		outPort4 = factory.createOutPort(
				"out port 4", passThrough.output, this);

	}
	
	@Override
	public void refresh() {
		for (int i = 0; i < 4; ++i) 
			setAttenuation(attenuationsDB[i], i);
	}

	public void destruct() {
		if (inPort1.isUsed())
			inPort1.getCable().disconnect();
		if (outPort1.isUsed())
			outPort1.getCable().disconnect();
		if (inPort2.isUsed())
			inPort2.getCable().disconnect();
		if (outPort2.isUsed())
			outPort2.getCable().disconnect();
		if (inPort3.isUsed())
			inPort3.getCable().disconnect();
		if (outPort3.isUsed())
			outPort3.getCable().disconnect();
		if (inPort4.isUsed())
			inPort4.getCable().disconnect();
		if (outPort4.isUsed())
			outPort4.getCable().disconnect();
	
	

	}

	public IInPort getInPort(int i) {
		if (i > attenuationsDB.length)
			return null;
		switch (i) {
			case 0:
				return inPort1;
			case 1:
				return inPort2;
			case 2:
				return inPort3;
			case 3:
				return inPort4;
		}
		return inPort1;
	}

	public IOutPort getOutPort(int i) {
		if (i > attenuationsDB.length)
			return null;
		switch (i) {
			case 0:
				return outPort1;
			case 1:
				return outPort2;
			case 2:
				return outPort3;
			case 3:
				return outPort4;
		}
		return outPort1;
	}

	public void cableConnected(IPort port) {
	}

	public void cableDisconnected(IPort port) {

	}

	@Override
	public void setAttenuation(double db, int port) {
		if (port > attenuators.length)
			return;
		attenuationsDB[port] = db;
		attenuators[port].setAttenuation(Tools.dBToV(db) - 1);		
	}
	
	@Override
	public void resetCounterInstance() {
		MultiplexerModule.moduleCount = 0;		
	}

	@Override
	public double getAttenuation(int port) {
		return attenuationsDB[port];
	}

}
