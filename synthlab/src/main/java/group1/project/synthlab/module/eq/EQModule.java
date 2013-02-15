package group1.project.synthlab.module.eq;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Tools;

import com.jsyn.unitgen.FilterPeakingEQ;
import com.jsyn.unitgen.PassThrough;

/**
 * Module EQ
 * Un egaliseur
 * @author Groupe 1
 */
public class EQModule extends Module implements IEQModule {

	private static final long serialVersionUID = 6376655272091662424L;

	protected static int moduleCount = 0;

	/** Filtres JSyn permettant d'attenuer ou amplifier le son autour d'une frequence */
	protected transient FilterPeakingEQ[] filtersEQ;
	
	/* Quelques mixeurs internes */
	protected transient PassThrough ptIn;
	protected transient PassThrough ptOut;
	
	/** Port d'entree */
	protected IInPort inPort;
	
	/** Port de sortie avec les frequences amplifiees ou attenuees */
	protected IOutPort outPort;

	/** Les attenuations en db pour chaque frequence */
	protected double[] attenuations;
	
	/** Les frequences a attenuer */
	protected final double[] FREQUENCIES = {16,32,63,125,250,500,1000,2000,4000,8000};

	public EQModule(Factory factory) {
		super("EQ-" + ++moduleCount, factory);

		/* Initialisation des variables */
		filtersEQ = new FilterPeakingEQ[10];
		attenuations = new double[10];		
		ptIn = new PassThrough();
		ptOut = new PassThrough();
		inPort = factory.createInPort("in", ptIn.input, this);	
		
		for (int i = 0; i < FREQUENCIES.length; ++i) {
			/* Initialise le filtre */
			filtersEQ[i] = new FilterPeakingEQ();			
			
			//calcQ	http://www.rane.com/note170.html				
			/* Calcul le Q et les frequences à -3 dB*/
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
			
			/* Mise en série des filtres */
			if (i-1 <0)
				filtersEQ[i].input.connect(ptIn.output);	
			else
				filtersEQ[i].input.connect(filtersEQ[i-1].output);	
			
			/* Determine le gain par defaut du filtre */
			filtersEQ[i].gain.set(Tools.dBToV(0));
			
			/* Ajout au circuit */
			circuit.add(filtersEQ[i]);
			
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

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eq.IEQModule#getInPort()
	 */
	public IInPort getInPort() {
		return inPort;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eq.IEQModule#getOutPort()
	 */
	public IOutPort getOutPort() {
		return outPort;
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
		EQModule.moduleCount = 0;		
	}
	
	public static void main(String[] args) {
		
		
	}


}
