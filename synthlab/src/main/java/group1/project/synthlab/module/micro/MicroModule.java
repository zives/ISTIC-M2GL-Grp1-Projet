package group1.project.synthlab.module.micro;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.module.vco.VCOModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.signal.Tools;
import group1.project.synthlab.unitExtension.filter.filterAttenuator.FilterAttenuator;

import javax.swing.JFrame;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineIn;
import com.jsyn.unitgen.LineOut;

/**
 * Module de sortie
 * 
 * @author Groupe 1
 * 
 */
public class MicroModule extends Module implements IMicroModule {

	protected static int moduleCount = 0;

	@SuppressWarnings("unused")
	private final double MAX_VOLTAGE = Signal.AMAX;

	/* jSyn module */
	protected transient LineIn lineIn;
	protected transient FilterAttenuator attenuator;

	/* Defintion des ports */
	protected IOutPort outPort;

	/* Proprietes du module */
	protected double attenuationDB;

	/**
	 * Initialise le circuit (attenuateur, port, ...)
	 */
	public MicroModule(Factory factory) {
		super("Micro-" + ++moduleCount, factory);
		lineIn = new LineIn();
		attenuator = new FilterAttenuator();

		// Ne pas ajouter LineOut au ciruit!
		// Bugs!
		circuit.add(attenuator);
		circuit.add(lineIn);
		attenuationDB = 0;

		outPort = factory.createOutPort("out", attenuator.output, this);

		attenuator.input.connect(lineIn.output);
		lineIn.setEnabled(false);

	}
	
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

	public void cableConnected(IPort port) {
	}

	public void cableDisconnected(IPort port) {

	}
	
	@Override
	public void resetCounterInstance() {
		MicroModule.moduleCount = 0;		
	}

	public static void main(String[] args) {
		

		// Creation du synthetiseur
		Synthesizer synth = JSyn.createSynthesizer();
		synth.start();
		

		// Creation d'un module de sortie
		LineOut out = new LineOut();

		// Creation d'oscillateurs arbitraire
		LineIn li = null;
		synth.add(li = new LineIn());

		// Ajout de notre module au synthetiseur et connexion aux oscillateur
		synth.add(out);
		li.output.connect(out.input);
		out.start();
	
		// Vue graphique
		AudioScope scope = new AudioScope(synth);
		scope.addProbe(li.output);
		scope.setTriggerMode(AudioScope.TriggerMode.AUTO);

		scope.getModel().getTriggerModel().getLevelModel()
				.setDoubleValue(0.0001);
		scope.getView().setShowControls(true);
		scope.start();

		// Fenetre
		JFrame frame = new JFrame();
		frame.add(scope.getView());
		frame.pack();
		frame.setVisible(true);

	}

}
