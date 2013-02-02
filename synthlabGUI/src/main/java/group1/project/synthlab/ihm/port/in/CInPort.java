package group1.project.synthlab.ihm.port.in;

import com.jsyn.ports.ConnectableInput;
import com.jsyn.ports.ConnectableOutput;

import group1.project.synthlab.cable.ICable;
import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.exceptions.PortAlreadyUsed;
import group1.project.synthlab.ihm.cable.CCable;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.port.CPort;
import group1.project.synthlab.ihm.port.IPPort;
import group1.project.synthlab.ihm.port.PPort;
import group1.project.synthlab.ihm.port.out.IPOutPort;
import group1.project.synthlab.ihm.port.out.POutPort;
import group1.project.synthlab.ihm.workspace.CWorkspace;
import group1.project.synthlab.port.in.InPort;
import group1.project.synthlab.workspace.Workspace;

public class CInPort extends InPort implements ICInPort {
	protected IPInPort presentation;

	public CInPort(String label, ConnectableInput jSynPort, CFactory factory) {
		super(label, jSynPort, factory);
		presentation = new PInPort(this);
	}

	public IPInPort getPresentation() {
		return presentation;
	}

	public void actionCable() throws BadConnection {
		if (!isUsed()) {
			if (CWorkspace.getInstance().isDrawingCable()) {
				CCable cable = (CCable) CWorkspace.getInstance()
						.getDrawingCable();
				
				try {
					cable.setInPort(this);
					CWorkspace.getInstance().setDrawingCable(null); 
					
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
		if (isUsed() || !CWorkspace.getInstance().isDrawingCable())
			presentation.setForbidden();
		else
			presentation.setAllowed();
	}
	
	
}
