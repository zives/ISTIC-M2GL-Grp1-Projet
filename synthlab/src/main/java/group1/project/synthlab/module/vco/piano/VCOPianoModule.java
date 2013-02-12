package group1.project.synthlab.module.vco.piano;

import com.jsyn.unitgen.PeakFollower;
import com.jsyn.unitgen.SquareOscillator;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.osc.OSCModule;
import group1.project.synthlab.module.vco.VCOModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.unitExtensions.filterModulation.FilterBinaryModulation;

/**
 * Module VCO
 * 
 * @author Groupe 1
 * 
 */
/**
 * @author Groupe 1
 *
 */
public class VCOPianoModule extends VCOModule implements IVCOPianoModule {

	
	/** Pour de sortie pour le signal triangulaire */
	protected IOutPort outEG;
	
	/* Variables internes */
	protected FilterBinaryModulation filterBinary;
	protected SquareOscillator sqrOsc;
	protected boolean continuousSound;

	
	/**
	 * Constructeur : initialise le VCO (, port, ...)
	 */
	public VCOPianoModule(Factory factory) {
		super(factory);
		filterBinary = new FilterBinaryModulation(0.001);
		sqrOsc =new SquareOscillator();
		
		outEG = factory.createOutPort("eg out", filterBinary.output, this);		
		circuit.add(filterBinary);
		circuit.add(sqrOsc);
		sqrOsc.frequency.set(0);
		sqrOsc.amplitude.set(amin);
		
		sqrOsc.output.connect(filterBinary.input);
		
		continuousSound = false;
	}


	@Override
	public IOutPort getOutEG() {
		return outEG;
	}


	@Override
	public void destruct() {
		if (outEG.isUsed())
			outEG.getCable().disconnect();
		super.destruct();
	}


	@Override
	public void start() {
		super.start();
		filterBinary.start();
		filterBinary.setEnabled(true);
		sqrOsc.start();
		sqrOsc.amplitude.set(a0);
	}


	@Override
	public void stop() {		
		if (!continuousSound) {			
			super.stop();
		}
		filterBinary.input.set(0);
		filterBinary.generate();	
		sqrOsc.amplitude.set(amin);
		
	}


	@Override
	public void cableConnected(IPort port) {
		super.cableConnected(port);
		if (port == outEG)
			continuousSound = true;
	}


	@Override
	public void cableDisconnected(IPort port) {
		if (port == outEG)
			continuousSound = false;
		super.cableDisconnected(port);
	}


	public void play() {
		start();
	}

	

}
