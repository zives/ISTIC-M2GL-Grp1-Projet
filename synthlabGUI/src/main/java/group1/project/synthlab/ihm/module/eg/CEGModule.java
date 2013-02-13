package group1.project.synthlab.ihm.module.eg;

import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.module.IPModuleObserver;
import group1.project.synthlab.module.eg.EGModule;
import group1.project.synthlab.port.IPort;

public class CEGModule extends EGModule implements ICEGModule {
	protected IPEGModule presentation;

	public CEGModule(CFactory factory) {
		super(factory);
		this.presentation = new PEGModule(this);
		//this.filterAmplitude.register(this.presentation);
	}

	public IPEGModule getPresentation() {
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
		String save = "<EGModule>\n";
		save+="<Attack>"+this.getAttack()+"</Attack>\n";
		save+="<Decay>"+this.getDecay()+"</Decay>\n";
		save+="<Release>"+this.getRelease()+"</Release>\n";
		save+="<Hold>"+this.getHold()+"</Hold>\n";
		save+="<Decibel>"+this.getDecibel()+"</Decibel>\n";
		save+="<Location x=\""+presentation.getLocation().getX()+
				"\" y=\""+presentation.getLocation().getY()+"\" />\n";
		save+="</EGModule>\n";
		return save;
	}

	public void updateAttack(double parseDouble) {
		// TODO Auto-generated method stub
		super.setAttack(parseDouble);
		presentation.updateAttackSlider(this.attack);
	}

	public void updateDecay(double parseDouble) {
		// TODO Auto-generated method stub
		super.setDecay(parseDouble);
		presentation.updateDecaySlider(this.decay);
	}

	public void updateRelease(double parseDouble) {
		// TODO Auto-generated method stub
		super.setRelease(parseDouble);
		presentation.updateReleaseSlider(this.release);
	}

	public void updateHold(double parseDouble) {
		// TODO Auto-generated method stub
		super.setHold(parseDouble);
		presentation.updateHoldSlider(this.hold);
	}

	public void updateDecibel(double parseDouble) {
		// TODO Auto-generated method stub
		super.setSustain(parseDouble);
		presentation.updateSustainSlider(this.decibel);
	}

	public void updateLocation(double x, double y) {
		// TODO Auto-generated method stub
		presentation.updateLocation(x,y);
	}
	
	
}
