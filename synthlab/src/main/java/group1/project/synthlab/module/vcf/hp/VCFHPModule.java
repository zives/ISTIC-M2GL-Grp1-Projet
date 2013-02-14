package group1.project.synthlab.module.vcf.hp;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.IPortObserver;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.signal.Tools;
import group1.project.synthlab.unitExtension.filter.filterModulation.FilterFrequencyModulation;
import group1.project.synthlab.unitExtension.filter.filterSupervisor.FilterAmplitude;
import group1.project.synthlab.unitExtension.filter.filterSupervisor.FilterRecordMinMaxAmplitude;
import javax.swing.JFrame;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.FilterHighPass;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;

/**
 * Module VCF-LP
 * 
 * @author Groupe 1
 * 
 */
public class VCFHPModule extends Module implements IPortObserver, IVCFHPModule {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7686361549155605655L;

	protected static int moduleCount = 0;
    
    /** Frequence de coupure min */
    public static final double fmin = Signal.FMIN;
    /** Frequence de coupure max */
    public static final double fmax = Signal.FMAX;
    /** Frequence de coupure */
    protected double f0 = 440;
    
    /** Filtre JSyn */
    protected transient FilterHighPass filter;
    
    /** Reglage grossier de la frequence de coupure : entier de 0 a 990 */
    protected int coarseAdjustment;
    /** Reglage fin de la frequence de coupure : double entre 0 et 10 */
    protected double fineAdjustment;
    
    /** Port d'entree : entree de signal */
    protected IInPort in;

    /** Port d'entree : modulation de la frequence de coupure */
    protected IInPort fm;
    
    /** Port de sortie : signal filtre */
    protected IOutPort out;
    
    /**
     * Filtre pour appliquer la formule de modulation de la frequence de coupure : fc = f0 * 2^(Vfm)
     */
    protected transient FilterFrequencyModulation filterFrequencyModulation;
    
	/**
	 * Filtre pour ramener l'amplitude du signal modulant a amax si elle est au
	 * dessus
	 */
	protected transient FilterAmplitude filterAmplitude;
	
    /**
     * Filtre pour recuperer les valeurs max et min d'un signal
     */
    protected transient FilterRecordMinMaxAmplitude filterPrintMinMaxAmplitude;
   
    
    /**
     * Constructeur : initialise le VCFHP (port, ...)
     */
    public VCFHPModule(Factory factory) {
        super("VCF-HP-" + ++moduleCount, factory);

		// Filtre d'amplitude
		// TODO : Tester lorsqu'on aura un VCA
		filterAmplitude = new FilterAmplitude(Signal.AMAXMODULATION, true);
		circuit.add(filterAmplitude);
		
        filterFrequencyModulation = new FilterFrequencyModulation(f0); // Filtre de modulation de la frequence de coupure
        filterPrintMinMaxAmplitude = new FilterRecordMinMaxAmplitude(); // Filtre pour afficher les valeurs min et max d'amplitude
        circuit.add(filterFrequencyModulation);
        circuit.add(filterPrintMinMaxAmplitude);
        
        // Les 2 filtres en serie (ici : passe-bas)
        filter = new FilterHighPass();
        filter.frequency.set(f0);
        circuit.add(filter);
        filter.output.connect(filterPrintMinMaxAmplitude.input); // Pour afficher les valeurs min et max d'amplitude dans les tests
             
        // On applique la formule f0 * 2 ^ (5Vfm) au signal en entree fm et on envoie la sortie dans l'entree frequency des filtres
        // Cette formule garantit egalement que si un signal nul est connecte en entree, la frequence de coupure vaudra f0
        // On doit multiplier Vfm par 5 car JSyn considere des amplitudes entre -1 et 1, et nous considerons des tensions entre -5V et +5V)
        filterFrequencyModulation.output.connect(filter.frequency);
        
        // Port d'entree : 
        in = factory.createInPort("in", filter.input, this);
        fm = factory.createInPort("fm", filterAmplitude.input, this);
        filterAmplitude.output.connect(filterFrequencyModulation.input);
        
        // Ports de sortie
        out = factory.createOutPort("out", filterPrintMinMaxAmplitude.output, this);
        
        // On definie le coarseAdjustement et le fineAdjustement
     	redefAdjustments();
     	
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
    	double newFrequency = ((coarseAdjustment + fineAdjustment)
				* ((fmax - fmin) + fmin) / 100);
		f0 = newFrequency;
        filterFrequencyModulation.setf0(f0);

    }
    
    /* (non-Javadoc)
     * @see group1.project.synthlab.module.IVCFModule#getFilterFrequencyModulation()
     */
	public FilterFrequencyModulation getFilterFrequencyModulation() {
		return filterFrequencyModulation;
	}

