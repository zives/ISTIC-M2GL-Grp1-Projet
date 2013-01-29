package group1.project.synthlab.module;

import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.port.out.OutPort;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.ImpulseOscillator;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;

public class VCOModule extends Module {

	// Modulation de fréquence connectée ou pas
	private boolean fmconnected;
	
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
		// TODO : connecter l'entrée du PassThrough au signal de modulation de fréquence
		fm = new InPort("fm", passThrough.input);
		
		// Ports de sortie
		outsine = new OutPort("outsine", sineosc.output);
		outsquare = new OutPort("outsquare", squareosc.output);
		outtriangle = new OutPort("outtriangle", triangleosc.output);
		
		// Quand on crée notre VCO, il n'a pas de signal en entrée, donc la fréquence vaut f0
		fmconnected = false;
		
		if(fm.isUsed())
			System.out.println("ERREUR : le port fm est utilisé"); // TODO : pas besoin
		else{
			sineosc.amplitude.set( 0.6 ); // TODO : réglage de l'amplitude ?
			squareosc.amplitude.set( 0.6 ); // TODO : réglage de l'amplitude ?
			triangleosc.amplitude.set( 0.6 ); // TODO : réglage de l'amplitude ?
			sineosc.frequency.set(f0);
			squareosc.frequency.set(f0);
			triangleosc.frequency.set(f0);
		}
	}
	
	public void changeFrequency(){
		if(!fmconnected){
			double newFrequency = (reglageoctave + reglagefin) * ((fmax - fmin) / 10);
			sineosc.frequency.set(newFrequency);
			squareosc.frequency.set(newFrequency);
			triangleosc.frequency.set(newFrequency);
		}
	}
	
	public void start() {
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
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
		// Create a context for the synthesizer.
		Synthesizer synth = JSyn.createSynthesizer();
		// Start synthesizer using default stereo output at 44100 Hz.
		synth.start();
		VCOModule vco = new VCOModule();
		synth.add(vco.getCircuit());
		
		LineOut lineOut = new LineOut();
		// Add a stereo audio output unit.
		synth.add(lineOut);
		
		// Connect the oscillator to both channels of the output.
		vco.getOutsine().getJSynPort().connect(lineOut.input);
		
		lineOut.start();
		// Sleep while the sound is generated in the background.
		try
		{
			double time = synth.getCurrentTime();
			// Sleep for a few seconds.
			synth.sleepUntil( time + 4.0 );
		} catch( InterruptedException e )
		{
			e.printStackTrace();
		}

		System.out.println( "Stop playing. -------------------" );
		// Stop everything.
		synth.stop();
	}

	
}
