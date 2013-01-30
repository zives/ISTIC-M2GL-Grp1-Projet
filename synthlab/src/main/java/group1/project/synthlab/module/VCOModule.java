package group1.project.synthlab.module;

import javax.swing.JFrame;

import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.port.out.OutPort;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.Add;
import com.jsyn.unitgen.LineOut;
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
	public static final double a0 = 0.5; // TODO : valeur minimale de la fréquence
	
	// Fréquences par défaut, max et min :
	public static final double fmin = 0; // TODO : valeur minimale de la fréquence
	public static final double fmax = 6000; // TODO : valeur maximale de la fréquence
	public static final double f0 = 300; // TODO : valeur par défaut de f0 ?
	
	// Un oscillateur pour chaque forme d'onde
	private SineOscillator sineosc;
	private SquareOscillator squareosc;
	private TriangleOscillator triangleosc;

	// Port d'entrée : modulation de fréquence
	private InPort fm;
	
	// Un PassThrough pour envoyer la modulation de fréquence vers l'entrée des 3 oscillateurs
	PassThrough passThrough;
	
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
		
		// Création du PassThrough
		passThrough = new PassThrough();
		
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
			sineosc.frequency.set(newFrequency);
			squareosc.frequency.set(newFrequency);
			triangleosc.frequency.set(newFrequency);
		}
	}
	
	// Fonction qui gère la connexion à l'entrée FM, et donc le passage à la modulation de fréquence par un signal en entrée
	// TODO : modifier cette fonction pour qu'elle n'ait pas de paramètres et retrouve plutôt le signal d'entrée en passant par le cable (à faire quand la User Story CABL sera terminée)
	// La fonction sera donc appelée lorsqu'on créera un câble.
	public void connectFM(UnitOutputPort output){
		fmconnected = true;
		Multiply multiply5 = new Multiply();
		multiply5.inputA.connect(output);
		multiply5.inputB.set(1);
		PowerOfTwo poweroftwo = new PowerOfTwo();
		multiply5.output.connect(poweroftwo.input);
		Multiply multiplyf0 = new Multiply();
		multiplyf0.inputB.set(f0); // f plutôt ?
		multiplyf0.inputA.connect(poweroftwo.output);
		passThrough.input.connect(multiplyf0.output);
		passThrough.output.connect(sineosc.frequency);
		passThrough.output.connect(squareosc.frequency);
		passThrough.output.connect(triangleosc.frequency);
	}
	
	// Fonction qui gère la déconnexion à l'entrée FM
	public void disconnectFM(){
		fmconnected = false;
		passThrough.input.disconnectAll();
		passThrough.output.disconnectAll();
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

	public static void main(String[] args){
		// On crée et démarre le Synthesizer
		Synthesizer synth = JSyn.createSynthesizer();
		synth.start();
		
		// On crée notre VCO (pas de signal fm en entrée) et on ajoute le circuit créé au Synthesizer
		VCOModule vco = new VCOModule();
		synth.add(vco.getCircuit());

		// LineOut sera remplacé par OutModule
		LineOut lineOut = new LineOut();
		synth.add(lineOut);
		lineOut.start();
		
		// On connecte la sortie de notre oscillateur sinusoïdal à lineOut
		vco.sineosc.output.connect(lineOut.input);
		
		// On crée un VCO dont on va utiliser la sortie sinusoïdale pour moduler la fréquence de notre premier vco
		VCOModule fm = new VCOModule();
		synth.add(fm.getCircuit());
		fm.squareosc.frequency.set(1);
		fm.squareosc.amplitude.set(0.1);	// Mettre une valeur assez élevée sinon le changement est fréquence est inaudible
		
		// Pour l'affichage
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
			synth.sleepUntil( time + 1.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
		
		
		vco.connectFM(fm.squareosc.output);

		// Avec modulation de fréquence pendant 3s
		try
		{
			double time = synth.getCurrentTime();
			// Sleep for a few seconds.
			synth.sleepUntil( time + 4.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}
		
		
		/* Sans modulation de fréquence le reste du temps, avec une fréquence réglée un peu plus haut
		vco.disconnectFM();
		vco.reglageoctave = 1;
		vco.reglagefin = 0.2;
		vco.changeFrequency();*/
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
