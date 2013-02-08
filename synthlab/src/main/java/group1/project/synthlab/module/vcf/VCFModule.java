package group1.project.synthlab.module.vcf;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.IPortObserver;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Tools;
import group1.project.synthlab.unitExtensions.FilterAmplitude.FilterRecordMinMaxAmplitude;
import group1.project.synthlab.unitExtensions.FilterFrequency.FilterFrequencyModulation;
import javax.swing.JFrame;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;

/**
 * Module VCF
 * 
 * @author Groupe 1
 * 
 */
public class VCFModule extends Module implements IPortObserver, IVCFModule {

	protected static int moduleCount = 0;

	/** Modulation de frequence connectee ou pas */
	protected boolean fmConnected;
	
	/** Frequence de coupure min */
	public static final double fmin = 0;
	/** Frequence de coupure max */
	public static final double fmax = 6000;
	/** Frequence de coupure */
	protected double f0 = 880;
	
	/** Facteur de qualite */
	protected double q = 1;
	
	/** Filtres JSyn : on doit utiliser 2 filtres en serie pour avoir du 24dB/octave */
	protected FilterLowPass filter1;
	protected FilterLowPass filter2;
	
	/** Reglage grossier de la frequence de coupure : entier de 0 a 9 */
	protected int coarseAdjustment; // entre 0 et 9 
	/** Reglage fin de la frequence de coupure : double entre 0 et 1 */
	protected double fineAdjustment; // entre 0 et 1
	
	/** Port d'entree : entree de signal */
	protected IInPort in;

	/** Port d'entree : modulation de frequence */
	protected IInPort fm;
	
	/** Port de sortie : signal filtre */
	protected IOutPort out;
	
	/**
	 * Filtre pour appliquer la formule de modulation de frequence de coupure : fc = f0 * 2^(Vfm)
	 */
	protected FilterFrequencyModulation filterFrequencyModulation;
	
	/**
	 * Filtre pour recuperer les valeurs max et min dd'un signal
	 */
	protected FilterRecordMinMaxAmplitude filterPrintMinMaxAmplitude;
	
	/** Pour le on/off */
	protected Multiply onoff = new Multiply();
	
	/** Etat du module (allume ou eteint) */
	protected boolean isOn;
	
