package group1.project.synthlab.ihm.module.multiplexer;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.multiplexer.MultiplexerModule;
import group1.project.synthlab.port.IPort;

/**
 * @author Groupe 1
 * Controleur pour le module multiplexer
 */
public class CMultiplexerModule extends MultiplexerModule implements ICMultiplexerModule {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9151008358022627949L;
	protected IPMultiplexerModule presentation;

	public CMultiplexerModule(CFactory factory) {
		super(factory);
		this.presentation = new PMultiplexerModule(this);
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.multiplexer.ICMultiplexerModule#getPresentation()
	 */
	public IPMultiplexerModule getPresentation() {
		return presentation;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.multiplexer.MultiplexerModule#cableConnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.multiplexer.MultiplexerModule#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}
	 
}
