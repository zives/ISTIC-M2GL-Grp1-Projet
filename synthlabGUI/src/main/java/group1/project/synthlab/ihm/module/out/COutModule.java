package group1.project.synthlab.ihm.module.out;

import group1.project.synthlab.ihm.cable.ICCable;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.out.OutModule;
import group1.project.synthlab.module.out.OutModule.Distribution;
import group1.project.synthlab.port.IPort;

public class COutModule extends OutModule implements ICOutModule {
	protected IPOutModule presentation;

	public COutModule(CFactory factory) {
		super(factory);
		this.presentation = new POutModule(this);
	}

	public IPOutModule getPresentation() {
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
		String save = "<OutModule>\n";
		save+="<Distribution>"+this.getDistribution()+"</Distribution>\n";
		save+="<AttenuationDB>"+this.getAttenuation()+"</AttenuationDB>\n";
		save+="<Location x=\""+presentation.getLocation().getX()+
				"\" y=\""+presentation.getLocation().getY()+"\" />\n";
		save+="</OutModule>\n";
		return save;
		
	}

	public static void resetModuleCount() {
		// TODO Auto-generated method stub
		moduleCount = 0;
	}
	
	public void updateAttenuation(double db) {
		super.setAttenuation(db);
		presentation.updateSlider();

	}
	
	public void updateDistribution(Distribution d) {
		super.setDistribution(d);
		presentation.updateDistribution();

	}
}
