package group1.project.synthlab.module.eq;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.module.vco.VCOModule;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Signal;
import group1.project.synthlab.signal.Tools;

import java.util.Timer;
import java.util.TimerTask;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.FilterPeakingEQ;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.SineOscillator;

/**
 * Module de sortie
 * 
 * @author Groupe 1
 * 
 */
public class EQModule extends Module implements IEQModule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6376655272091662424L;

	protected static int moduleCount = 0;

	/* Jsyn modules */
	protected transient FilterPeakingEQ[] filtersEQ;
	protected transient PassThrough ptIn;
	protected transient PassThrough ptOut;
	/* Defintion des ports */
	protected IInPort inPort;
	protected IOutPort outPort;

	/* Variables internes */
	protected double[] attenuations;
	protected final double[] FREQUENCIES = {16,32,63,125,250,500,1000,2000,4000,8000};
	/**
	 * Initialise le circuit (attenuateur, port, ...)
	 */
	public EQModule(Factory factory) {
		super("EQ-" + ++moduleCount, factory);

		filtersEQ = new FilterPeakingEQ[10];
		attenuations = new double[10];		
		ptIn = new PassThrough();
		ptOut = new PassThrough();
		inPort = factory.createInPort("in", ptIn.input, this);
	
		
		for (int i = 0; i < FREQUENCIES.length; ++i) {
			filtersEQ[i] = new FilterPeakingEQ();			
			//calcQ	http://www.rane.com/note170.html	 	
			double f1 =0;
			if (i == 0)
				f1 = ((0.01 + FREQUENCIES[i]) / 2);
			else
				f1 = ((FREQUENCIES[i - 1] + FREQUENCIES[i])  / 2);
			double f2 = 0;					
			if (i ==(FREQUENCIES.length - 1)) 
				f2 = (( FREQUENCIES[i] + 20000) / 2);
			else
				f2 = (( FREQUENCIES[i] +  FREQUENCIES[i + 1]) / 2);
			double f0 = FREQUENCIES[i];
			double Q = f0 / (f2 - f1);
			filtersEQ[i].frequency.set(FREQUENCIES[i]);
			filtersEQ[i].Q.set(Q);
			if (i-1 <0)
				filtersEQ[i].input.connect(ptIn.output);	
			else
				filtersEQ[i].input.connect(filtersEQ[i-1].output);	
			filtersEQ[i].gain.set(Tools.dBToV(0));
						
			circuit.add(filtersEQ[i]);
			//ptOut.input.connect(filtersEQ[i].output);
			
		}
		outPort = factory.createOutPort("out", filtersEQ[FREQUENCIES.length - 1].output, this);
			
		circuit.add(ptIn);
		circuit.add(ptOut);
	}
	
	@Override
	public void refresh() {
		for (int i = 0; i < FREQUENCIES.length; ++i)
			setAttenuation(i, attenuations[i]);
	}
	
	
	public double[] getFrequencies() {
		return FREQUENCIES;
	}
	
	public void setAttenuation(int i, double dB) {
		if (i >= FREQUENCIES.length)
			return;
		attenuations[i] = dB;
		filtersEQ[i].gain.set(Tools.dBToV(dB));
	}
	
	public double getAttenuation(int i) {
		return attenuations[i];
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#destruct()
	 */
	public void destruct() {
		if (inPort.isUsed())
			inPort.getCable().disconnect();
		if (outPort.isUsed())
			outPort.getCable().disconnect();

	}

	

	
	public IInPort getInPort() {
		return inPort;
	}
	
	public IOutPort getOutPort() {
		return outPort;
	}

	public void cableConnected(IPort port) {
	}

	public void cableDisconnected(IPort port) {

	}
	
	@Override
	public void resetCounterInstance() {
		EQModule.moduleCount = 0;		
	}
	
	public static void main(String[] args) {
		// Creeation de la factory
				Factory factory = new Factory();

				// Creation d'un module de sortie
				//OutModule out = new OutModule(factory);

				// Creation du synthetiseur
				Synthesizer synth = JSyn.createSynthesizer();
				synth.start();

				// Creation d'oscillateurs arbitraire
				SineOscillator oscS = null;
				synth.add(oscS = new SineOscillator());
				// Ajout de notre module au synthetiseur et connexion aux oscillateur
				//synth.add(out.getCircuit());
				// oscS.output.connect(out.getLeftPort().getJSynPort());

				// Reglage des oscillateurs
				oscS.frequency.set(700);
				oscS.amplitude.set(1);

			
				final FilterPeakingEQ filter = new FilterPeakingEQ();
				
				synth.add(filter);
				
				
				filter.input.connect(oscS.output);
					
				filter.frequency.set(700);
				filter.gain.set(Tools.dBToV(0));
				filter.start();
				
				Timer t = new Timer();
				t.schedule(new TimerTask() {
					
					@Override
					public void run() {
						System.err.println(filter.output.get());
						
					}
				},100,100);
		
	}


}
