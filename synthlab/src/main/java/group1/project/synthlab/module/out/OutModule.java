package group1.project.synthlab.module.out;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.unitExtensions.Attenuator.Attenuator;

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
public class OutModule extends Module implements IOutModule {

	public enum Distribution {
		NORMAL, DISTRIBUTED
	}

	protected static int moduleCount = 0;

	@SuppressWarnings("unused")
	private final double MAX_VOLTAGE = Signal.AMAX;

	/* jSyn module */
	protected LineOut lineOut;
	protected Attenuator attenuatorLeft;
	protected Attenuator attenuatorRight;

	/* Defintion des ports */
	protected IInPort leftPort;
	protected IInPort rightPort;

	/* Variables internes */
	private PassThrough passThroughLeft;
	private PassThrough passThroughRight;
	private boolean isOn;

	/* Proprietes du module */
	protected Distribution distribution;
	protected double attenuationDB;


	/**
	 * Initialise le circuit (attenuateur, port, ...)
	 */
	public OutModule(Factory factory) {
		super("Out-" + ++moduleCount, factory);
		lineOut = new LineOut();
		attenuatorLeft = new Attenuator();
		attenuatorRight = new Attenuator();

		// Ne pas ajouter LineOut au ciruit!
		// Bugs!
		circuit.add(attenuatorLeft);
		circuit.add(attenuatorRight);

		passThroughLeft = new PassThrough();
		passThroughRight = new PassThrough();

		setDistribution(Distribution.NORMAL);
		attenuationDB = 0;

		lineOut.input.setMaximum(1); // MAX_VOLTAGE
		lineOut.input.setMinimum(-1); // -MAX_VOLTAGE

		leftPort = factory.createInPort("Source left", attenuatorLeft.input,
				this);
		rightPort = factory.createInPort("Source right", attenuatorRight.input,
				this);

		attenuatorLeft.output.connect(passThroughLeft.input);
		attenuatorRight.output.connect(passThroughRight.input);

		isOn = false;

	}

	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#destruct()
	 */
	public void destruct() {
		if (rightPort.isUsed())
			rightPort.getCable().disconnect();
		
		if (leftPort.isUsed())
			leftPort.getCable().disconnect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * group1.project.synthlab.module.out.IOutModule#setDistribution(group1.
	 * project.synthlab.module.out.OutModule.Distribution)
	 */
	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
		passThroughLeft.output.disconnectAll();
		passThroughRight.output.disconnectAll();
		switch (this.distribution) {
		case NORMAL:
			passThroughLeft.output.connect(lineOut.input.getConnectablePart(0));
			passThroughRight.output
					.connect(lineOut.input.getConnectablePart(1));
			break;
		default:
			passThroughLeft.output.connect(lineOut.input.getConnectablePart(0));
			passThroughLeft.output.connect(lineOut.input.getConnectablePart(1));
			passThroughRight.output
					.connect(lineOut.input.getConnectablePart(0));
			passThroughRight.output
					.connect(lineOut.input.getConnectablePart(1));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.out.IOutModule#setAttenuation(double)
	 */
	public void setAttenuation(double db) {
		if (db > 12)
			db = 12;
		attenuationDB = db;
		double voltage = Math.pow(10.0, new Double(db) / 20.0);
		attenuatorLeft.setAttenuation(voltage - 1);
		attenuatorRight.setAttenuation(voltage - 1);
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
	public LineOut getLineOut() {
		return lineOut;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.out.IOutModule#getLeftPort()
	 */
	public IInPort getLeftPort() {
		return leftPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.out.IOutModule#getRightPort()
	 */
	public IInPort getRightPort() {
		return rightPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.out.IOutModule#getDistribution()
	 */
	public Distribution getDistribution() {
		return distribution;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#start()
	 */
	public void start() {
		circuit.start();
		lineOut.setSynthesisEngine(circuit.getSynthesisEngine());
		lineOut.start();
		isOn = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#stop()
	 */
	public void stop() {
		lineOut.stop();
		circuit.stop();
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

	// Test fonctionnel
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// Creeation de la factory
		Factory factory = new Factory();

		// Creation d'un module de sortie
		OutModule out = new OutModule(factory);

		// Creation du synthetiseur
		Synthesizer synth = JSyn.createSynthesizer();
		synth.start();

		// Creation d'oscillateurs arbitraire
		SineOscillator oscS = null;
		synth.add(oscS = new SineOscillator());
		TriangleOscillator oscT = null;
		synth.add(oscT = new TriangleOscillator());

		// Ajout de notre module au synthetiseur et connexion aux oscillateur
		synth.add(out.getCircuit());
		oscS.output.connect(out.getLeftPort().getJSynPort());
		oscT.output.connect(out.getRightPort().getJSynPort());

		// Reglage des oscillateurs
		oscS.frequency.set(200.0);
		oscS.amplitude.set(1);
		oscT.frequency.set(300.0);
		oscT.amplitude.set(1);

		// Vue graphique
		AudioScope scope = new AudioScope(synth);
		scope.addProbe(oscS.output);
		scope.addProbe(oscT.output);
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

		// Debut du test
		try {
			out.start();
			System.out.println("Demarrage...");
			Thread.sleep(2000);
			System.out
					.println("Resultat attendu freq 300 oscillation sinusoidale a gauche et triangle freq 600 a droite");
			out.setDistribution(Distribution.NORMAL);
			oscS.amplitude.set(0);
			Thread.sleep(2000);
			oscS.amplitude.set(1);
			oscT.amplitude.set(0);
			Thread.sleep(2000);
			oscT.amplitude.set(1);
			Thread.sleep(2000);
			System.out.println("Mixage des ports");
			out.setDistribution(Distribution.DISTRIBUTED);
			Thread.sleep(2000);
			System.out.println("+12db");
			out.setAttenuation(12);
			Thread.sleep(2000);
			System.out.println("-20db");
			out.setAttenuation(-20);
			Thread.sleep(2000);
			System.out.println("Stop");
			out.stop();
			Thread.sleep(3000);
			System.out.println("Start");
			out.start();
			out.setAttenuation(0);
			Thread.sleep(3000);
		} catch (Exception e) {

		}

	}

}
