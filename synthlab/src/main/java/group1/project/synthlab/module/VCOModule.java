package group1.project.synthlab.module;

import javax.swing.JFrame;

import group1.project.synthlab.cable.Cable;
import group1.project.synthlab.module.OutModule.Distribution;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.IPortObserver;
import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.port.out.OutPort;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.PowerOfTwo;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;

public class VCOModule extends Module implements IPortObserver {

	// Modulation de fréquence connectée ou pas
	private boolean fmconnected;
	
	// Amplitude par défaut
	public static final double a0 = 0.5;
	
	// Fréquences max, min et de base par défaut :
	public static final double fmin = 0;
	public static final double fmax = 6000;
	public static double f0 = 300;
	
	// Un oscillateur pour chaque forme d'onde
	private SineOscillator sineosc;
	private SquareOscillator squareosc;
	private TriangleOscillator triangleosc;

	// Port d'entrée : modulation de fréquence
	private InPort fm;
	
	// Un PassThrough pour envoyer la modulation de fréquence vers l'entrée des 3 oscillateurs
	private PassThrough passThrough;
	
	// Ports de sortie : 1 pour chaque forme d'onde
	private OutPort outsine;
	private OutPort outsquare;
	private OutPort outtriangle;
	
	// Réglage manuel de la fréquence
	private int reglageoctave; // entre 0 et 9 
	private double reglagefin; // entre 0 et 1
	
	// Constructeur
	public VCOModule() {
		super("VCO-" + moduleCount);
		
		// Création des oscillateurs
		sineosc = new SineOscillator();
		squareosc = new SquareOscillator();
		triangleosc = new TriangleOscillator();
		
		circuit.add(sineosc);
		circuit.add(squareosc);
		circuit.add(triangleosc);
		
		// Création du PassThrough : on applique la formule f0 * 2 ^ (5*Vfm) au signal en entrée et on envoie la sortie dans un passThrough
		// On doit multiplier Vfm par 5 car JSyn considère des amplitudes entre -1 et 5, et nous considérons des tensions entre -5V et +5V)
		passThrough = new PassThrough();
		Multiply multiply5 = new Multiply();
		multiply5.inputB.set(5);
		PowerOfTwo poweroftwo = new PowerOfTwo();
		multiply5.output.connect(poweroftwo.input);
		Multiply multiplyf0 = new Multiply();
		multiplyf0.inputB.set(f0);
		multiplyf0.inputA.connect(poweroftwo.output);
		passThrough.input.connect(multiplyf0.output);
		
		// Port d'entrée : 
		fm = new InPort("fm", multiply5.inputA);
		fm.register(this);
		
		// Ports de sortie
		outsine = new OutPort("outsine", sineosc.output);
		outsquare = new OutPort("outsquare", squareosc.output);
		outtriangle = new OutPort("outtriangle", triangleosc.output);
		
		// Quand on crée notre VCO, il n'a pas de signal en entrée, donc la fréquence vaut f0
		fmconnected = false;
		
		// On règle les fréquences et amplitudes des oscillateurs aux valeurs par défaut
		sineosc.amplitude.set(a0);
		squareosc.amplitude.set(a0);
		triangleosc.amplitude.set(a0);
		sineosc.frequency.set(f0);
		squareosc.frequency.set(f0);
		triangleosc.frequency.set(f0);
	}
	
	// Fonction appelée lorsque les réglages sont modifiées sur l'IHM
	public void changeFrequency(){
		if(!fmconnected){
			double newFrequency = (reglageoctave + reglagefin) * ((fmax - fmin) / 10);
			f0 = newFrequency;
			sineosc.frequency.set(f0);
			squareosc.frequency.set(f0);
			triangleosc.frequency.set(f0);
		}
	}
	
	// Fonction qui gère la connexion à l'entrée FM, et donc le passage à la modulation de fréquence par un signal en entrée
	// La fonction sera donc appelée lorsqu'on connectera un cable au port fm
	public void cableConnected(IPort port) {
		if(port.getLabel().equals("fm")){ // Si un câble vient d'être connecté dans l'entrée fm, on "active" la modulation de fréquence
			System.out.println("\nConnexion d'un câble dans l'entrée fm !");
			fmconnected = true;
			passThrough.output.connect(sineosc.frequency);
			passThrough.output.connect(squareosc.frequency);
			passThrough.output.connect(triangleosc.frequency);
		}
	}