	/**
	 * Constructeur : initialise le VCF (, port, ...)
	 */
	public VCFModule(Factory factory) {
		super("VCF-" + ++moduleCount, factory);
		
		filterFrequencyModulation = new FilterFrequencyModulation(f0); // Filtre de modulation de la frequence de coupure
		filterPrintMinMaxAmplitude = new FilterRecordMinMaxAmplitude(); // Filtre pour afficher les valeurs min et max d'amplitude
		circuit.add(filterFrequencyModulation);
		circuit.add(filterPrintMinMaxAmplitude);
		
		// Les filtres (ici : passe-bas)
		// TODO : proposer d'autres types de filtres
		filter1 = new FilterLowPass();
		filter1.Q.set(0.7);
		circuit.add(filter1);
		filter2 = new FilterLowPass();
		filter2.Q.set(1);
		circuit.add(filter2);
		filter1.output.connect(filter2.input);
		filter2.output.connect(filterPrintMinMaxAmplitude.input); // Pour afficher les valeurs min et max d'amplitude
		filterPrintMinMaxAmplitude.output.connect(onoff.inputA);
		
		// On applique la formule f0 * 2 ^ (5Vfm) au signal en entree fm et on envoie la sortie dans un passThrough
		// Cette formule garantit egalement que si un signal nul est connecte en entree, la frequence de coupure vaudra f0
		// On doit multiplier Vfm par 5 car JSyn considere des amplitudes entre -1 et 1, et nous considerons des tensions entre -5V et +5V)
		filterFrequencyModulation.output.connect(filter1.frequency);
		filterFrequencyModulation.output.connect(filter2.frequency);
		
		// Port d'entree : 
		in = factory.createInPort("in", filter1.input, this);
		fm = factory.createInPort("fm", filterFrequencyModulation.input, this);

		// Ports de sortie
		out = factory.createOutPort("out", onoff.output, this);
		
		// Lorsqu'il est cree, le VCF est eteint, on ne laisse donc passer aucun signal
		onoff.inputB.set(0);
		isOn = false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#destruct()
	 */
	public void destruct() {
		if (out.isUsed())
			out.getCable().disconnect();
		if (in.isUsed())
			in.getCable().disconnect();
		if (fm.isUsed())
			fm.getCable().disconnect();
	}
	
	// Fonction appelee lorsque les reglages de la frequence de coupure sont modifiees sur l'IHM
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCFModule#changeFrequency()
	 */
	public void changeFrequency(){
		double newFrequency = (coarseAdjustment + fineAdjustment) * ((fmax - fmin) / 10);
		f0 = newFrequency;
		filterFrequencyModulation.setf0(f0);
	}
	
	// Fonction appelee lorsque les reglages du facteur de qualite sont modifiees sur l'IHM
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCFModule#changeQFactor()
	 */
	private void changeQFactor(){
		filter1.Q.set(q);
		filter2.Q.set(q);
	}
		
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#start()
	 */
	public void start() {
		onoff.inputB.set(1);
		isOn = true;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#stop()
	 */
	public void stop() {
		onoff.inputB.set(0);
		isOn = false;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCFModule#getf0()
	 */
	public double getf0() {
		return f0;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCFModule#getIn()
	 */
	public IInPort getIn() {
		return in;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCFModule#getFm()
	 */
	public IInPort getFm() {
		return fm;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCFModule#getOut()
	 */
	public IOutPort getOut() {
		return out;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#isStarted()
	 */
	public boolean isStarted() {
		return isOn;
	}

	// Fonction qui gere la connexion d'un cable a un port d'entree
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCFModule#cableConnected()
	 */
	public void cableConnected(IPort port) {
		if(port == in){
			System.out.println("Connexion d'un cable dans l'entree in");
		}
		else if(port == fm){
			System.out.println("Connexion d'un cable dans l'entree fm");
		}
	}

	// Fonction qui gere la deconnexion d'un cable d'un port d'entree
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCFModule#cableDisconnected()
	 */
	public void cableDisconnected(IPort port) {
		if(port == in){
			System.out.println("Deconnexion d'un cable de l'entree in");
		}
		else if(port == in){
			System.out.println("Deconnexion d'un cable de l'entree fm");
		}
	}
	
	// Tests fonctionnels
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		//Factory
		Factory factory = new Factory();
		
		// On cree et demarre le Synthesizer
		Synthesizer synth = JSyn.createSynthesizer();
		synth.start();
		
		// On cree notre VCF et on ajoute le circuit cree au Synthesizer
		VCFModule vcf = (VCFModule) factory.createVCFModule();
		synth.add(vcf.getCircuit());
		vcf.start();
		
		// On cree un oscillateur que l'on connectera dans l'entree in
		SineOscillator inOsc = new SineOscillator();
		inOsc.amplitude.set(1);
		inOsc.frequency.set(3520);
		synth.add(inOsc);
		
		// On cree un oscillateur que l'on connectera dans l'entree fm
		SquareOscillator fmOsc = new SquareOscillator();
		fmOsc.frequency.set(0.5);
		fmOsc.amplitude.set(0.2);
		synth.add(fmOsc);
				
		// LineOut remplace ici OutModule
		LineOut out = new LineOut();
		synth.add(out);
		out.start();
		
		// On connecte l'oscillateur inOsc a l'entree in du VCF
		inOsc.output.connect(vcf.getIn().getJSynPort());
		
		// On connecte la sortie du VCF au LineOut
		vcf.getOut().getJSynPort().connect(out.input);
		
		// Pour l'affichage des courbes
		AudioScope scope= new AudioScope( synth );
		scope.addProbe(vcf.onoff.output);
		scope.setTriggerMode( AudioScope.TriggerMode.AUTO );
		scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.0001 );
		scope.getView().setShowControls( true );
		scope.start();
		JFrame frame = new JFrame();
		frame.add(scope.getView());
		frame.pack();
		frame.setVisible(true);
		
		vcf.filterPrintMinMaxAmplitude.reset();
		// Sans modulation de frequence au debut, on compare 4 frequences de inOsc
		System.out.println("Sans modulation de signal, fc = 440 Hz, inOsc 20 Hz : le signal ne doit pas etre filtre (amplitude en sortie de 1)");
		Tools.wait(synth, 2);
		System.out.println("Frequence de coupure du filtre 1 : " + vcf.filter1.frequency.getValue());
		System.out.println("Frequence de coupure du filtre 2 : " + vcf.filter2.frequency.getValue());
		System.out.println("Amplitude min : " + vcf.filterPrintMinMaxAmplitude.getMin() + "\nAmplitude max : " + vcf.filterPrintMinMaxAmplitude.getMax());
		
		System.out.println("Sans modulation de signal, fc = 440 Hz, inOsc 880 Hz : on est une octave au dessus de la frequence de coupure, le signal doit donc etre attenue de 24dB (soit une amplitude en sortie d'environ 0,07)");
		inOsc.frequency.set(880);
		Tools.wait(synth, 1);
		vcf.filterPrintMinMaxAmplitude.reset();
		Tools.wait(synth, 2);
		System.out.println("Frequence de coupure du filtre 1 : " + vcf.filter1.frequency.getValue());
		System.out.println("Frequence de coupure du filtre 2 : " + vcf.filter2.frequency.getValue());
		System.out.println("Amplitude min : " + vcf.filterPrintMinMaxAmplitude.getMin() + "\nAmplitude max : " + vcf.filterPrintMinMaxAmplitude.getMax());
		
		System.out.println("Sans modulation de signal, fc = 440 Hz, inOsc 1760 Hz : on est deux octave au dessus de la frequence de coupure, le signal doit donc etre attenue de 48dB (soit une amplitude en sortie d'environ 0,005)");
		inOsc.frequency.set(1760);
		Tools.wait(synth, 1);
		vcf.filterPrintMinMaxAmplitude.reset();
		Tools.wait(synth, 2);
		System.out.println("Frequence de coupure du filtre 1 : " + vcf.filter1.frequency.getValue());
		System.out.println("Frequence de coupure du filtre 2 : " + vcf.filter2.frequency.getValue());
		System.out.println("Amplitude min : " + vcf.filterPrintMinMaxAmplitude.getMin() + "\nAmplitude max : " + vcf.filterPrintMinMaxAmplitude.getMax());
		
		System.out.println("Sans modulation de signal, fc = 440 Hz, inOsc 440 Hz : on est a la frequence de coupure, le signal ne doit donc pas etre attenue)");
		inOsc.frequency.set(440);
		Tools.wait(synth, 1);
		vcf.filterPrintMinMaxAmplitude.reset();
		Tools.wait(synth, 2);
		System.out.println("Frequence de coupure du filtre 1 : " + vcf.filter1.frequency.getValue());
		System.out.println("Frequence de coupure du filtre 2 : " + vcf.filter2.frequency.getValue());
		System.out.println("Amplitude min : " + vcf.filterPrintMinMaxAmplitude.getMin() + "\nAmplitude max : " + vcf.filterPrintMinMaxAmplitude.getMax());
		
		
		// On connecte l'oscillateur fmOsc a l'entree fm du VCF
		fmOsc.output.connect(vcf.getFm().getJSynPort());
		// On verifie que la frequence de coupure est bien divisee par 2 ou multipliee par 2 quand on passe d'une crete a la suivante
		// Avec un signal modulant carre, la valeur de la frequence du signal modulee va alternee entre 2 valeurs
		// Il suffit donc d'afficher ces valeurs pour verifier le rapport de 1 a 4.
		int i = 0;
		while(i<30) {
			System.out.println("Frequence filtre 1= " + vcf.filter1.frequency.getValue());
			System.out.println("Frequence filtre 1= " + vcf.filter2.frequency.getValue());
			i++;
			try {
				synth.sleepUntil( synth.getCurrentTime() + 0.3 );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// On verifie egalement dans cette phase que l'amplitude du signal en sortie est bien aux alentours de 1 lorsque la frequence de coupure est de 800 et 0,07 (attenuation de 24dB) lorsque la frequence de coupure est de 200
		
	}

}
