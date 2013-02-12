package group1.project.synthlab.ihm.module.vco.piano;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.ihm.module.vco.IPVCOModule;
import group1.project.synthlab.module.vco.piano.VCOPianoModule;
import group1.project.synthlab.port.IPort;

public class CVCOPianoModule extends VCOPianoModule implements ICVCOPianoModule {

	protected double[] gamme0 = { 32.7, 36.71, 41.20, 43.65, 49, 55, 61.74 };

	protected double[] gamme0Sharp = { 34.65, 38.89, 46.25, 51.91, 58.27 };
	
	protected int octaveStart;
	protected IPVCOPianoModule presentation;

	public CVCOPianoModule(CFactory factory) {
		super(factory);
		this.presentation = new PVCOPianoModule(this);
		this.filterAmplitude.register(this.presentation);
		this.octaveStart = 2;
	}

	public IPVCOPianoModule getPresentation() {
		return presentation;
	}

	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
	}

	@Override
	public void cableDisconnected(IPort port) {		
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}

	
	@Override
	public void play(int note, int octave, boolean sharp) {
		if (note > gamme0.length)
			return;
		if (!sharp)
			this.setf0(gamme0[note] * Math.pow(2, octave + octaveStart));
		else 
			this.setf0(gamme0Sharp[note] * Math.pow(2, octave + octaveStart));
		super.play();
	}

	@Override
	public void stopPlay() {
		this.stop();

	}

	@Override
	public void changeoctave(int value) {
		if (value == 3)
			octaveStart = 4;
		else if (value == 2)
			octaveStart = 2;
		else octaveStart = 0;
		
	}
	
	@Override
	public String saveConfiguration() {
		// TODO Auto-generated method stub
		String save = "<VCOPianoModule>\n";
		save+="<OcatveStart>"+this.octaveStart+"</OcatveStart>\n";
		save+="<Location x=\""+presentation.getLocation().getX()+
				"\" y=\""+presentation.getLocation().getY()+"\" />\n";
		save+="</VCOPianoModule>\n";
		return save;
	}


}
