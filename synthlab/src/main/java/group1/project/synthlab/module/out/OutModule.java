package group1.project.synthlab.module.out;

import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.in.IInPort;
import group1.project.synthlab.signal.Tools;
import group1.project.synthlab.unitExtension.filter.filterAttenuator.FilterAttenuator;

import javax.swing.JFrame;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.scope.AudioScope;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.TriangleOscillator;

/**
 * Module de sortie
 * 
 * @author Groupe 1
 * 
 */
public class OutModule extends Module implements IOutModule {


	private static final long serialVersionUID = -528788264072924146L;

	protected static int moduleCount = 0;

	/** Le module de sortie JSyn */
	protected transient LineOut lineOut;
	
	/** Filtre d'attenuation du port gauche */
	protected transient FilterAttenuator attenuatorLeft;
	
	/** Filtre d'attenuation du port droit */
	protected transient FilterAttenuator attenuatorRight;

	/** Le port gauche */
	protected IInPort leftPort;
	
	/** Le port droit */
	protected IInPort rightPort;

	/** Mixeur interne pour la distribution sur les HP */
	private transient PassThrough passThroughLeft;
	
	/** Mixeur interne pour la distribution sur les HP */
	private transient PassThrough passThroughRight;

	/** Distribution choisi par l'utilisateur */
	protected Distribution distribution;	
	
	/**
	 * Attenuation generale
	 */
	protected double attenuationDB;

