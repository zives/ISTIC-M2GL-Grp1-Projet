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
	protected IInPort[] inPorts;
	protected IOutPort[] outPorts;

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
		inPorts = new IInPort[4];
		outPorts = new IOutPort[4];
		for (int i = 0; i < 4; ++i) {
			attenuators[i] = new FilterAttenuator();
			inPorts[i] = factory.createInPort("in port " + String.valueOf(i + 1),
					attenuators[i].input, this);
			outPorts[i] = factory.createOutPort(
					"out port " + String.valueOf(i + 1), passThrough.output, this);

			attenuators[i].output.connect(passThrough.input);
			circuit.add(attenuators[i]);
		}

	}
	
	@Override
	public void refresh() {
		for (int i = 0; i < 4; ++i) 
			setAttenuation(attenuationsDB[i], i);
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
