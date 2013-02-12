package group1.project.synthlab.ihm.module.sequencer;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.sequencer.SequencerModule;
import group1.project.synthlab.port.IPort;

public class CSequencer extends SequencerModule implements ICSequencer {
	protected IPSequencer presentation;

	public CSequencer(CFactory factory) {
		super(factory);
		this.presentation = new PSequencer(this);
	}

	public IPSequencer getPresentation() {
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
	public String saveConfiguration() {
		// TODO Auto-generated method stub
		return "";
	}
	
}
