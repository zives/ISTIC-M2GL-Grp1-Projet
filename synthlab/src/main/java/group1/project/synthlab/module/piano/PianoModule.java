package group1.project.synthlab.module.piano;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.unitExtensions.filterModulation.FilterBinaryModulation;
import group1.project.synthlab.unitExtensions.filterModulation.FilterFrequencyModulation;

import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;

/**
 * Module VCO
 * 
 * @author Groupe 1
 * 
 */
public class PianoModule extends Module implements IPianoModule {

	protected static int moduleCount = 0;
	
	/** Pour de sortie pour le signal triangulaire */
	protected IOutPort outGate;
	protected IOutPort out;
	
	/* Variables internes */
	protected SquareOscillator sqrOscGate;
	protected SquareOscillator sqrOut;
	protected final double LA3 = 0;
	protected double a0 = LA3;

	protected boolean isOn;
	
	/**
	 * Constructeur : initialise le VCO (, port, ...)
	 */
	public PianoModule(Factory factory) {
		super("Piano-" + ++moduleCount, factory);
		sqrOscGate =new SquareOscillator();
		sqrOut =new SquareOscillator();
		
		outGate = factory.createOutPort("eg gate", sqrOscGate.output, this);		
		circuit.add(sqrOscGate);
		sqrOscGate.frequency.set(0);
		sqrOscGate.amplitude.set(0);
		
		out= factory.createOutPort("out", sqrOut.output, this);	
		circuit.add(sqrOut);
		sqrOut.frequency.set(0);
		sqrOut.amplitude.set(LA3);
		
		
	}


	@Override
	public IOutPort getOutGate() {
		return outGate;
	}
	
	@Override
	public IOutPort getOut() {
		return out;
	}


	@Override
	public void destruct() {
		if (outGate.isUsed())
			outGate.getCable().disconnect();
		if (out.isUsed())
			out.getCable().disconnect();
	}


	@Override
	public void start() {
		sqrOscGate.start();
		sqrOscGate.amplitude.set(1);
		sqrOut.amplitude.set(a0);
		isOn = true;
	}


	@Override
	public void stop() {		
		sqrOscGate.amplitude.set(0);
		isOn = false;
	}


	@Override
	public void cableConnected(IPort port) {

	}


	@Override
	public void cableDisconnected(IPort port) {

	}


	@Override
	public boolean isStarted() {
		return isOn;
	}


	@Override
	public void play(NOTE note, int octave) {
		int posLA = NOTE.LA.ordinal();
		int posFinal = (octave - 3) * 12 + note.ordinal() - posLA;
		a0 =  (posFinal / 12.0) / (double) Signal.AMAX;
		start();
	}


	
	

}
