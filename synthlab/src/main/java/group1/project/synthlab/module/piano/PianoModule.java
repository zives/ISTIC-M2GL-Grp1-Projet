package group1.project.synthlab.module.piano;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.module.vco.VCOModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.unitExtension.producer.SimpleProducer;

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
	protected SimpleProducer gateProducer;
	protected SimpleProducer outProducer;
	protected final double LA3 = 0;
	protected double a0 = LA3;

	/**
	 * Constructeur : initialise le VCO (, port, ...)
	 */
	public PianoModule(Factory factory) {
		super("Piano-" + ++moduleCount, factory);
		gateProducer =new SimpleProducer();
		outProducer =new SimpleProducer();
		
		outGate = factory.createOutPort("eg gate", gateProducer.output, this);		
		circuit.add(gateProducer);
		gateProducer.input.set(0);
		
		out= factory.createOutPort("out", outProducer.output, this);	
		circuit.add(outProducer);
		outProducer.input.set(LA3);
		
		
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
	public void cableConnected(IPort port) {

	}


	@Override
	public void cableDisconnected(IPort port) {

	}

	@Override
	public void play(NOTE note, int octave) {
		int posLA = NOTE.LA.ordinal();
		int posFinal = (octave - 3) * 12 + note.ordinal() - posLA;
		a0 =  (posFinal / 12.0) / (double) Signal.AMAX;
		gateProducer.input.set(1);
		outProducer.input.set(a0);
	}


	public void stopPlay() {
		gateProducer.input.set(0);
		
	}
	
	@Override
	public void resetCounterInstance() {
		PianoModule.moduleCount = 0;		
	}


	
	

}
