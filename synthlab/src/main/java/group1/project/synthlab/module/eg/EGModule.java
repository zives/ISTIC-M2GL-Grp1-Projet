package group1.project.synthlab.module.eg;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.IPortObserver;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Tools;
import group1.project.synthlab.unitExtension.envelope.Envelope;
import javax.swing.JFrame;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;

/**
 * Module EG
 * 
 * @author Groupe 1
 * 
 */
public class EGModule extends Module implements IPortObserver, IEGModule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5037197385996670646L;

	protected static int moduleCount = 0;

	/** Port d'entree : une entree de declenchement */
	protected IInPort gate;

	/** Port de sortie out */
	protected IOutPort out;

	/** Temps de montee en secondes */
	protected double attack = 0.2;
	/** Temps de declin en secondes */
	protected double decay = 0.2;
	/** Temps de relachement en secondes */
	protected double release = 0.3;
	/** Temps entre attack et decay */
	protected double hold = 0;
	/** attenuation du niveau maximum atteint en fin de phase de montee, comprise entre 0 et -60dB */
	protected double sustain;
	
	/** sauvegarde de sustain en decibel et en volt */
	protected double decibel = -2;
	protected double volt;

	/** Le generateur d'enveloppe */
	protected transient Envelope envelope;

	/**
	 * Constructeur : initialise l'EG (ports, valeurs par defaut des parametres...)
	 */
	public EGModule(Factory factory) {
		super("EG-" + ++moduleCount, factory);
	
		envelope = new Envelope();

		circuit.add(envelope);

		// On regle l'eg aux valeurs par defaut
		envelope.sustain.set(Tools.dBToV(decibel));
		this.decibel = getDecibel();
		this.volt = getVolt();
		envelope.sustain.set(Tools.dBToV(decibel));
		envelope.attack.set(attack);
		envelope.decay.set(decay);
		envelope.release.set(release);
		envelope.hold.set(hold);
		envelope.attack.setMinimum(0);
		envelope.attack.setMaximum(2);
		envelope.decay.setMinimum(0);
		envelope.decay.setMaximum(2);
		envelope.release.setMinimum(0);
		envelope.release.setMaximum(2);
		envelope.hold.setMinimum(0);
		envelope.hold.setMaximum(2);

		gate = factory.createInPort("gate", envelope.input, this);
		out = factory.createOutPort("out", envelope.output, this);
		
		sustain = getVolt();
		
		envelope.setEnabled(false);
		
	}
	

	@Override
	public void refresh() {
		envelope.sustain.set(Tools.dBToV(decibel));
		envelope.attack.set(attack);
		envelope.decay.set(decay);
		envelope.release.set(release);
		envelope.hold.set(hold);
	}

	private double getVolt() {
		return Tools.dBToV(decibel);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#cableConnected()
	 */
	public void cableConnected(IPort port) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#cableDisconnected()
	 */
	public void cableDisconnected(IPort port) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#getEnvelope()
	 */
	public Envelope getEnvelope() {
		return envelope;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#getAttack()
	 */
	public double getAttack() {
		return attack;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#setAttack()
	 */
	public void setAttack(double attack) {
		this.attack = attack;
		this.envelope.attack.set(attack);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#getDecay()
	 */
	public double getDecay() {
		return decay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#setDecay()
	 */
	public void setDecay(double decay) {
		this.decay = decay;
		this.envelope.decay.set(decay);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#getRelease()
	 */
	public double getRelease() {
		return release;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#setRelease()
	 */
	public void setRelease(double release) {
		this.release = release;
		this.envelope.release.set(release);
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#getHold()
	 */
	public double getHold() {
		return hold;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#setHold()
	 */
	public void setHold(double hold) {
		this.hold = hold;
		this.envelope.hold.set(hold);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#getSustain()
	 */
	public double getSustain() {
		return sustain;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#setSustain()
	 */
	public void setSustain(double decibel) {
		this.decibel = decibel;
		this.sustain = Tools.dBToV(decibel);
		volt = sustain;
		envelope.sustain.set(sustain);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#getGate()
	 */
	public IInPort getGate() {
		return gate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IEGModule#getOut()
	 */
	public IOutPort getOut() {
		return out;
	}
	
	public double getDecibel(){
		return this.decibel;
	}

	
	@Override
	public void resetCounterInstance() {
		EGModule.moduleCount = 0;		
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
		eg.setAttack(0.2);
		eg.setDecay(0.2);
		eg.setRelease(1);
		eg.setSustain(-6);
		eg.start();

		// LineOut remplace ici OutModule
		LineOut out = new LineOut();
		synth.add(out);
		out.start();

		// On cree l'oscillateur qui sera connecte a l'entree gate de l'EG
		SquareOscillator oscGate = new SquareOscillator();
		oscGate.frequency.set(0.5);		// La frequence doit etre tres faible pour pouvoir observer la sortie de l'enveloppe a chaque front montant
		oscGate.amplitude.set(1);
		synth.add(oscGate);
		oscGate.start();
		oscGate.output.connect(eg.getGate().getJSynPort());
		
		// On cree l'oscillateur dont l'amplitude sera controlee par l'enveloppe
		SineOscillator osc = new SineOscillator();
		osc.frequency.set(440);
		eg.getOut().getJSynPort().connect(osc.amplitude);
		out.input.connect(osc.output);
		synth.add(osc);
		osc.start();
		
		// Pour l'affichage des courbes
		AudioScope scope = new AudioScope(synth);
		scope.addProbe(osc.output);
		
		scope.setTriggerMode(AudioScope.TriggerMode.AUTO);
		scope.getModel().getTriggerModel().getLevelModel()
				.setDoubleValue(0.0001);
		scope.getView().setShowControls(true);
		scope.start();
		JFrame frame = new JFrame();
		frame.add(scope.getView());
		frame.pack();
		frame.setVisible(true);
        
		System.out.println("\n\n\n****************************************************\n");
		System.out.println("Tests fonctionnels du Module EG");
		System.out.println("\n****************************************************\n\n");
		
		System.out.println("\n\nTest Module EG, avec differents parametres");
		
		System.out.println("\nAttack = 0.2s, Decay = 0.2s, Sustain = -6dB, Release = 1s");
		Tools.wait(synth, 10);
		
		System.out.println("\nAttack = 1s, Decay = 0.2s, Sustain = -6dB, Release = 1s");
		eg.setAttack(1);
		Tools.wait(synth, 10);
		
		System.out.println("\nAttack = 0.2s, Decay = 1s, Sustain = -6dB, Release = 1s");
		eg.setAttack(0.2);
		eg.setDecay(1);
		Tools.wait(synth, 10);
		
		System.out.println("\nAttack = 0.2s, Decay = 0.2s, Sustain = -24dB, Release = 1s");
		eg.setDecay(0.2);
		eg.setSustain(-24);
		Tools.wait(synth, 10);
		
		System.out.println("\nAttack = 0.2s, Decay = 0.2s, Sustain = -6dB, Release = 5s");
		eg.setSustain(-6);
		eg.setRelease(10);
		Tools.wait(synth, 10);
	}


}