	public OutModule(Factory factory) {
		super("Out-" + ++moduleCount, factory);
		
		//Initilisation du module de sortie
		lineOut = new LineOut();
		
		//Initialisation des filtres pour l'attenuation
		attenuatorLeft = new FilterAttenuator();
		attenuatorRight = new FilterAttenuator();

		//Ajout au circuit des composants
		circuit.add(attenuatorLeft);
		circuit.add(attenuatorRight);
		circuit.add(lineOut);

		//Initialisation des mixeurs internes et ajout au circuit
		passThroughLeft = new PassThrough();
		passThroughRight = new PassThrough();		
		circuit.add(passThroughLeft);
		circuit.add(passThroughRight);

		//MAJ de la distribution du son par defaut
		setDistribution(Distribution.NORMAL);
		
		//Pas d'attenuation de base
		attenuationDB = 0;

		//Creation des ports
		leftPort = factory.createInPort("in left", attenuatorLeft.input,
				this);
		rightPort = factory.createInPort("in right", attenuatorRight.input,
				this);

		//Connexions des ports aux mixeurs internes
		attenuatorLeft.output.connect(passThroughLeft.input);
		attenuatorRight.output.connect(passThroughRight.input);

	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#refresh()
	 */
	@Override
	public void refresh() {
		setAttenuation(attenuationDB);
		setDistribution(distribution);
	}


	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.IModule#destruct()
	 */
	public void destruct() {
		if (rightPort.isUsed())
			rightPort.getCable().disconnect();
		
		if (leftPort.isUsed())
			leftPort.getCable().disconnect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * group1.project.synthlab.module.out.IOutModule#setDistribution(group1.
	 * project.synthlab.module.out.OutModule.Distribution)
	 */
	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
		//Deconnexion des miexeurs aux ports
		passThroughLeft.output.disconnectAll();
		passThroughRight.output.disconnectAll();
		//Reconnexion
		switch (this.distribution) {
		case NORMAL:
			passThroughLeft.output.connect(lineOut.input.getConnectablePart(0));
			passThroughRight.output
					.connect(lineOut.input.getConnectablePart(1));
			break;
		default:
			passThroughLeft.output.connect(lineOut.input.getConnectablePart(0));
			passThroughLeft.output.connect(lineOut.input.getConnectablePart(1));
			passThroughRight.output
					.connect(lineOut.input.getConnectablePart(0));
			passThroughRight.output
					.connect(lineOut.input.getConnectablePart(1));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.out.IOutModule#setAttenuation(double)
	 */
	public void setAttenuation(double db) {
		if (attenuationDB > 12)
			attenuationDB = 12;
		this.attenuationDB = db;
		//On converti la valeur en dB en volt et on retranche la tension nominale (1 en JSyn) pour obtenir une attenuation
		attenuatorLeft.setAttenuation(Tools.dBToV(db) - 1);
		attenuatorRight.setAttenuation(Tools.dBToV(db) - 1);	

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.out.IOutModule#getAttenuation()
	 */
	public double getAttenuation() {
		return attenuationDB;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.out.IOutModule#getLineOut()
	 */
	public LineOut getLineOut() {
		return lineOut;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.out.IOutModule#getLeftPort()
	 */
	public IInPort getLeftPort() {
		return leftPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.out.IOutModule#getRightPort()
	 */
	public IInPort getRightPort() {
		return rightPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.out.IOutModule#getDistribution()
	 */
	public Distribution getDistribution() {
		return distribution;
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
	public void resetCounterInstance() {
		OutModule.moduleCount = 0;		
	}

	// Test fonctionnel
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		// Creeation de la factory
		Factory factory = new Factory();

		// Creation d'un module de sortie
		OutModule out = new OutModule(factory);

		// Creation du synthetiseur
		Synthesizer synth = JSyn.createSynthesizer();
		synth.start();

		// Creation d'oscillateurs arbitraire
		SineOscillator oscS = null;
		synth.add(oscS = new SineOscillator());
		TriangleOscillator oscT = null;
		synth.add(oscT = new TriangleOscillator());

		// Ajout de notre module au synthetiseur et connexion aux oscillateur
		synth.add(out.getCircuit());
		oscS.output.connect(out.getLeftPort().getJSynPort());
		oscT.output.connect(out.getRightPort().getJSynPort());

		// Reglage des oscillateurs
		oscS.frequency.set(200.0);
		oscS.amplitude.set(1);
		oscT.frequency.set(300.0);
		oscT.amplitude.set(1);

		// Vue graphique
		AudioScope scope = new AudioScope(synth);
		scope.addProbe(out.attenuatorLeft.output);
		scope.addProbe(out.attenuatorRight.output);
		scope.setTriggerMode(AudioScope.TriggerMode.AUTO);

		scope.getModel().getTriggerModel().getLevelModel()
				.setDoubleValue(0.0001);
		scope.getView().setShowControls(true);
		scope.start();

		// Fenetre
		JFrame frame = new JFrame();
		frame.add(scope.getView());
		frame.pack();
		frame.setVisible(true);

		System.out.println("\n\n\n****************************************************\n");
		System.out.println("Tests fonctionnels du Module OUT");
		System.out.println("\n****************************************************\n\n");
		
		// Debut du test
		try {
			out.start();
			System.out.println("Demarrage...");
			Thread.sleep(2000);
			System.out
					.println("Resultat attendu freq 200 oscillation sinusoidale a gauche et triangle freq 300 a droite");
			out.setDistribution(Distribution.NORMAL);
			oscS.amplitude.set(0);
			Thread.sleep(2000);
			oscS.amplitude.set(1);
			oscT.amplitude.set(0);
			Thread.sleep(2000);
			oscT.amplitude.set(1);
			Thread.sleep(2000);
			System.out.println("Mixage des ports");
			out.setDistribution(Distribution.DISTRIBUTED);
			Thread.sleep(2000);
			System.out.println("+12db");
			out.setAttenuation(12);
			Thread.sleep(2000);
			System.out.println("-20db");
			out.setAttenuation(-20);
			Thread.sleep(2000);
			System.out.println("Stop");
			out.stop();
			Thread.sleep(3000);
			System.out.println("Start");
			out.start();
			out.setAttenuation(0);
			Thread.sleep(3000);
		} catch (Exception e) {

		}

	}


}
