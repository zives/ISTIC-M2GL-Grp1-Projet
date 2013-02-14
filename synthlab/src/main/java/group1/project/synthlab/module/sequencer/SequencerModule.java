package group1.project.synthlab.module.sequencer;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.IPortObserver;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.signal.Tools;
import group1.project.synthlab.unitExtension.filter.filterSupervisor.FilterRecordMinMaxAmplitude;
import group1.project.synthlab.unitExtension.filter.filterSupervisor.FilterRisingEdge;
import group1.project.synthlab.unitExtension.filter.filterSupervisor.IFilterObserver;
import group1.project.synthlab.unitExtension.producer.SimpleProducer;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.SineOscillator;

/**
 * Module Sequenceur
 * 
 * @author Groupe 1
 * 
 */
public class SequencerModule extends Module implements IPortObserver, ISequencerModule, IFilterObserver {
	
	protected static int moduleCount = 0;
	
	protected SimpleProducer producer;
	
	/** Le pas courant */
	protected int currentStep;
	
	/** Le filtre permettant de detecter des fronts montants */
	protected FilterRisingEdge filterRisingEdge;
	
	/** Port d'entree : une entree de declenchement */
	protected IInPort gate;

	/** Signal produit */
	protected Multiply multiply;
	
	/** Port de sortie out */
	protected IOutPort out;
	
	/** Reglage des niveaux pour chaque pas */
	private double[] steps = new double[8];
	
	public SequencerModule(Factory factory) {
		super("Sequencer-" + ++moduleCount, factory);
		
		filterRisingEdge = new FilterRisingEdge();
		filterRisingEdge.register(this);
		circuit.add(filterRisingEdge);
		
		steps[0] = 0;
		steps[1] = 0;
		steps[2] = 0;
		steps[3] = 0;
		steps[4] = 0;
		steps[5] = 0;
		steps[6] = 0;
		steps[7] = 0;
		
		multiply = new Multiply();
		circuit.add(multiply);
		
		producer = new SimpleProducer();
		producer.input.set(1.0 / Signal.AMAX);
		producer.output.connect(multiply.inputB);
		circuit.add(producer);
		
		currentStep = 1;
		
		// Port d'entree : 
		gate = factory.createInPort("gate", filterRisingEdge.input, this);
		
		// Port de sortie
		out = factory.createOutPort("out", multiply.output, this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.ISequencerModule#resetSteps()
	 */
	public void resetSteps(){
		currentStep = 1;
		multiply.inputA.set(steps[0]);
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.ISequencerModule#setStepValue()
	 */
	public void setStepValue(int step, double value){
		steps[step - 1] = value;
		if (currentStep == step)
			multiply.inputA.set(steps[currentStep-1]);
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.ISequencerModule#getStepValue()
	 */
	public double getStepValue(int step){
		return steps[step-1];
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.ISequencerModule#update()
	 */
	public void update() {
		if(currentStep == 8)
			currentStep = 1;
		else
			currentStep++;
		multiply.inputA.set(steps[currentStep-1]);
	}
	
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.ISequencerModule#getCurrentStep()
	 */
	public int getCurrentStep() {
		return currentStep;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.ISequencerModule#getFilterRisingEdge()
	 */
	public FilterRisingEdge getFilterRisingEdge() {
		return filterRisingEdge;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.ISequencerModule#getGate()
	 */
	public IInPort getGate() {
		return gate;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.ISequencerModule#getOut()
	 */
	public IOutPort getOut() {
		return out;
	}
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#destruct()
	 */
	public void destruct() {
		if (out.isUsed())
			out.getCable().disconnect();
		if (gate.isUsed())
			gate.getCable().disconnect();
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.ISequencerModule#cableConnected()
	 */
	public void cableConnected(IPort port) {
		if(port == gate)
			System.out.println("Cable connecte dans l'entree gate");
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.ISequencerModule#cableDisonnected()
	 */
	public void cableDisconnected(IPort port) {
	}
	
	public void resetCounterInstance() {
		SequencerModule.moduleCount = 0;		
	}
	
	public static void main(String[] args){
		
		//Factory
		Factory factory = new Factory();
		
		// On cree et demarre le Synthesizer
		Synthesizer synth = JSyn.createSynthesizer();
		
		// On cree notre Sequenceur et on ajoute le circuit cree au Synthesizer
		SequencerModule sequencer = (SequencerModule) factory.createSequencerModule();
		synth.add(sequencer.getCircuit());
		
		// On le connecte a un filtre en sortie qui recuperera les valeurs
		FilterRecordMinMaxAmplitude filter = new FilterRecordMinMaxAmplitude();
		synth.add(filter);
		filter.start();
		sequencer.getOut().getJSynPort().connect(filter.input);
		
		sequencer.setStepValue(1, 0.01);
		sequencer.setStepValue(2, 0.02);
		sequencer.setStepValue(3, 0.03);
		sequencer.setStepValue(4, 0.04);
		sequencer.setStepValue(5, 0.05);
		sequencer.setStepValue(6, 0.06);
		sequencer.setStepValue(7, 0.07);
		sequencer.setStepValue(8, 0.08);
		sequencer.start();
		
		// On cree un oscillateur que l'on connectera dans l'entree in
		SineOscillator oscGate = new SineOscillator();
		oscGate.amplitude.set(0.5);
		oscGate.frequency.set(1);
		synth.add(oscGate);
		oscGate.start();
		oscGate.output.connect(sequencer.gate.getJSynPort());

		synth.start();
		
		int i = 0;
		while(i<70){
			System.out.println("Valeur en sortie : "+filter.getCurrentValue());
			Tools.wait(synth, 0.2);
			i++;
		}

		System.out.println("On met toutes les valeurs des pas a 0.33 et on remet a 1");
		sequencer.resetSteps();
		sequencer.setStepValue(1, 0.33);
		sequencer.setStepValue(2, 0.33);
		sequencer.setStepValue(3, 0.33);
		sequencer.setStepValue(4, 0.33);
		sequencer.setStepValue(5, 0.33);
		sequencer.setStepValue(6, 0.33);
		sequencer.setStepValue(7, 0.33);
		sequencer.setStepValue(8, 0.33);

		while(true){
			System.out.println("Valeur en sortie : "+filter.getCurrentValue());
			Tools.wait(synth, 0.2);
			i++;
		}
		
	}
	
}
