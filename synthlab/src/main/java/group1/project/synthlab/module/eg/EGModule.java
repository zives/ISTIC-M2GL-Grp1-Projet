package group1.project.synthlab.module.eg;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.IPortObserver;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.signal.Tools;
import group1.project.synthlab.unitExtensions.FilterAmplitude.FilterAmplitude;
import group1.project.synthlab.unitExtensions.FilterAttenuator.FilterFrequencyModulation;

import javax.swing.JFrame;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;

/**
 * Module VCO
 * 
 * @author Groupe 1
 * 
 */
/**
 * @author 10008808
 * 
 */
public class EGModule extends Module implements IPortObserver, IEGModule {

	protected static int moduleCount = 0;

	/** Port d'entree : une entree de declenchement */
	protected IInPort gate;

	/** Pour de sortie pour le signal out */
	protected IOutPort out;

	/** Temps de montee en milliseconde */
	protected int attack;
	/** Temps de maintien en milliseconde */
	protected int decay;
	/** Temps de relachement en milliseconde */
	protected int release;
	/** Temps entre attack et decay */
	protected int hold;
	/** amplitude comprise entre 0(pas d'atténuation et moins 60) */
	protected double sustain;

	protected EnvelopeDAHDSR envelope;
	/** Etat du module (allume ou eteint) */
	protected boolean isOn;

	/**
	 * 
	 */
	public EGModule(Factory factory) {
		super("EG-" + ++moduleCount, factory);
		this.attack = 1000;
		this.decay = 1000;
		this.release = 1000;
		this.sustain = 0.3;
		this.hold = 0;
	
		envelope = new EnvelopeDAHDSR();

		circuit.add(envelope);

		// On regle les frequences des oscillateurs aux valeurs par defaut
		envelope.sustain.set(Tools.dBToV(sustain));
		envelope.attack.set(attack / 1000.0);
		envelope.decay.set(decay / 1000.0);
		envelope.release.set(release / 1000.0);
		envelope.hold.set(hold / 1000.0);
		envelope.delay.set(0);
		
		envelope.attack.setMinimum(0);
		envelope.attack.setMaximum(5);
		envelope.decay.setMinimum(0);
		envelope.decay.setMaximum(5);
		envelope.release.setMinimum(0);
		envelope.release.setMaximum(5);
		envelope.hold.setMinimum(0);
		envelope.hold.setMaximum(5);

		gate = factory.createInPort("gate", envelope.input, this);
		out = factory.createOutPort("out", envelope.output, this);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#destruct()
	 */
	public void destruct() {
		if (gate.isUsed())
			gate.getCable().disconnect();
		if (out.isUsed())
			out.getCable().disconnect();
	}

	// Fonction appelee lorsque les reglages sont modifiees sur l'IHM
	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IVCOModule#changeFrequency()
	 */

	// Fonction qui gere la connexion a l'entree FM, et donc le passage a la
	// modulation de frequence par un signal en entree
	// La fonction sera donc appelee lorsqu'on connectera un cable au port fm
	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IVCOModule#cableConnected()
	 */
	public void cableConnected(IPort port) {
	}

	// Fonction qui gere la deconnexion a l'entree FM
	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IVCOModule#cableDisconnected()
	 */
	public void cableDisconnected(IPort port) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#start()
	 */
	public void start() {
		 circuit.start();
		circuit.start();
		envelope.start();
	
		isOn = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#stop()
	 */
	public void stop() {
		circuit.stop();
		envelope.stop();
		isOn = false;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
		this.envelope.attack.set(attack / 1000.0);
	}

	public int getDecay() {
		return decay;
	}

	public void setDecay(int decay) {
		this.decay = decay;
		this.envelope.decay.set(decay / 1000.0);
	}

	public int getRelease() {
		return release;
	}

	public void setRelease(int release) {
		this.release = release;
		this.envelope.release.set(release / 1000.0);
	}

	public int getHold() {
		return hold;
	}

	public void setHold(int hold) {
		this.hold = hold;
		this.envelope.hold.set(hold / 1000.0);
	}

	public double getSustain() {
		return sustain;
	}

	public void setSustain(double sustain) {
		this.sustain = sustain;
		envelope.sustain.set(Tools.dBToV(sustain));
	}

	public IInPort getGate() {
		return gate;
	}

	public IOutPort getOut() {
		return out;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#isStarted()
	 */
	public boolean isStarted() {
		return isOn;
	}

	// Tests fonctionnels
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Throwable {
		// Factory
		Factory factory = new Factory();

		// On cree et demarre le Synthesizer
		Synthesizer synth = JSyn.createSynthesizer();
		synth.start();

		// On cree notre eg et on ajoute le circuit cree au Synthesizer
		EGModule eg = (EGModule) factory.createEGModule();
		synth.add(eg.getCircuit());
		eg.start();
		
		// LineOut remplace ici OutModule
		LineOut out = new LineOut();
		synth.add(out);
		out.start();

		// Creation d'oscillateurs arbitraire
		SineOscillator oscS = null;
		synth.add(oscS = new SineOscillator());
		
		oscS.frequency.set(700);
		oscS.amplitude.set(1);
		oscS.start();
		
		//Filte amplitude
		FilterAmplitude filter = new FilterAmplitude(5, false);
		
		filter.input.connect(eg.getOut().getJSynPort());
		filter.output.connect(oscS.amplitude);
		//connecte la sortie de l oscillateur a l entree du module eg
		//eg.getOut().getJSynPort().connect(oscS.amplitude);
		
		SquareOscillator oscC = null;
		synth.add(oscC = new SquareOscillator());
		oscC.output.connect(eg.getGate().getJSynPort());
		oscC.frequency.set(1/10);
		oscC.amplitude.set(1);
		oscC.start();
	//((UnitInputPort)	eg.getGate().getJSynPort()).set(1);
		
		
		// On connecte la sortie de notre eg a la sortie
		out.input.connect(oscS.output);
		
		//eg.envelope.frequence.set(0.1);
		
		// Pour l'affichage des courbes
		AudioScope scope = new AudioScope(synth);
		scope.addProbe(oscS.output);
		
		scope.setTriggerMode(AudioScope.TriggerMode.AUTO);
		scope.getModel().getTriggerModel().getLevelModel()
				.setDoubleValue(0.0001);
		scope.getView().setShowControls(true);
		scope.start();


		JFrame frame = new JFrame();
		frame.add(scope.getView());
		frame.pack();
		frame.setVisible(true);
		
		eg.setAttack(3000);
		eg.setHold(4000);
		eg.setDecay(10000);
		eg.setRelease(20000);
		eg.setSustain(-20);
	


	}
}
