package group1.project.synthlab.module;

import javax.swing.JFrame;

import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.unitExtensions.Attenuator;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.SineOscillator;

public class OutModule extends Module implements IModule {

	/**
	 * Module de sortie
	 * @author Groupe 1
	 *
	 */
	public enum Distribution {
		MONO, STEREO
	}
	
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
		
		//Ne pas ajouter LineOut au ciruit!
		//Bugs!
		circuit.add(attenuatorLeft);
		circuit.add(attenuatorRight);
		
		passThroughLeft = new PassThrough();
		passThroughRight = new PassThrough();	
		 
		setDistribution(Distribution.MONO);

		lineOut.input.setMaximum(1); //MAX_VOLTAGE
		lineOut.input.setMinimum(-1); //-MAX_VOLTAGE

		leftPort = new InPort("Source left", attenuatorLeft.input);
		rightPort = new InPort("Source right", attenuatorRight.input);
		
		attenuatorLeft.output.connect(passThroughLeft.input);
		attenuatorRight.output.connect(passThroughRight.input);

		isOn = false;
	}
	
	/**
	 * Mise à jour de la distribution (en mono ou en stero)
	 * @param distribution
	 */
	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
		passThroughLeft.output.disconnectAll();
		passThroughRight.output.disconnectAll();
		switch (this.distribution) {
		case MONO:
			passThroughLeft.output.connect(lineOut.input.getConnectablePart(0));
			passThroughRight.output.connect(lineOut.input.getConnectablePart(1));
			break;
		default:
			passThroughLeft.output.connect(lineOut.input.getConnectablePart(0));
			passThroughLeft.output.connect(lineOut.input.getConnectablePart(1));
			passThroughRight.output.connect(lineOut.input.getConnectablePart(0));
			passThroughRight.output.connect(lineOut.input.getConnectablePart(1));
		}
	}

	/**
	 * Formule pour attenuer le son
	 * 10 ^ (valeurEnDB / 20) * tensionNominale
	 * @param db (valeur max : 12 dB)
	 */
	public void mitigate(double db) {
		if (db > 12) 
			db = 12;
		double voltage = Math.pow(10.0, db / 20.0) * MAX_VOLTAGE; 
		attenuatorLeft.setAttenuation(voltage / MAX_VOLTAGE - 1);
		attenuatorRight.setAttenuation(voltage / MAX_VOLTAGE - 1);
		System.out.println(voltage / MAX_VOLTAGE  - 1);
		//System.out.println(voltage);
	}

	/**
	 *
	 * @return
	 */
	public LineOut getLineOut() {
		return lineOut;
	}

	/**
	 * Retourne le port gauche en entree
	 * @return
	 */
	public InPort getLeftPort() {
		return leftPort;
	}
	
	/**
	 * Retourne le port droit en entree
	 * @return
	 */
	public InPort getRightPort() {
		return rightPort;
	}

	/**
	 * Retourne la distribution (mono ou stereo)
	 * @return
	 */
	public Distribution getDistribution() {
		return distribution;
	}

	/**
	 * Demarre le circuit
	 */
	public void start() {
		circuit.start();
		lineOut.setSynthesisEngine(circuit.getSynthesisEngine());
		lineOut.start();		
		isOn = true;
	}

	/**
	 * Arrete le circuit
	 */
	public void stop() {
		circuit.stop();
		lineOut.stop();
		isOn = false;
	}

	/**
	 * Donne l'etat du bouton Mute
	 * @return true si le circuit est en marche, faux sinon (le son est coupe) 
	 */
	public boolean isStarted() {
		return isOn;
	}

	public static void main(String[] args) {
		OutModule out = new OutModule();
		
		// Create a context for the synthesizer.
		Synthesizer synth = JSyn.createSynthesizer();

		synth.start();
		
		SineOscillator osc;
		// Add a tone generator.
		synth.add( osc = new SineOscillator() );
		// Add a stereo audio output unit.
		synth.add(out.getCircuit());
		osc.output.connect(out.getLeftPort().getJSynPort());
		// Set the frequency and amplitude for the sine wave.
		osc.frequency.set(300.0);		
		osc.amplitude.set(1);
		out.mitigate(-6);	
		
		out.start();
		
	
		
		 AudioScope scope= new AudioScope( synth );
		scope.addProbe( osc.output );
		scope.setTriggerMode( AudioScope.TriggerMode.AUTO );
		
		scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.0001 );
	
		scope.getView().setShowControls( true );
		
		scope.start();
		

		
		JFrame frame = new JFrame();
		frame.add(scope.getView());
		frame.pack();
		frame.setVisible(true);
				
				
	}
}
