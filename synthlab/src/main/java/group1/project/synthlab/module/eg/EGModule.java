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

	/** Temps de montee en milliseconde*/
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
		envelope.hold.set(hold /1000.0);
		envelope.delay.set(0);
		
		gate = factory.createInPort("gate", envelope.amplitude, this);
		out =  factory.createOutPort("out", envelope.output, this);
		
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
	public void cableConnected(IPort port) {}

	// Fonction qui gere la deconnexion a l'entree FM
	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IVCOModule#cableDisconnected()
	 */
	public void cableDisconnected(IPort port) {}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#start()
	 */
	public void start() {
		// circuit.start();
		circuit.start();
		isOn = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#stop()
	 */
	public void stop() {
		circuit.stop();
		isOn = false;
	}

	
	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
		this.envelope.attack.set(attack);
	}

	public int getDecay() {
		return decay;
	}

	public void setDecay(int decay) {
		this.decay = decay;
		this.envelope.decay.set(decay);
	}

	public int getRelease() {
		return release;
	}

	public void setRelease(int release) {
		this.release = release;
		this.envelope.release.set(release);
	}

	public int getHold() {
		return hold;
	}

	public void setHold(int hold) {
		this.hold = hold;
		this.envelope.hold.set(hold);
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

		// On cree notre VCO et on ajoute le circuit cree au Synthesizer
		EGModule vco = (EGModule) factory.createVCOModule();
		synth.add(vco.getCircuit());
		vco.start();

		// LineOut remplace ici OutModule
		LineOut out = new LineOut();

		synth.add(out);
		out.start();

		// On connecte la sortie sinusoidale de notre VCO a la sortie

		out.input.connect(vco.getOutSine().getJSynPort());

		// On cree un VCO dont on va utiliser la sortie carree pour moduler la
		// frequence de notre premier vco
		EGModule fm = new EGModule(factory);
		synth.add(fm.getCircuit());
		fm.start();
		// la frequence du signal modulant doit etre faible pour que le
		// changement de frequence soit audible
		fm.squareOsc.frequency.set(0.5);
		// Les amplitudes en JSyn varient entre -1 et 1, ce qui correspond dans
		// notre modele a -5V +5V
		// Une amplitude de 0.2 correspond donc a une amplitude crete a crete de
		// 2V
		// Ainsi, en theorie, quand on passe d'un sommet a un creux, la
		// frequence du signal doit etre divisee par 2^2 et lorsqu'on passe d'un
		// creux a un sommet la frequence doit etre multipliee par 2^2.
		fm.squareOsc.amplitude.set(0.2);

		// Pour l'affichage des courbes
		AudioScope scope = new AudioScope(synth);
		scope.addProbe(vco.sineOsc.output);

		scope.setTriggerMode(AudioScope.TriggerMode.AUTO);
		scope.getModel().getTriggerModel().getLevelModel()
				.setDoubleValue(0.0001);
		scope.getView().setShowControls(true);
		scope.start();
		JFrame frame = new JFrame();
		frame.add(scope.getView());
		frame.pack();
		frame.setVisible(true);

		// Sans modulation de frequence pendant 6s
		// On verifie que notre VCO genere bien 3 signaux (carre, sinusoidale et
		// triangulaire)
		// 2s par signal, le changement de signal doit etre audible

		// Sinusoidale
		try {
			double time = synth.getCurrentTime();
			synth.sleepUntil(time + 2.0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Carree
		out.input.disconnectAll();
		out.input.connect(vco.getOutSquare().getJSynPort());

		try {
			double time = synth.getCurrentTime();
			synth.sleepUntil(time + 2.0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Triangulaire
		out.input.disconnectAll();
		out.input.connect(vco.getOutTriangle().getJSynPort());

		try {
			double time = synth.getCurrentTime();

			synth.sleepUntil(time + 2.0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Retour en sinusoidale
		out.input.disconnectAll();
		out.input.connect(vco.getOutSine().getJSynPort());

		// Avec modulation de frequence pendant quelques secondes
		// On connecte la sortie carree de notre VCO fm vers le port d'entree fm
		// de notre VCO vco
		fm.getOutSquare().getJSynPort().connect(vco.getFm().getJSynPort());

		vco.cableConnected(vco.getFm());

		// On verifie que la frequence est bien divisee par 2^2 ou multipliee
		// par
		// 2^2 quand on passe d'une crete a la suivante
		// Avec un signal modulant carre, la valeur de la frequence du signal
		// modulee va alternee entre 2 valeurs
		// Il suffit donc d'afficher ces valeurs pour verifier le rapport de 1 a
		// 4.
		int i = 0;
		while (i < 30) {
			System.out.println("Frequence = "
					+ vco.sineOsc.frequency.getValue());
			i++;
			try {
				synth.sleepUntil(synth.getCurrentTime() + 0.3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// On verifie que lorsque le signal modulant est nul, la frequence est
		// bien f0
		// On peut voir cela dans l'affichage ou en verifiant que la hauteur de
		// la note jouee est la meme qu'avant modulation.
		fm.squareOsc.amplitude.set(0);

		try {
			double time = synth.getCurrentTime();
			synth.sleepUntil(time + 2.0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Sans modulation de frequence le reste du temps, avec une frequence
		// reglee un peu plus haut
		fm.squareOsc.output.disconnectAll();
		vco.cableDisconnected(vco.getFm());
		vco.setf0(600);
		vco.changeFrequency();
	}

}