	/* (non-Javadoc)
     * @see group1.project.synthlab.module.IVCFModule#getFilterAmplitude()
     */
	public FilterAmplitude getFilterAmplitude() {
		return filterAmplitude;
	}
	
	/* (non-Javadoc)
     * @see group1.project.synthlab.module.IVCFHPModule#getFilter()
     */
	public FilterHighPass getFilter() {
		return filter;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IVCFModule#redefAdjustments()
	 */
	public void redefAdjustments() {
		coarseAdjustment = (int) ((f0 - fmin) / (fmax - fmin) * 100);
		if (coarseAdjustment > 99)
			coarseAdjustment = 99;
		fineAdjustment = (((f0 - fmin) / (fmax - fmin)) * 100) - coarseAdjustment;
	}
        
      
    /* (non-Javadoc)
     * @see group1.project.synthlab.module.IVCFModule#getf0()
     */
    public double getf0() {
        return f0;
    }
    
    /* (non-Javadoc)
     * @see group1.project.synthlab.module.IVCFModule#setf0(double)
     */
    public void setf0(double f0) {
        this.f0 = f0;
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
    
    /*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IVCFModule#getCoarseAdjustment()
	 */

	public int getCoarseAdjustment() {
		return coarseAdjustment;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IVCFModule#setCoarseAdjustment()
	 */
	public void setCoarseAdjustment(int coarseadjustment) {
		this.coarseAdjustment = coarseadjustment;
		changeFrequency();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IVCFModule#getFineAdjustment()
	 */
	public double getFineAdjustment() {
		return fineAdjustment;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IVCFModule#setFineAdjustment()
	 */
	public void setFineAdjustment(double fineadjustment) {
		this.fineAdjustment = fineadjustment;
		changeFrequency();
	}
	
	@Override
	public void resetCounterInstance() {
		VCFHPModule.moduleCount = 0;		
	}
    
    // Tests fonctionnels
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        //Factory
        Factory factory = new Factory();
        
        // On cree et demarre le Synthesizer
        Synthesizer synth = JSyn.createSynthesizer();
        synth.start();
        
        // On cree notre VCFHP et on ajoute le circuit cree au Synthesizer
        VCFHPModule vcfhp = (VCFHPModule) factory.createVCFHPModule();
        synth.add(vcfhp.getCircuit());
        
        // On cree un oscillateur que l'on connectera dans l'entree in
        SineOscillator inOsc = new SineOscillator();
        inOsc.amplitude.set(1);
        inOsc.frequency.set(3000);
        synth.add(inOsc);
        
        // On cree un oscillateur que l'on connectera dans l'entree fm (amplitude crete a crete de 2V => frequence de coupure multipliee et divisee par 4)
        SquareOscillator fmOsc = new SquareOscillator();
        fmOsc.frequency.set(0.5);
        fmOsc.amplitude.set(0.2);
        synth.add(fmOsc);
        fmOsc.start();
                
        // LineOut remplace ici OutModule
        LineOut out = new LineOut();
        synth.add(out);
        out.start();
        
        // On connecte l'oscillateur inOsc a l'entree in du VCFHP
        inOsc.output.connect(vcfhp.getIn().getJSynPort());
        
        // On connecte la sortie du VCFHP au LineOut
        vcfhp.getOut().getJSynPort().connect(out.input);
        
        // Pour l'affichage des courbes
        AudioScope scope= new AudioScope( synth );
        scope.addProbe((UnitOutputPort) vcfhp.getOut().getJSynPort());
        scope.setTriggerMode( AudioScope.TriggerMode.AUTO );
        scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.0001 );
        scope.getView().setShowControls( true );
        scope.start();
        JFrame frame = new JFrame();
        frame.add(scope.getView());
        frame.pack();
        frame.setVisible(true);
        
        vcfhp.filterPrintMinMaxAmplitude.reset();
        
		System.out.println("\n\n\n****************************************************\n");
		System.out.println("Tests fonctionnels du Module VCF-HP");
		System.out.println("\n****************************************************\n\n");
		
        System.out.println("\n\nSans modulation de frequence au debut, on compare 4 frequences de inOsc avec");
        System.out.println("\n\nSans modulation de signal, fc = 440 Hz, inOsc 3000 Hz (ATTENTION AUX OREILLES !) : le signal ne doit pas etre filtre (amplitude en sortie de 1)");
        Tools.wait(synth, 2);
        System.out.println("Frequence de coupure du filtre : " + vcfhp.filter.frequency.getValue());
        System.out.println("Amplitude min : " + vcfhp.filterPrintMinMaxAmplitude.getMin() + "\nAmplitude max : " + vcfhp.filterPrintMinMaxAmplitude.getMax());
        
        System.out.println("\n\nSans modulation de signal, fc = 440 Hz, inOsc 220 Hz : on est une octave en dessous de la frequence de coupure, le signal doit donc etre attenue de 12dB (soit une amplitude en sortie d'environ 0.26)");
        inOsc.frequency.set(220);
        Tools.wait(synth, 1);
        vcfhp.filterPrintMinMaxAmplitude.reset();
        Tools.wait(synth, 2);
        System.out.println("Frequence de coupure du filtre : " + vcfhp.filter.frequency.getValue());
        System.out.println("Amplitude min : " + vcfhp.filterPrintMinMaxAmplitude.getMin() + "\nAmplitude max : " + vcfhp.filterPrintMinMaxAmplitude.getMax());
        
        System.out.println("\n\nSans modulation de signal, fc = 440 Hz, inOsc 110 Hz : on est deux octave en dessous de la frequence de coupure, le signal doit donc etre attenue de 24dB (soit une amplitude en sortie d'environ 0.07)");
        inOsc.frequency.set(110);
        Tools.wait(synth, 1);
        vcfhp.filterPrintMinMaxAmplitude.reset();
        Tools.wait(synth, 2);
        System.out.println("Frequence de coupure du filtre : " + vcfhp.filter.frequency.getValue());
        System.out.println("Amplitude min : " + vcfhp.filterPrintMinMaxAmplitude.getMin() + "\nAmplitude max : " + vcfhp.filterPrintMinMaxAmplitude.getMax());
        
        System.out.println("\n\nSans modulation de signal, fc = 440 Hz, inOsc 440 Hz : on est a la frequence de coupure, le signal ne doit donc pas etre attenue)");
        inOsc.frequency.set(440);
        Tools.wait(synth, 1);
        vcfhp.filterPrintMinMaxAmplitude.reset();
        Tools.wait(synth, 2);
        System.out.println("Frequence de coupure du filtre : " + vcfhp.filter.frequency.getValue());
        System.out.println("Amplitude min : " + vcfhp.filterPrintMinMaxAmplitude.getMin() + "\nAmplitude max : " + vcfhp.filterPrintMinMaxAmplitude.getMax());
        
        System.out.println("\n\nLorsque le filtre est totalement ouvert (frequence de coupure null) le signal de sortie est identique au signal d'entree");
        vcfhp.setf0(0);
        vcfhp.redefAdjustments();
        vcfhp.changeFrequency();
        Tools.wait(synth, 1);
        vcfhp.filterPrintMinMaxAmplitude.reset();
        Tools.wait(synth, 4);
        System.out.println("Frequence de coupure du filtre : " + vcfhp.filter.frequency.getValue());
        System.out.println("Amplitude min : " + vcfhp.filterPrintMinMaxAmplitude.getMin() + "\nAmplitude max : " + vcfhp.filterPrintMinMaxAmplitude.getMax());
        
        System.out.println("\n\nLorsque le filtre est totalement ferme (frequence de coupure infinie) le signal de sortie est nul");
        vcfhp.setf0(Double.MAX_VALUE);
        vcfhp.redefAdjustments();
        vcfhp.changeFrequency();
        Tools.wait(synth, 1);
        vcfhp.filterPrintMinMaxAmplitude.reset();
        Tools.wait(synth, 4);
        System.out.println("Frequence de coupure du filtre : " + vcfhp.filter.frequency.getValue());
        System.out.println("Amplitude min : " + vcfhp.filterPrintMinMaxAmplitude.getMin() + "\nAmplitude max : " + vcfhp.filterPrintMinMaxAmplitude.getMax());
        
        
        System.out.println("\n\nOn remet f0 a 440 et la frequence de inOsc a 440 Hz");
        inOsc.frequency.set(440);
        vcfhp.setf0(440);
        vcfhp.redefAdjustments();
        vcfhp.changeFrequency();
        
        System.out.println("f0 = " + vcfhp.getf0());

        System.out.println("\n\nOn connecte l'oscillateur fmOsc a l'entree fm du VCFHP");
        fmOsc.output.connect(vcfhp.getFm().getJSynPort());
        vcfhp.cableConnected(vcfhp.getFm());
        
        // On verifie que la frequence de coupure est bien divisee par 4 ou multipliee par 4 quand on passe d'une crete a la suivante
        // Avec un signal modulant carre, la valeur de la frequence du signal modulee va alternee entre 2 valeurs (220 et 880)
        // Il suffit donc d'afficher ces valeurs pour verifier le rapport de 1 a 4.
        int i = 0;
        while(i<30) {
            System.out.println("Frequence de coupure du filtre = " + vcfhp.filter.frequency.getValue());
            i++;
            try {
                synth.sleepUntil( synth.getCurrentTime() + 0.3 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

