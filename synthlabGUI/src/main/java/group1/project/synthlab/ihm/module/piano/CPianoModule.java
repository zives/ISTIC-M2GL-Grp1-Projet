package group1.project.synthlab.ihm.module.piano;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.piano.PianoModule;
import group1.project.synthlab.port.IPort;

/**
 * @author Groupe 1
 * Controleur du module PIANO
 */
public class CPianoModule extends PianoModule implements ICPianoModule {
	
	private static final long serialVersionUID = -5331363512055964743L;
	
	/**
	 * Debut d'octave (indice de la presentation)
	 */
	protected int octaveStart;
	
	
	/**
	 * La presentation
	 */
	protected IPPianoModule presentation;	
	
	/**
	 * Les notes basiques
	 */
	protected final NOTE[] NOTE_N = {NOTE.DO, NOTE.RE, NOTE.MI, NOTE.FA, NOTE.SOL, NOTE.LA, NOTE.SI};
	
	/**
	 * Les notes dieses
	 */
	protected final NOTE[] NOTE_D = {NOTE.DOd, NOTE.REd, NOTE.FAd, NOTE.SOLd, NOTE.LAd};

	public CPianoModule(CFactory factory) {
		super(factory);
		this.presentation = new PPianoModule(this);
		this.octaveStart = 2;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPPianoModule getPresentation() {
		return presentation;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.piano.PianoModule#cableConnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.piano.PianoModule#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableDisconnected(IPort port) {		
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}

	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.piano.ICPianoModule#playFromPresentation(int, int, boolean)
	 */
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

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.piano.PianoModule#stopPlay()
	 */
	@Override
	public void stopPlay() {
		super.stopPlay();

	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.piano.ICPianoModule#changeoctave(int)
	 */
	@Override
	public void changeOctaveStart(int value) {
		if (value == 3)
			octaveStart = 4;
		else if (value == 2)
			octaveStart = 2;
		else octaveStart = 0;
		
	}	
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.piano.ICPianoModule#getOctaveStart()
	 */
	@Override
	public int getOctaveStart(){
		return octaveStart;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.piano.PianoModule#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}
	
	


}
