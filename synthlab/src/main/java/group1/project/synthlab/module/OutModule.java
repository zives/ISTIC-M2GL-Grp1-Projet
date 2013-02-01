package group1.project.synthlab.module;

import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.unitExtensions.Attenuator;

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
public class OutModule extends Module implements IModule {

	public enum Distribution {
		NORMAL, DISTRIBUTED
	}

	@SuppressWarnings("unused")
	private final double MAX_VOLTAGE = 5;

	/* jSyn module */
	protected LineOut lineOut;
	protected Attenuator attenuatorLeft;
	protected Attenuator attenuatorRight;

	/* Défintion des ports */
	protected InPort leftPort;
	protected InPort rightPort;

	/* Variables internes */
	private PassThrough passThroughLeft;
	private PassThrough passThroughRight;
	private boolean isOn;

	/* Propriétés du module */
	Distribution distribution;

	/**
	 * Initialise le circuit (attenuateur, port, ...)
	 */
	public OutModule() {
		super("Out-" + moduleCount);
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

		lineOut.input.setMaximum(1); // MAX_VOLTAGE
		lineOut.input.setMinimum(-1); // -MAX_VOLTAGE

		leftPort = new InPort("Source left", attenuatorLeft.input);
		rightPort = new InPort("Source right", attenuatorRight.input);

		attenuatorLeft.output.connect(passThroughLeft.input);
		attenuatorRight.output.connect(passThroughRight.input);

		isOn = false;
	}

	/**
	 * Mise à jour de la distribution du son
	 * 
	 * @param distribution
	 *            NORMAL, le son ne sort que sur le haut parleur correspond au
	 *            cable du port (gauche ou droite); DISTRIBUTED, le son de
	 *            chacun des ports est envoyé sur les deux hauts parleurs. Si
	 *            les deux ports sont occupés, le son est mixé/fusionné sur les
	 *            deux hauts parleurs.
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

	/**
	 * Atténuation du son Formule pour attenuer le son 10 ^ (valeurEnDB / 20) *
	 * tensionNominale http://fr.wikipedia.org/wiki/Niveau_(audio)
	 * 
	 * @param db
	 *            (valeurs : -inf à +12 dB)
	 */
	public void setAttenuation(double db) {
		if (db > 12)
			db = 12;
		double voltage = Math.pow(10.0, new Double(db) / 20.0);
		attenuatorLeft.setAttenuation(voltage - 1);
		attenuatorRight.setAttenuation(voltage - 1);
	}

	/**
	 * 
	 * @return the amplitude attenuation
	 */
	public double getAttenuation() {
		return attenuatorLeft.getAttenuation();
	}

	/**
	 * 
	 * @return l'objet JSyn correspondant à la sortie brute (son non filtré)
	 */
	public LineOut getLineOut() {
		return lineOut;
	}

	/**
	 * @return Le port d'entrée gauche
	 */
	public InPort getLeftPort() {
		return leftPort;
	}

	/**
	 * @return Retourne le port d'entrée droite
	 */
	public InPort getRightPort() {
		return rightPort;
	}

	/**
	 * @return Retourne la distribution du son
	 */
	public Distribution getDistribution() {
		return distribution;
	}

	/**
	 * Démarre le circuit
	 */
	public void start() {
		circuit.start();
		lineOut.setSynthesisEngine(circuit.getSynthesisEngine());
		lineOut.start();
		isOn = true;
	}

	/**
	 * Arrête le circuit
	 */
	public void stop() {
		lineOut.stop();
		circuit.stop();
		isOn = false;
	}

	/**
	 * Donne l'état du bouton Mute
	 * 
	 * @return true si le circuit est en marche, faux sinon (le son est coupé)
	 */
	public boolean isStarted() {
		return isOn;
	}

	// Test fonctionnel
	public static void main(String[] args) {

		// Creation d'un module de sortie
		OutModule out = new OutModule();

		// Creation du synthétiseur
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

		// Début du test
		try {
			out.start();
			System.out.println("Demarrage...");
			Thread.sleep(2000);
			System.out
					.println("Resultat attendu freq 300 oscillation sinusoidale à gauche et triangle freq 600 à droite");
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
