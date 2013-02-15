package group1.project.synthlab.module.micro;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Tools;
import group1.project.synthlab.unitExtension.filter.filterAttenuator.FilterAttenuator;

import com.jsyn.unitgen.LineIn;

/**
 * Module du microphone
 * 
 * @author Groupe 1
 * 
 */
public class MicroModule extends Module implements IMicroModule {

	private static final long serialVersionUID = 1204424552539500533L;

	protected static int moduleCount = 0;

	/** Module JSyn d'entree */
	protected transient LineIn lineIn;
	
	/** Filtre d'attenuation sur l'entree */
	protected transient FilterAttenuator attenuator;

	/** Port de sortie */
	protected IOutPort outPort;

	/** Attenuation en dB apportee en entree */
	protected double attenuationDB;

	public MicroModule(Factory factory) {
		super("Micro-" + ++moduleCount, factory);
		
		/* Initialise les module JSyn */
		lineIn = new LineIn();
		attenuator = new FilterAttenuator();

		/* Ajout au circuit */
		circuit.add(attenuator);
		circuit.add(lineIn);
		attenuationDB = 0;

		/* Initialise le port de sortie */
		outPort = factory.createOutPort("out", attenuator.output, this);

		/* Connexion */
		attenuator.input.connect(lineIn.output);
		lineIn.setEnabled(false);

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#refresh()
	 */
	@Override
	public void refresh() {
		setAttenuation(attenuationDB);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#destruct()
	 */
	public void destruct() {

		if (outPort.isUsed())
			outPort.getCable().disconnect();
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.micro.IMicroModule#getOutPort()
	 */
	public IOutPort getOutPort() {
		return outPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.out.IOutModule#setAttenuation(double)
	 */
	public void setAttenuation(double db) {
		if (attenuationDB > 12)
			attenuationDB = 12;
		this.attenuationDB = db;
		// On convertie la valeur en dB en volt et on retranche la tension
		// nominale (1 en JSyn) pour obtenir une attenuation
		attenuator.setAttenuation(Tools.dBToV(db) - 1);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.out.IOutModule#getAttenuation()
	 */
	public double getAttenuation() {
		return attenuationDB;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.out.IOutModule#getLineOut()
	 */
	public LineIn getLineIn() {
		return lineIn;
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
	 * @see group1.project.synthlab.module.IModule#resetCounterInstance()
	 */
	@Override
	public void resetCounterInstance() {
		MicroModule.moduleCount = 0;		
	}

	public static void main(String[] args) {

	}

}
