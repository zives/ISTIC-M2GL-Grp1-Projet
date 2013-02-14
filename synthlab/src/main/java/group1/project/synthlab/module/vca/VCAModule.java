package group1.project.synthlab.module.vca;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.IPortObserver;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.unitExtension.filter.filterModulation.FilterAmplitudeModulation;
import javax.swing.JFrame;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SquareOscillator;

/**
 * Module VCA
 * 
 * @author Groupe 1
 * 
 */
public class VCAModule extends Module implements IPortObserver, IVCAModule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8298675496248913286L;

	protected static int moduleCount = 0;

	/** Modulation d'amplitude connectee ou pas */
	protected boolean amConnected;
	
	/** Gain min en dB*/
	public static final double amin = -60;
	/** Gain max en dB*/
	public static final double amax = 24;
	/** Gain par defaut en dB */
	protected double a0 = 0; // Valeur en dB, a diviser par 60 avant de brancher dans le filtre (12dB => 1V => 0.2JSyn)
	
	/** Filtre d'attenuation en fonction de a0 */
	protected transient FilterAmplitudeModulation filtera0;

	/** Filtre d'attenuation en fonction de am */
	protected transient FilterAmplitudeModulation filteram;
	
	/** Port d'entree : entree de signal */
	protected IInPort in;

	/** Port d'entree : modulation de frequence */
	protected IInPort am;
	
	/** Port de sortie : signal filtre */
	protected IOutPort out;
	
	
	/**
	 * Constructeur : initialise le VCA (, port, ...)
	 */
	public VCAModule(Factory factory) {
		super("VCA-" + ++moduleCount, factory);
		
		filtera0 = new FilterAmplitudeModulation(); // filtera0 sert a modifier l'amplitude du signal en fonction de a0
		filteram = new FilterAmplitudeModulation(); // filtera0 sert a modifier l'amplitude du signal en fonction de am

		filtera0.inputB.set(a0/60);
		amConnected = false;
		
		circuit.add(filtera0);
		circuit.add(filteram);
		
		// Port d'entree : 
		in = factory.createInPort("in", filtera0.inputA, this);
		am = factory.createInPort("am", filteram.inputB, this);
		filtera0.output.connect(filteram.inputA);
		
		// Port de sortie
		out = factory.createOutPort("out", filteram.output, this);
	}
	
	@Override
	public void refresh() {
		changeGain();
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
	
	// Fonction appelee lorsque le reglage du gain est modifie sur l'IHM
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCAModule#changeGain()
	 */
	public void changeGain(){	
		filtera0.inputB.set(a0/60);
	
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCAModule#geta0()
	 */
	public double geta0() {
		return a0;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCAModule#seta0()
	 */
	public void seta0(double a0) {
		this.a0 = a0;
	}

	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCAModule#getFiltera0()
	 */
	public FilterAmplitudeModulation getFiltera0() {
		return filtera0;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCAModule#getFilteram()
	 */
	public FilterAmplitudeModulation getFilteram() {
		return filteram;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCAModule#getIn()
	 */
	public IInPort getIn() {
		return in;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCAModule#getAm()
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

	// Fonction qui gere la connexion d'un cable a un port d'entree
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IVCAModule#cableConnected()
	 */
	public void cableConnected(IPort port) {
		if(port == in){
			System.out.println("Connexion d'un cable dans l'entree in");
		}
		else if(port == am){
			amConnected  =true;
			changeGain();
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
			amConnected = false;
			changeGain();
			System.out.println("Deconnexion d'un cable de l'entree am");
		}
	}
	

	public void resetCounterInstance() {
		VCAModule.moduleCount = 0;		
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
		
		// On cree un oscillateur que l'on connectera dans l'entree in
		SquareOscillator inOsc = new SquareOscillator();
		inOsc.amplitude.set(0.5);
		inOsc.frequency.set(300);
		synth.add(inOsc);
		
		// On cree un oscillateur que l'on connectera dans l'entree am
		SquareOscillator amOsc = new SquareOscillator();
		amOsc.frequency.set(0.5);
		amOsc.amplitude.set(0.1); // Amplitude de 0.1JSyn correspond a une amplitude de 1V crete a crete. En passant d'un pic a un creux on doit donc avoir une attenuation de 12dB (amplitude divisee par 4)
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

        
		System.out.println("\n\n\n****************************************************\n");
		System.out.println("Tests fonctionnels du Module VCA");
		System.out.println("\n****************************************************\n\n");
		
		System.out.println("Sans modulation d'amplitude au debut. Le signal doit etre identique au signal en entree (amplitude 0.5 JSyn)");
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
		
		System.out.println("Sans modulation d'amplitude mais en reglant f0 a 6dB. L'amplitude du signal en sortie doit etre doublee (1 JSyn)");
		vca.seta0(6);
		vca.changeGain();
		i = 0;
		while (i < 10) {
			System.out.println("Amplitude en sortie = " + vca.filteram.output.getValue());
			i++;
			try {
				synth.sleepUntil(synth.getCurrentTime() + 0.3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("On remet a0 a 0. On active la modulation d'amplitude. L'amplitude doit varier du simple au quadruple (-12 dB => /4), de 0.25 JSyn (0.5/2) a 1 JSyn (0.5*2)");
		vca.seta0(0);
		vca.changeGain();
		amOsc.output.connect(vca.getAm().getJSynPort());
		vca.cableConnected(vca.getAm());
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
		
		System.out.println("Toujours avec la meme modulation d'amplitude, on met a0 a 6dB. L'amplitude doit varier du simple au quadruple (-12 dB => /4), mais dans des valeurs plus elevees, entre 0.50 JSYN (1/2) a 2 JSyn (1*2)");
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
		
		System.out.println("On remet a0 a 0. On change maintenant le signal modulant pour lui mettre une amplitude de 0.2, soit 2V crete a crete. L'amplitude du signal en sortie du VCA doit donc maintenant alterner dans un rapport de 1 a 16 (-2V => -24dB => /16), de 0.125 (0.5/4) a 2 (0.5*4)");
		vca.seta0(0);
		vca.changeGain();
		amOsc.amplitude.set(0.2);
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
