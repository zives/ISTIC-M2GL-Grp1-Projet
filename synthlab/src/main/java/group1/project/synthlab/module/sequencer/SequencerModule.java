package group1.project.synthlab.module.sequencer;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.SineOscillator;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.IPortObserver;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.unitExtensions.filterSupervisor.FilterRisingEdge;
import group1.project.synthlab.unitExtensions.filterSupervisor.IFilterObserver;

/**
 * Module Sequenceur
 * 
 * @author Groupe 1
 * 
 */
public class SequencerModule extends Module implements IPortObserver, ISequencerModule, IFilterObserver {
	
	protected static int moduleCount = 0;

	/** Le pas courant */
	protected int currentStep;
	
	/** Le filtre permettant de detecter des fronts montants */
	protected FilterRisingEdge filterSequencer;
	
	/** Port d'entree : une entree de declenchement */
	protected IInPort gate;

	/** Signal produit */
	protected Multiply multiply;
	
	/** Port de sortie out */
	protected IOutPort out;

	/** Etat du module (allume ou eteint) */
	protected boolean isOn;
	
	/** Reglage des niveaux pour chaque pas */
	private double[] steps = new double[8];
	
	public SequencerModule(Factory factory) {
		super("VCA-" + ++moduleCount, factory);
		
		filterSequencer = new FilterRisingEdge();
		filterSequencer.register(this);
		
		circuit.add(filterSequencer);
		
		steps[0] = 1;
		steps[1] = -0.3;
		steps[2] = 0.5;
		steps[3] = -1;
		steps[4] = -0.8;
		steps[5] = -1;
		steps[6] = 0.7;
		steps[7] = 0;
		
		multiply = new Multiply();
		multiply.inputA.set(0);
		multiply.inputB.set(1);
		circuit.add(multiply);
		
		currentStep = 1;
		
		// Port d'entree : 
		gate = factory.createInPort("gate", filterSequencer.input, this);
		
		// Port de sortie
		out = factory.createOutPort("out", multiply.output, this);
				
		isOn = false;
	}


	public void resetSteps(){
		currentStep = 1;
	}
	
	public void update() {
		if(currentStep == 8)
			currentStep = 1;
		else
			currentStep++;
		multiply.inputA.set(steps[currentStep-1]);
		System.out.println("currentStep = " + currentStep);
	}
	
	@Override
	public void start() {
		circuit.start();
		isOn = true;
	}

	@Override
	public void stop() {
		circuit.stop();
		isOn = false;
	}

	@Override
	public boolean isStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void destruct() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cableConnected(IPort port) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cableDisconnected(IPort port) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args){
		
		//Factory
		Factory factory = new Factory();
		
		// On cree et demarre le Synthesizer
		Synthesizer synth = JSyn.createSynthesizer();
		
		// On cree notre Sequenceur et on ajoute le circuit cree au Synthesizer
		SequencerModule sequencer = (SequencerModule) factory.createSequencerModule();
		synth.add(sequencer.getCircuit());
		sequencer.start();
		
		// On cree un oscillateur que l'on connectera dans l'entree in
		SineOscillator oscGate = new SineOscillator();
		oscGate.amplitude.set(0.5);
		oscGate.frequency.set(0.5);
		synth.add(oscGate);
		oscGate.start();
		oscGate.output.connect(sequencer.gate.getJSynPort());

		synth.start();
		
		while(true){
			System.out.println("Out = " + sequencer.multiply.output.get());
			try {
				synth.sleepUntil(synth.getCurrentTime() + 0.1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
