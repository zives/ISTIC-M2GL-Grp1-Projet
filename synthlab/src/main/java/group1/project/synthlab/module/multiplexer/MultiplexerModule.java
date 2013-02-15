package group1.project.synthlab.module.multiplexer;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Tools;
import group1.project.synthlab.unitExtension.filter.filterAttenuator.FilterAttenuator;

import com.jsyn.unitgen.PassThrough;

/**
 * Module multiplexer
 * Fusionne un signal sur les port d'entrees
 * Replique le signal en sortie
 * @author Groupe 1
 * 
 */
public class MultiplexerModule extends Module implements IMultiplexerModule {

	private static final long serialVersionUID = -5543487526179911346L;

	protected static int moduleCount = 0;

	/** Port d'entree 1 */
	protected IInPort inPort1;
	
	/** Port d'entree 2 */
	protected IInPort inPort2;
	
	/** Port d'entree 3 */
	protected IInPort inPort3;
	
	/** Port d'entree 4 */
	protected IInPort inPort4;
	
	/** Port de sortie 1 */
	protected IOutPort outPort1;
	
	/** Port de sortie 2 */
	protected IOutPort outPort2;
	
	/** Port de sortie 3 */
	protected IOutPort outPort3;
	
	/** Port de sortie 4 */
	protected IOutPort outPort4;

	/** Filtres d'attenuation pour les ports d'entree */
	protected transient FilterAttenuator[] attenuators;
	
	/** Les valeurs d'attenuation en dB */
	protected double[] attenuationsDB;

	/** Mixeur interne */
	private PassThrough passThrough;

	public MultiplexerModule(Factory factory) {
		super("Multiplexer-" + ++moduleCount, factory);

		/* Intialise le mixeur interne */
		passThrough = new PassThrough();
		circuit.add(passThrough);

		/* Initialise les attenuations avec les filtres correspondant */
		attenuators = new FilterAttenuator[4];
		attenuationsDB = new double[4];		
		for (int i = 0; i < 4; ++i) {
			attenuators[i] = new FilterAttenuator();
			attenuators[i].output.connect(passThrough.input);
			circuit.add(attenuators[i]);
		}
		
		/* Initialise les ports du module */
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
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.Module#stop()
	 */
	@Override
	public void stop() {
		passThrough.output.setValueInternal(0);		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#refresh()
	 */
	@Override
	public void refresh() {
		for (int i = 0; i < 4; ++i) 
			setAttenuation(attenuationsDB[i], i);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#destruct()
	 */
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

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.multiplexer.IMultiplexerModule#getInPort(int)
	 */
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

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.multiplexer.IMultiplexerModule#getOutPort(int)
	 */
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
	 * @see group1.project.synthlab.module.multiplexer.IMultiplexerModule#setAttenuation(double, int)
	 */
	@Override
	public void setAttenuation(double db, int port) {
		if (port > attenuators.length)
			return;
		attenuationsDB[port] = db;
		attenuators[port].setAttenuation(Tools.dBToV(db) - 1);		
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#resetCounterInstance()
	 */
	@Override
	public void resetCounterInstance() {
		MultiplexerModule.moduleCount = 0;		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.multiplexer.IMultiplexerModule#getAttenuation(int)
	 */
	@Override
	public double getAttenuation(int port) {
		return attenuationsDB[port];
	}

}
