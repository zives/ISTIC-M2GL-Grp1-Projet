package group1.project.synthlab.ihm.module.eqView;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.eqView.EQViewModule;
import group1.project.synthlab.port.IPort;

import java.awt.Component;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Groupe 1
 * Controleur du module EQView
 */
public class CEQViewModule extends EQViewModule implements ICEQViewModule {

	private static final long serialVersionUID = 2533954124672614376L;
	
	protected IPEQViewModule presentation;
	
	public CEQViewModule(CFactory factory) {
		super(factory);
		this.presentation = new PEQViewModule(this);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if (isOn)
					((Component) presentation).repaint();
				
			}
		}, 0, 40);

		((Component) presentation).repaint();
		
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eqView.EQViewModule#stop()
	 */
	@Override
	public void stop() {
		super.stop();
		((Component) presentation).repaint();
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.ihm.module.ICModule#getPresentation()
	 */
	public IPEQViewModule getPresentation() {
		return presentation;
	}
	
	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eqView.EQViewModule#cableConnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableConnected(IPort port) {		
		super.cableConnected(port);
		presentation.register((IPModuleObserver) port.getCable());
		
	}

	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eqView.EQViewModule#cableDisconnected(group1.project.synthlab.port.IPort)
	 */
	@Override
	public void cableDisconnected(IPort port) {			
		presentation.unregister((IPModuleObserver) port.getCable());
		super.cableDisconnected(port);
	}


	/* (non-Javadoc)
	 * @see group1.project.synthlab.module.eqView.EQViewModule#refresh()
	 */
	@Override
	public void refresh() {
		super.refresh();
		presentation.updatePresentation();
	}
	
}
