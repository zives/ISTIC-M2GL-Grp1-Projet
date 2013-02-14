package group1.project.synthlab.ihm.module.sequencer;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.sequencer.SequencerModule;
import group1.project.synthlab.port.IPort;

<<<<<<< HEAD:synthlabGUI/src/main/java/group1/project/synthlab/ihm/module/sequencer/CSequencerModule.java
public class CSequencerModule extends SequencerModule implements ICSequencerModule {
	protected IPSequencerModule presentation;
=======
public class CSequencer extends SequencerModule implements ICSequencer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2949518358197250979L;
	protected IPSequencer presentation;
>>>>>>> 3413653a1cd8c04c66646c875ed3cfe92fe06730:synthlabGUI/src/main/java/group1/project/synthlab/ihm/module/sequencer/CSequencer.java

	public CSequencerModule(CFactory factory) {
		super(factory);
		this.presentation = new PSequencerModule(this);
	}

	public IPSequencerModule getPresentation() {
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

	@Override
	public void initPresentation(Object... params) {
		// TODO Auto-generated method stub
		presentation.updatePresentation();
	}
	
}
