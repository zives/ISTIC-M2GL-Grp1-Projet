package group1.project.synthlab.module.noise;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.PinkNoise;
import com.jsyn.unitgen.WhiteNoise;
import group1.project.synthlab.factory.Factory;
import group1.project.synthlab.module.Module;
import group1.project.synthlab.port.IPort;
import group1.project.synthlab.port.out.IOutPort;
import group1.project.synthlab.signal.Tools;
import group1.project.synthlab.unitExtensions.noise.BrownianNoise;

public class NoiseModule extends Module implements INoiseModule {

	protected static int moduleCount = 0;
	
	/** Etat du module (allume ou eteint) */
	protected boolean isOn;
	
	/** Generateur de bruit blanc de JSyn */
	protected WhiteNoise whiteNoise;
	
	/** Generateur de bruit Rouge/Brun de JSyn */
	// TODO : Red Noise = Brown Noise ?
	protected BrownianNoise brownianNoise;
	
	/** Generateur de bruit rose */
	protected PinkNoise pinkNoise;
	
	/** Port de sortie pour le bruit blanc */
	protected IOutPort outWhite;
	
	/** Port de sortie pour le bruit rouge */
	protected IOutPort outBrownian;
    
	/** Port de sortie pour le bruit rose */
	protected IOutPort outPink;
	
	/** Pour le on/off du bruit blanc */
	protected Multiply onoffWhite;
	
	/** Pour le on/off du bruit rose */
	protected Multiply onoffPink;
	
	/** Pour le on/off du bruit rouge/brun */
	protected Multiply onoffBrownian;
	
	public NoiseModule(Factory factory) {
		super("Noise-" + ++moduleCount, factory);
		
		// Les generateurs de bruit
		whiteNoise = new WhiteNoise();
		circuit.add(whiteNoise);
		brownianNoise = new BrownianNoise();
		circuit.add(brownianNoise);
		pinkNoise = new PinkNoise();
		circuit.add(pinkNoise);
		
		// Le on/off du bruit blanc
		onoffWhite = new Multiply();
		circuit.add(onoffWhite);
		onoffWhite.inputB.set(0);
		whiteNoise.output.connect(onoffWhite.inputA);
		
		// Le on/off du bruit rose
		onoffPink = new Multiply();
		circuit.add(onoffPink);
		onoffPink.inputB.set(0);
		pinkNoise.output.connect(onoffPink.inputA);
		
		// Le on/off du bruit rouge/brun
		onoffBrownian = new Multiply();
		circuit.add(onoffBrownian);
		onoffBrownian.inputB.set(0);
		brownianNoise.output.connect(onoffBrownian.inputA);
		
		// Ports de sortie
		outWhite = factory.createOutPort("white", onoffWhite.output, this);
		outBrownian = factory.createOutPort("brownian", onoffBrownian.output, this);
		outPink = factory.createOutPort("pink", onoffPink.output, this);

		isOn = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#start()
	 */
	public void start() {
		onoffWhite.inputB.set(1);
		onoffPink.inputB.set(1);
		onoffBrownian.inputB.set(1);
		isOn = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#stop()
	 */
	public void stop() {
		onoffWhite.inputB.set(0);
		onoffPink.inputB.set(0);
		onoffBrownian.inputB.set(0);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IModule#destruct()
	 */
	public void destruct() {
		if (outWhite.isUsed())
			outWhite.getCable().disconnect();
		if (outBrownian.isUsed())
			outBrownian.getCable().disconnect();
		if (outBrownian.isUsed())
			outBrownian.getCable().disconnect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.INoiseModule#stop()
	 */
	public void cableConnected(IPort port) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.INoiseModule#stop()
	 */
	public void cableDisconnected(IPort port) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IVCOModule#getOutWhite()
	 */
	public IOutPort getOutWhite() {
		return outWhite;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IVCOModule#getOutRed()
	 */
	public IOutPort getOutBrownian() {
		return outBrownian;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see group1.project.synthlab.module.IVCOModule#getOutPink()
	 */
	public IOutPort getOutPink() {
		return outPink;
	}
	
	// Tests fonctionnels
	public static void main(String[] args){
		// Factory
		Factory factory = new Factory();

		// On cree et demarre le Synthesizer
		Synthesizer synth = JSyn.createSynthesizer();
		synth.start();

		// On cree notre Module Noise et on ajoute le circuit cree au Synthesizer
		NoiseModule noise = (NoiseModule) factory.createNoiseModule();
		synth.add(noise.getCircuit());
		// LineOut remplace ici OutModule
		LineOut out = new LineOut();
		synth.add(out);
		out.start();

		System.out.println("Demarrage du Module Noise");
		noise.start();
		
		System.out.println("5s de bruit blanc");
		out.input.disconnectAll();
		out.input.connect(noise.getOutWhite().getJSynPort());
		Tools.wait(synth, 5);
		
		System.out.println("5s de bruit rose");
		out.input.disconnectAll();
		out.input.connect(noise.getOutPink().getJSynPort());
		Tools.wait(synth, 5);
		
		System.out.println("5s de bruit rouge/brun");
		out.input.disconnectAll();
		out.input.connect(noise.getOutBrownian().getJSynPort());
		Tools.wait(synth, 5);
		
		System.out.println("Arret du Module Noise");
		noise.stop();
	}
}
