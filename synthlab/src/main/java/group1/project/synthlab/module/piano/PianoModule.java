package group1.project.synthlab.module.piano;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.unitExtension.producer.SimpleProducer;

/**
 * Un piano * 
 * @author Groupe 1
 * 
 */
public class PianoModule extends Module implements IPianoModule {

	private static final long serialVersionUID = -5274787748023694192L;

	protected static int moduleCount = 0;
	
	/** Port de sortie correspondant ou non à l'appuis sur une touche */
	protected IOutPort outGate;
	
	/** Port de sortie correspondant à la tension à delivrer a un vco (ou autre) */
	protected IOutPort out;
	
	/** Generateur de tension pour le port gate */
	protected transient SimpleProducer gateProducer;
	
	/** Generateur de tension pour le port out */
	protected transient SimpleProducer outProducer;
	
	/** Definition de la tension pour la note LA */
	protected final double LA3 = 0;
	
	/** Tension courante */
	protected double a0 = LA3;

	public PianoModule(Factory factory) {
		super("Piano-" + ++moduleCount, factory);
		
		//Initialise les filtres
		gateProducer = new SimpleProducer();
		outProducer = new SimpleProducer();
		
		//Creations des ports
		outGate = factory.createOutPort("eg gate", gateProducer.output, this);		
		circuit.add(gateProducer);
		gateProducer.input.set(0);		
		out = factory.createOutPort("out", outProducer.output, this);	
		circuit.add(outProducer);
		outProducer.input.set(LA3);		
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#refresh()
	 */
	@Override
	public void refresh() {
		outProducer.input.set(a0);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.piano.IPianoModule#getOutGate()
	 */
	public IOutPort getOutGate() {
		return outGate;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.piano.IPianoModule#getOut()
	 */
	public IOutPort getOut() {
		return out;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#destruct()
	 */
	public void destruct() {
		if (outGate.isUsed())
			outGate.getCable().disconnect();
		if (out.isUsed())
			out.getCable().disconnect();
	}


	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPortObserver#cableConnected(group1.project.synthlab.port.IPort)
	 */
	public void cableConnected(IPort port) {

	}


	/* (non-Javadoc)
	 * @see group1.project.synthlab.port.IPortObserver#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	public void cableDisconnected(IPort port) {

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.piano.IPianoModule#play(group1.project.synthlab.module.piano.IPianoModule.NOTE, int)
	 */
	public void play(NOTE note, int octave) {
		//Position de la note LA dans l'enum
		int posLA = NOTE.LA.ordinal();
		//Position relative de la note recherchee par rapport a LA
		int posFinal = (octave - 3) * 12 + note.ordinal() - posLA;
		//Calcul de la tension a delivrer
		a0 =  (posFinal / 12.0) / (double) Signal.AMAX;
		//Mise a jour des filtres de production de tension
		gateProducer.input.set(1);
		outProducer.input.set(a0);
	}


	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.piano.IPianoModule#stopPlay()
	 */
	public void stopPlay() {
		gateProducer.input.set(0);
		
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#resetCounterInstance()
	 */
	public void resetCounterInstance() {
		PianoModule.moduleCount = 0;		
	}


	
	

}