	// Fonction qui gère la déconnexion à l'entrée FM
	public void cableDisconnected(IPort port) {
		if(port.getLabel().equals("fm")){ // Si un câble vient d'être déconnecté de l'entrée fm, on remet la fréquence des oscillateurs à f0
			fmconnected = false;
			passThrough.output.disconnectAll();
		}
	}
	
	public void start() {
		sineosc.start();
		squareosc.start();
		triangleosc.start();
	}

	public void stop() {
		sineosc.stop();
		squareosc.stop();
		triangleosc.stop();
	}
	
	public InPort getFm() {
		return fm;
	}

	public void setFm(InPort fm) {
		this.fm = fm;
	}

	public OutPort getOutsine() {
		return outsine;
	}

	public void setOutsine(OutPort outsine) {
		this.outsine = outsine;
	}

	public OutPort getOutsquare() {
		return outsquare;
	}

	public void setOutsquare(OutPort outsquare) {
		this.outsquare = outsquare;
	}

	public OutPort getOuttriangle() {
		return outtriangle;
	}

	public void setOuttriangle(OutPort outtriangle) {
		this.outtriangle = outtriangle;
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Throwable{
		// On crée et démarre le Synthesizer
		Synthesizer synth = JSyn.createSynthesizer();
		synth.start();
		
		// On crée notre VCO (pas de signal fm en entrée) et on ajoute le circuit créé au Synthesizer
		VCOModule vco = new VCOModule();
		synth.add(vco.getCircuit());

		// LineOut sera remplacé par OutModule
		OutModule out = new OutModule();
		out.setDistribution(Distribution.MONO);
		synth.add(out.getCircuit());
		out.start();
		
		// On connecte la sortie sinusoïdale de notre VCO au module de sortie
		Cable c1 = new Cable();
		c1.setOutPort(vco.getOutsine());
		c1.setInPort(out.getLeftPort());
		
		// On crée un VCO dont on va utiliser la sortie sinusoïdale pour moduler la fréquence de notre premier vco
		VCOModule fm = new VCOModule();
		synth.add(fm.getCircuit());
		fm.squareosc.frequency.set(0.2);
		// Si l'on considère des tensions comprises entre -5V et +5V, cela correspond à une amplitude de 0,5V, donc une amplitude crète à crète de 1V
		// Ainsi, quand on passe d'un sommet à un creux, on divise la fréquence par 2, et quand on passe d'un creux à un sommet on multiplie la fréquence par 2
		fm.squareosc.amplitude.set(0.1);
		
		// Pour l'affichage des courbes
		AudioScope scope= new AudioScope( synth );
		scope.addProbe(vco.sineosc.output);
		scope.addProbe(fm.squareosc.output);
		scope.setTriggerMode( AudioScope.TriggerMode.AUTO );
		scope.getModel().getTriggerModel().getLevelModel().setDoubleValue( 0.0001 );
		scope.getView().setShowControls( true );
		scope.start();
		JFrame frame = new JFrame();
		frame.add(scope.getView());
		frame.pack();
		frame.setVisible(true);
		
		// Sans modulation de fréquence pendant 3s
		try
		{
			double time = synth.getCurrentTime();
			// Sleep for a few seconds.
			synth.sleepUntil( time + 3.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
		
		// Avec modulation de fréquence pendant quelques secondes
		// On connecte la sortie de notre VCO fm vers le port d'entrée fm de notre VCO vco
		Cable c2 = new Cable();
		c2.setOutPort(fm.getOutsquare());
		c2.setInPort(vco.getFm());

		// On vérifie que la fréquence est bien divisée par 2 ou multipliée par 2 quand on passe d'une crète à la suivante
		int i = 0;
		while(i<15) {
			System.out.println("Fréquence = " + vco.sineosc.frequency.getValue());
			i++;
			try {
				synth.sleepUntil( synth.getCurrentTime() + 0.5 );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Sans modulation de fréquence le reste du temps, avec une fréquence réglée un peu plus haut
		//fm.squareosc.output.disconnectAll();
		c2.finalize();
		vco.reglageoctave = 1;
		vco.reglagefin = 0.2;
		vco.changeFrequency();
	}

	public int getReglageoctave() {
		return reglageoctave;
	}

	public void setReglageoctave(int reglageoctave) {
		this.reglageoctave = reglageoctave;
	}

	public double getReglagefin() {
		return reglagefin;
	}

	public void setReglagefin(double reglagefin) {
		this.reglagefin = reglagefin;
	}

	
}
