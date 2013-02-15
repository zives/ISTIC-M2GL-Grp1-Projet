package group1.project.synthlab.module.eqView;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Signal;

import com.jsyn.unitgen.FilterBandPass;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.PeakFollower;

/**
 * Visualisateur du niveau audio par frequence
 * Un egaliseur graphique
 * @author Groupe 1
 * 
 */
public class EQViewModule extends Module implements IEQViewModule {

	private static final long serialVersionUID = -2644295642877311574L;

	protected static int moduleCount = 0;

	/** Des filtres JSyn permettant d'intercepter les valeurs autour d'une frequence uniquement */
	protected transient FilterBandPass[] filtersBand;
	
	/** Obtient les amplitudes maximums observees et les rend en sortie */
	protected transient PeakFollower[] filtersMax;
	
	/** Mixeur interne */
	protected transient PassThrough ptIn;
	
	/** Mixeur interne */
	protected transient PassThrough ptOut;
	
	/** Port d'entree */
	protected IInPort inPort;
	
	/** Port de sortie */
	protected IOutPort outPort;

	/** Demi-vie des PeakFollower, plus celle-ci est haute, plus un maximum est gardé en tant que valeur de référence longtemps */
	protected final double MAX_HALFLIFE = 0.5;

	/** Les frequences à observer */
	protected final double[] FREQUENCIES = {5,25,40,63,100,160,250,400,630,1000,1600,2500,4000,6300,10000};

	public EQViewModule(Factory factory) {
		super("EQView-" + ++moduleCount, factory);

		/* Initialisation des variables */
		filtersBand = new FilterBandPass[15];
		filtersMax = new PeakFollower[15];
		ptIn = new PassThrough();
		ptOut = new PassThrough();
		inPort = factory.createInPort("in", ptIn.input, this);
		outPort = factory.createOutPort("out", ptIn.output, this);
		
		for (int i = 0; i < FREQUENCIES.length; ++i) {
			/* Initialise les filtres */
			filtersBand[i] = new FilterBandPass();			
			
			/* Calcul le coefficient Q et les frequences f1 et f2 autour de f0 à -3 db */
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
			
			/* Connecte les filtres au miexeur d'entree en parallele */
			filtersBand[i].frequency.set(FREQUENCIES[i]);
			filtersBand[i].Q.set(Q);
			filtersBand[i].input.connect(ptIn.output);		
			
			/* Connecte les filtres cherchant les maximums de chaque frequence au filtre respectif passband */
			filtersMax[i] = new PeakFollower();
			filtersMax[i].halfLife.set(MAX_HALFLIFE);
			filtersMax[i].input.connect(filtersBand[i].output);
			
			/* Ajoute les filtres au circuit */
			circuit.add(filtersBand[i]);
			circuit.add(filtersMax[i]);
			
			/* Mix la sortie (non utlise car le port de sortie est connecte au port d'entree pour obtenir le meme son, il ne s'agit la que d'un visualisateur)*/
			ptOut.input.connect(filtersMax[i].output);
			
		}

		/* Ajoute les mixeurs au circuit */
		circuit.add(ptIn);
		circuit.add(ptOut);
				
	}
	
	@Override
	public void refresh() {
		//Rien à faire
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eqView.IEQViewModule#getMax(int)
	 */
	public double getMax(int i) {
		if (i >= FREQUENCIES.length)
			return 0;
		return filtersMax[i].output.get();				
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eqView.IEQViewModule#getFrequencies()
	 */
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
		if (outPort.isUsed())
			outPort.getCable().disconnect();

	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eqView.IEQViewModule#getInPort()
	 */
	public IInPort getInPort() {
		return inPort;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eqView.IEQViewModule#getOutPort()
	 */
	public IOutPort getOutPort() {
		return outPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#stop()
	 */
	public void stop() {
		Signal.turnOff(ptIn.input);
		super.stop();
		for (int i = 0; i < FREQUENCIES.length; ++i) 
			filtersMax[i].output.setValueInternal(0);
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
	 * @see group1.project.synthlab.module.IModule#resetCounterInstance()
	 */
	@Override
	public void resetCounterInstance() {
		EQViewModule.moduleCount = 0;		
	}

	public static void main(String[] args) {
		
	}

}
