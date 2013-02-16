package group1.project.synthlab.ihm.module.sequencer;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.sequencer.SequencerModule;
import group1.project.synthlab.port.IPort;

/**
 * @author Groupe 1
 * Controleur du sequenceur
 */
public class CSequencerModule extends SequencerModule implements ICSequencerModule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected IPSequencerModule presentation;

	public CSequencerModule(CFactory factory) {
		super(factory);
		this.presentation = new PSequencerModule(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.sequencer.ICSequencerModule#getPresentation()
	 */
	public IPSequencerModule getPresentation() {
		return presentation;
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.sequencer.SequencerModule#cableConnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.sequencer.SequencerModule#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableDisconnected(IPort port) {		
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.sequencer.SequencerModule#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}
	
}
