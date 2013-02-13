package group1.project.synthlab.ihm.module.piano;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.piano.PianoModule;
import group1.project.synthlab.port.IPort;

import java.awt.Component;

public class CPianoModule extends PianoModule implements ICPianoModule {
	
	protected int octaveStart;
	protected IPPianoModule presentation;
	protected final NOTE[] NOTE_N = {NOTE.DO, NOTE.RE, NOTE.MI, NOTE.FA, NOTE.SOL, NOTE.LA, NOTE.SI};
	protected final NOTE[] NOTE_D = {NOTE.DOd, NOTE.REd, NOTE.FAd, NOTE.SOLd, NOTE.LAd};

	public CPianoModule(CFactory factory) {
		super(factory);
		this.presentation = new PPianoModule(this);
		this.octaveStart = 2;
	}

	public IPPianoModule getPresentation() {
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
	public void playFromPresentation(int note, int octave, boolean sharp) {
		if (note > 7 && !sharp)
			return;
		else if (note > 5 && sharp)
			return;
		
		if (!sharp)
			super.play(NOTE_N[note], octave + octaveStart);
		else 
			super.play(NOTE_D[note], octave + octaveStart);
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
		String save = "<CPianoModule>\n";
		save+="<OctaveStart>"+this.octaveStart+"</OctaveStart>\n";
		save+="<Location x=\""+((Component) presentation).getLocation().getX()+
				"\" y=\""+((Component) presentation).getLocation().getY()+"\" />\n";
		save+="</CPianoModule>\n";
		return save;
	}
	
	@Override
	public int getOctaveStart(){
		return octaveStart;
	}

	public void updateOctaveStart(int parseInt) {
		changeoctave(parseInt);
		int position = 3 ;
		if(octaveStart == 4){
			position = 3;
		}
		else if(octaveStart == 2){
			position = 2;
		}
		System.out.println(position);
		presentation.updateSliderOctave(position);
	}
	
	public void updateLocation(double x, double y) {
		presentation.updateLocation(x,y);
	}


}
