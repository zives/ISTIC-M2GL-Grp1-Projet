package group1.project.synthlab.ihm.module.eqView;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.eqView.EQViewModule;
import group1.project.synthlab.port.IPort;

import java.awt.Component;
import java.util.Timer;
import java.util.TimerTask;

public class CEQViewModule extends EQViewModule implements ICEQViewModule {

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
		}, 0, 30);

		((Component) presentation).repaint();
		
	}
	
	

	@Override
	public void stop() {
		super.stop();
		((Component) presentation).repaint();
	}



	public IPEQViewModule getPresentation() {
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

	


}
