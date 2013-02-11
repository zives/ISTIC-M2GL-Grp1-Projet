package group1.project.synthlab.module.eqView;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.workspace.Workspace;

import java.util.Timer;
import java.util.TimerTask;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.FilterBandPass;
import com.jsyn.unitgen.FilterPeakingEQ;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.PeakFollower;
import com.jsyn.unitgen.SineOscillator;

/**
 * Module de sortie
 * 
 * @author Groupe 1
 * 
 */
public class EQViewModule extends Module implements IEQViewModule {

	protected static int moduleCount = 0;

	/* Jsyn modules */
	protected FilterBandPass[] filtersBand;
	protected PeakFollower[] filtersMax;
	protected PassThrough ptIn;
	protected PassThrough ptOut;
	/* Defintion des ports */
	protected IInPort inPort;

	/* Variables internes */
	protected AudioScope os ;

	protected boolean isOn;
	protected final double MAX_HALFLIFE = 0.5;

	protected final double[] FREQUENCIES = {5,25,40,63,100,160,250,400,630,1000,1600,2500,4000,6300,10000};
	/**
	 * Initialise le circuit (attenuateur, port, ...)
	 */
	public EQViewModule(Factory factory) {
		super("EQView-" + ++moduleCount, factory);

		filtersBand = new FilterBandPass[15];
		filtersMax = new PeakFollower[15];
		ptIn = new PassThrough();
		ptOut = new PassThrough();
		inPort = factory.createInPort("in", ptIn.input, this);
		
		
		os = new AudioScope(Workspace.getInstance().getSynthetizer());

		for (int i = 0; i < FREQUENCIES.length; ++i) {
			filtersBand[i] = new FilterBandPass();			
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
			filtersBand[i].frequency.set(FREQUENCIES[i]);
			filtersBand[i].Q.set(Q);
			filtersBand[i].input.connect(ptIn.output);		
			
			filtersMax[i] = new PeakFollower();
			filtersMax[i].halfLife.set(MAX_HALFLIFE);
			filtersMax[i].input.connect(filtersBand[i].output);
			
			circuit.add(filtersBand[i]);
			circuit.add(filtersMax[i]);
			ptOut.input.connect(filtersMax[i].output);
			
		}
		os.addProbe(ptIn.output); //It's the input because output is a corrupted signal
		isOn = false;

	}
	
	public double getMax(int i) {
		if (i >= FREQUENCIES.length)
			return 0;
		return filtersMax[i].output.get();				
	}
	
	public double[] getFrequencies() {
		return FREQUENCIES;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#destruct()
	 */
	public void destruct() {
		if (inPort.isUsed())
			inPort.getCable().disconnect();

	}

	

	
	public IInPort getInPort() {
		return inPort;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#start()
	 */
	public void start() {
		circuit.start();;
		os.start();
		isOn = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#stop()
	 */
	public void stop() {
		circuit.stop();
		os.stop();
		for (int i = 0; i < FREQUENCIES.length; ++i) 
			filtersMax[i].output.setValueInternal(0);
		isOn = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#isStarted()
	 */
	public boolean isStarted() {
		return isOn;
	}

	public void cableConnected(IPort port) {
	}

	public void cableDisconnected(IPort port) {

	}

	// Test fonctionnel
	@SuppressWarnings("deprecation")
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

	
		final FilterBandPass filter = new FilterBandPass();
		final PeakFollower filter2 = new PeakFollower();
		final FilterBandPass filter_a = new FilterBandPass();
		final PeakFollower filter_a2 = new PeakFollower();
		final FilterBandPass filter_b = new FilterBandPass();
		final PeakFollower filter_b22 = new PeakFollower();
		
		synth.add(filter2);
		synth.add(filter);
		synth.add(filter_a2);
		synth.add(filter_a);
		synth.add(filter_b22);
		synth.add(filter_b);
		
		PassThrough pt = new PassThrough();
		
		
		pt.output.connect(filter.input);
		pt.output.connect(filter_a.input);
		pt.output.connect(filter_b.input);
		pt.input.connect(oscS.output);
		
		filter.output.connect(filter.input);
		filter_a.output.connect(filter_a.input);
		filter_b.output.connect(filter_b.input);
			
		filter_b.frequency.set(700);
		filter_b.start();
		
		synth.add(pt);
		pt.start();
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.err.println(filter_b.output.get());
				
			}
		},100,100);
		
	//filter.output.connect(out.getLeftPort().getJSynPort());
		//out.start();

//		AudioScope scope = new AudioScope(synth);
//		scope.addProbe(oscS.output);
//		scope.addProbe(filter.output);
//		scope.setTriggerMode(AudioScope.TriggerMode.AUTO);
//
//		scope.getModel().getTriggerModel().getLevelModel()
//				.setDoubleValue(0.0001);
//		scope.getView().setShowControls(true);
//		scope.start();

		// Fenetre
//		JFrame frame = new JFrame();
//		frame.add(scope.getView());
//		frame.pack();
//		frame.setVisible(true);
	}

}