package group1.project.synthlab.module.sequencer;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.IPortObserver;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.signal.Tools;
import group1.project.synthlab.unitExtensions.filterSupervisor.FilterASupprimer;
import group1.project.synthlab.unitExtensions.filterSupervisor.FilterRisingEdge;
import group1.project.synthlab.unitExtensions.filterSupervisor.IFilterObserver;
import group1.project.synthlab.workspace.Workspace;

/**
 * Module Sequenceur
 * 
 * @author Groupe 1
 * 
 */
public class SequencerModule extends Module implements IPortObserver, ISequencerModule, IFilterObserver {
	
	protected static int moduleCount = 0;

	protected AudioScope scope;
	
	protected SquareOscillator osc;
	
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
		super("Sequencer-" + ++moduleCount, factory);
		
		filterSequencer = new FilterRisingEdge();
		filterSequencer.register(this);
		scope = new AudioScope(Workspace.getInstance().getSynthetizer());
		scope.addProbe(filterSequencer.output);
		circuit.add(filterSequencer);
		
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
		
		osc = new SquareOscillator();
		osc.frequency.set(0);
		osc.amplitude.set(1.0 / Signal.AMAX);
		osc.output.connect(multiply.inputB);
		circuit.add(osc);
		
		currentStep = 1;
		
		// Port d'entree : 
		gate = factory.createInPort("gate", filterSequencer.input, this);
		
		// Port de sortie
		out = factory.createOutPort("out", multiply.output, this);
		
		isOn = false;
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
		System.out.println("currentStep = " + currentStep + ", value = "+steps[currentStep-1]);
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#start()
	 */
	public void start() {
		scope.start();
		isOn = true;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#stop()
	 */
	public void stop() {
		scope.stop();
		isOn = false;
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
	 * @see group1.project.synthlab.module.IModule#isStarted()
	 */
	public boolean isStarted() {
		return isOn;
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
			System.out.println("Cable connecté dans l'entrée gate, isOn = "+isOn+", input = "+filterSequencer.input.get());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.ISequencerModule#cableDisonnected()
	 */
	public void cableDisconnected(IPort port) {
	}
	
	public static void main(String[] args){
		
		//Factory
		Factory factory = new Factory();
		
		// On cree et demarre le Synthesizer
		Synthesizer synth = JSyn.createSynthesizer();
		
		// On cree notre Sequenceur et on ajoute le circuit cree au Synthesizer
		SequencerModule sequencer = (SequencerModule) factory.createSequencerModule();
		synth.add(sequencer.getCircuit());
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

		FilterASupprimer filterASupprimer = new FilterASupprimer();
		sequencer.out.getJSynPort().connect(filterASupprimer.input);
		synth.add(filterASupprimer);
		filterASupprimer.start();
		synth.start();
		
		Tools.wait(synth, 30);

		System.out.println("On met toutes les valeurs des pas à 0.33 et on remet a 1");
		sequencer.resetSteps();
		sequencer.setStepValue(1, 0.33);
		sequencer.setStepValue(2, 0.33);
		sequencer.setStepValue(3, 0.33);
		sequencer.setStepValue(4, 0.33);
		sequencer.setStepValue(5, 0.33);
		sequencer.setStepValue(6, 0.33);
		sequencer.setStepValue(7, 0.33);
		sequencer.setStepValue(8, 0.33);

	}
	
}
