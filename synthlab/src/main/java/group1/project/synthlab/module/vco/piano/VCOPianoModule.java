package group1.project.synthlab.module.vco.piano;

import com.jsyn.unitgen.PeakFollower;
import com.jsyn.unitgen.SquareOscillator;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.osc.OSCModule;
import group1.project.synthlab.module.vco.VCOModule;
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
	protected IOutPort outSignalOn;
	
	/* Variables internes */
	protected FilterBinaryModulation filterBinary;
	protected SquareOscillator sqrOsc;

	
	/**
	 * Constructeur : initialise le VCO (, port, ...)
	 */
	public VCOPianoModule(Factory factory) {
		super(factory);
		filterBinary = new FilterBinaryModulation(0.001);
		sqrOsc =new SquareOscillator();
		
		outSignalOn = factory.createOutPort("signal on", filterBinary.output, this);		
		circuit.add(filterBinary);
		circuit.add(sqrOsc);
		sqrOsc.frequency.set(0);
		sqrOsc.amplitude.set(amin);
		
		sqrOsc.output.connect(filterBinary.input);
	}


	@Override
	public IOutPort getOutSignalOn() {
		return outSignalOn;
	}


	@Override
	public void destruct() {
		if (outSignalOn.isUsed())
			outSignalOn.getCable().disconnect();
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
		filterBinary.input.set(0);
		filterBinary.generate();		
		super.stop();
		sqrOsc.amplitude.set(amin);
	}

	

}
