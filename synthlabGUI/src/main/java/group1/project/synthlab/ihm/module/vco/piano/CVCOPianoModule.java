package group1.project.synthlab.ihm.module.vco.piano;

import java.awt.event.MouseEvent;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.vco.CVCOModule;
import group1.project.synthlab.ihm.module.vco.ICVCOModule;
import group1.project.synthlab.ihm.module.vco.IPVCOModule;

public class CVCOPianoModule extends CVCOModule implements ICVCOPianoModule {

	protected double[] gamme0 = { 32.7, 36.71, 41.20, 43.65, 49, 55, 61.74 };

	protected double[] gamme0Sharp = { 34.65, 38.89, 46.25, 51.91, 58.27 };

	public CVCOPianoModule(CFactory factory) {
		super(factory);
		this.presentation = new PVCOPianoModule(this);
		this.filterAmplitude.register(this.presentation);

	}

	public IPVCOModule getPresentation() {
		return presentation;
	}

	@Override
	public void play(int note, int octave, boolean sharp) {
		if (note > gamme0.length)
			return;
		if (!sharp)
			this.setf0(gamme0[note] * Math.pow(2, octave));
		else 
			this.setf0(gamme0Sharp[note] * Math.pow(2, octave));
		start();
	}

	@Override
	public void stopPlay() {
		this.stop();

	}


}
