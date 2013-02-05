package group1.project.synthlab.ihm.port;

import group1.project.synthlab.exceptions.BadConnection;
import group1.project.synthlab.ihm.factory.CFactory;
import group1.project.synthlab.ihm.workspace.CWorkspace;
import group1.project.synthlab.module.IModule;
import group1.project.synthlab.port.Port;

public class CPort extends Port implements ICPort {
	protected IPPort presentation;
		
	public CPort(String label, CWorkspace workspace, IModule module,  CFactory factory) {
		super(label, module,  factory);
		presentation = new PPort(this);
	}

	public IPPort getPresentation() {
		return presentation;
	}

	public void actionCable() throws BadConnection {
		throw new BadConnection("Impossible de générer un cable avec un port de type indéfini, utilisez InPort ou OuPort !");
		
	}

	public void checkPutCable() {
		presentation.setForbidden();
		
	}

	public void removeCable() throws BadConnection {
		throw new BadConnection("Impossible de supprimer un cable avec un port de type indéfini, utilisez InPort ou OuPort !");
	}
}
