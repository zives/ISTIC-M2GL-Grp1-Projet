package group1.project.synthlab.module.vcf;

import javax.swing.JFrame;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.IPortObserver;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.FilterBiquadCommon;
import com.jsyn.unitgen.FilterLowPass;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.PowerOfTwo;
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
	protected double f0 = 400;
	
	/** Filtre passe bas */
	protected FilterBiquadCommon filter;
	
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
	
	/** Pour l'application de la formule de modulation de frequence */
	protected Multiply multiplyf0 = new Multiply();
	
	/** Pour le on/off */
	protected Multiply onoff = new Multiply();
	
	/** Etat du module (allume ou eteint) */
	protected boolean isOn;
	
	/**
	 * Constructeur : initialise le VCF (, port, ...)
	 */
	public VCFModule(Factory factory) {
		super("VCF-" + ++moduleCount, factory);
		
		// Le filtre (ici un passe-bas)
		// TODO : proposer d'autres types de filtres
		filter = new FilterLowPass();
		circuit.add(filter);
		filter.output.connect(onoff.inputA);
		
		// On applique la formule f0 * 2 ^ (5Vfm) au signal en entree fm et on envoie la sortie dans un passThrough
		// Cette formule garantit egalement que si un signal nul est connecte en entree, la frequence de coupure vaudra f0
		// On doit multiplier Vfm par 5 car JSyn considere des amplitudes entre -1 et 1, et nous considerons des tensions entre -5V et +5V)
		Multiply multiply5 = new Multiply();
		multiply5.inputB.set(5); 
		PowerOfTwo poweroftwo = new PowerOfTwo();
		multiply5.output.connect(poweroftwo.input);
		multiplyf0.inputB.set(f0);
		multiplyf0.inputA.connect(poweroftwo.output);
		multiplyf0.output.connect(filter.frequency);
						
		// Port d'entree : 
		in = factory.createInPort("in", filter.input, this);
		fm = factory.createInPort("fm", multiply5.inputA, this);

		// Ports de sortie
		out = factory.createOutPort("out", onoff.output, this);
		
		// Lorsqu'il est cree, le VCF est eteint, on ne laisse donc passer aucun signal
		onoff.inputB.set(0);
	}
	
	// Fonction appelee lorsque les reglages sont modifiees sur l'IHM
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCOModule#changeFrequency()
	 */
	public void changeFrequency(){
		double newFrequency = (coarseAdjustment + fineAdjustment) * ((fmax - fmin) / 10);
		f0 = newFrequency;
		multiplyf0.inputB.set(f0);
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
		else if(port == in){
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
	// TODO : retester la formule de modulation de frequence pour etre sur
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
		inOsc.frequency.set(400);
		synth.add(inOsc);
		
		// On cree un oscillateur que l'on connectera dans l'entree fm
		SquareOscillator fmOsc = new SquareOscillator();
		fmOsc.amplitude.set(0.5);
		fmOsc.frequency.set(0.5);
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
		
		// Sans modulation de frequence au debut
		try
		{
			double time = synth.getCurrentTime();
			synth.sleepUntil( time + 2.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
		System.out.println("Sortie du filtre : " + vcf.filter.output.getValues().length);
		// On connecte l'oscillateur fmOsc a l'entree fm du VCF
		//fmOsc.output.connect(vcf.getFm().getJSynPort());
		
	}

}
