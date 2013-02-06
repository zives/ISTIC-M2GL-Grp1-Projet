package group1.project.synthlab.module.vca;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.IPortObserver;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.unitExtensions.FilterAttenuator.FilterModulationAmplitude;
import javax.swing.JFrame;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.SquareOscillator;

/**
 * Module VCA
 * 
 * @author Groupe 1
 * 
 */
public class VCAModule extends Module implements IPortObserver, IVCAModule {

	protected static int moduleCount = 0;

	/** Modulation d'amplitude connectee ou pas */
	protected boolean amConnected;
	
	/** Gain min */
	public static final double amin = 0;
	/** Gain max */
	public static final double amax = 6000;
	/** Gain par defaut */
	protected double a0 = 0; // Valeur en dB, a diviser par 60 avant de brancher dans le filtre (12dB => 1V => 0.2JSyn)
	
	/** Filtre d'attenuation en fonction de a0 */
	protected FilterModulationAmplitude filtera0;

	/** Filtre d'attenuation en fonction de am */
	protected FilterModulationAmplitude filteram;
	
	/**  */
	protected Multiply multiply = new Multiply();
	
	/** Port d'entree : entree de signal */
	protected IInPort in;

	/** Port d'entree : modulation de frequence */
	protected IInPort am;
	
	/** Port de sortie : signal filtre */
	protected IOutPort out;
	
	/** Etat du module (allume ou eteint) */
	protected boolean isOn;
	
	/**
	 * Constructeur : initialise le VCA (, port, ...)
	 */
	public VCAModule(Factory factory) {
		super("VCA-" + ++moduleCount, factory);
		
		filtera0 = new FilterModulationAmplitude();
		filteram = new FilterModulationAmplitude();

		filtera0.inputB.set(a0/60);
		
		circuit.add(filtera0);
		circuit.add(filteram);
		
		// Port d'entree : 
		in = factory.createInPort("in", filtera0.inputA, this);
		am = factory.createInPort("am", filteram.inputB, this);
		filtera0.output.connect(filteram.inputA);
		
		// Ports de sortie
		out = factory.createOutPort("out", filteram.output, this);
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
		if (am.isUsed())
			am.getCable().disconnect();
	}
	
	// Fonction appelee lorsque les reglages de la frequence de coupure sont modifiees sur l'IHM
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCOModule#changeFrequency()
	 */
	public void changeGain(){
		filtera0.inputB.set(a0/60);
	}
		
	public double geta0() {
		return a0;
	}

	public void seta0(double a0) {
		this.a0 = a0;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#start()
	 */
	public void start() {
		isOn = true;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#stop()
	 */
	public void stop() {
		isOn = false;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCAModule#getIn()
	 */
	public IInPort getIn() {
		return in;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCAModule#getFm()
	 */
	public IInPort getAm() {
		return am;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCAModule#getOut()
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
	 * @see group1.project.synthlab.module.IVCAModule#cableConnected()
	 */
	public void cableConnected(IPort port) {
		if(port == in){
			System.out.println("Connexion d'un cable dans l'entree in");
		}
		else if(port == am){
			filteram.inputB.connect(multiply.output);
			System.out.println("Connexion d'un cable dans l'entree am");
		}
	}

	// Fonction qui gere la deconnexion d'un cable d'un port d'entree
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCAModule#cableDisconnected()
	 */
	public void cableDisconnected(IPort port) {
		if(port == in){
			System.out.println("Deconnexion d'un cable de l'entree in");
		}
		else if(port == am){
			System.out.println("Deconnexion d'un cable de l'entree am");
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
		
		// On cree notre VCA et on ajoute le circuit cree au Synthesizer
		VCAModule vca = (VCAModule) factory.createVCAModule();
		synth.add(vca.getCircuit());
		vca.start();
		
		// On cree un oscillateur que l'on connectera dans l'entree in
		SquareOscillator inOsc = new SquareOscillator();
		inOsc.amplitude.set(0.5);
		inOsc.frequency.set(300);
		synth.add(inOsc);
		
		// On cree un oscillateur que l'on connectera dans l'entree am
		SquareOscillator amOsc = new SquareOscillator();
		amOsc.frequency.set(0.5);
		amOsc.amplitude.set(0.2);
		synth.add(amOsc);
				
		// LineOut remplace ici OutModule
		LineOut out = new LineOut();
		synth.add(out);
		out.start();
		
		// On connecte l'oscillateur inOsc a l'entree in du VCA
		inOsc.output.connect(vca.getIn().getJSynPort());
		
		// On connecte la sortie du VCA au LineOut
		vca.getOut().getJSynPort().connect(out.input);
		
		// Pour l'affichage des courbes
		AudioScope scope= new AudioScope( synth );
		scope.addProbe(vca.filteram.output);
		scope.setTriggerMode( AudioScope.TriggerMode.AUTO );
		scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.0001 );
		scope.getView().setShowControls( true );
		scope.start();
		JFrame frame = new JFrame();
		frame.add(scope.getView());
		frame.pack();
		frame.setVisible(true);
		
		// Sans modulation d'amplitude au debut
		int i = 0;
		while (i < 10) {
			System.out.println("Amplitude en sortie = " + vca.filteram.output.getValue());
			i++;
			try {
				synth.sleepUntil(synth.getCurrentTime() + 0.3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Avec modulation d'amplitude le reste du temps
		amOsc.output.connect(vca.getAm().getJSynPort());
		vca.cableConnected(vca.getAm());
		
		try
		{
			double time = synth.getCurrentTime();
			synth.sleepUntil( time + 2.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
		
		i = 0;
		while (i < 20) {
			System.out.println("Amplitude en sortie = " + vca.filteram.output.getValue());
			i++;
			try {
				synth.sleepUntil(synth.getCurrentTime() + 0.3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Changement de a0
		vca.seta0(6);
		vca.changeGain();
		i = 0;
		while (i < 20) {
			System.out.println("Amplitude en sortie = " + vca.filteram.output.getValue());
			i++;
			try {
				synth.sleepUntil(synth.getCurrentTime() + 0.3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
