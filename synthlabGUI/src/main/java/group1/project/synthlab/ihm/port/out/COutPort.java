package group1.project.synthlab.ihm.port.out;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.ihm.cable.ICCable;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.workspace.CWorkspace;
import group1.project.synthlab.port.out.OutPort;

import com.jsyn.ports.ConnectableOutput;

public class COutPort extends OutPort implements ICOutPort {
	protected IPOutPort presentation;

	public COutPort(String label, ConnectableOutput jSynPort, CFactory factory) {
		super(label, jSynPort, factory);
		presentation = new POutPort(this);
	}

	public IPOutPort getPresentation() {
		return presentation;
	}

	public void actionCable() throws BadConnection {
		if (!isUsed()) {
			if (!CWorkspace.getInstance().isDrawingCable()) {
				ICCable cable = (ICCable)factory.createCable();
				try {
					CWorkspace.getInstance().setDrawingCable(cable);
					cable.setOutPort(this);
									
				} catch (PortAlreadyUsed e) {
					e.printStackTrace();
				}
				
			}
		} else {
			// TODO à faire dans une autre user story (Déconnexion)
		}

	}
	
	@Override
	public void setCable(ICable cable) {
		super.setCable(cable);
		presentation.refresh();
	}
	
	public void checkPutCable() {
		if (isUsed() || CWorkspace.getInstance().isDrawingCable())
			presentation.setForbidden();
		else
			presentation.setAllowed();
	}

}
